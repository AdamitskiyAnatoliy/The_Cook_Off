package com.adamitskiy.anatoliy.the_cook_off;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class MainListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;
    ArrayList<MainFeedObject> feed;
    String twitterUrl;
    ImageView userImage;

    public MainListAdapter (Context _mContext, ArrayList<MainFeedObject> feed) {
        mContext = _mContext;
        this.feed = feed;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return feed.size();
    }

    @Override
    public Object getItem(int position) {
        return feed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.main_feed_list_item, null);

        userImage = (ImageView) vi.findViewById(R.id.main_feed_image_view);
        TextView achievement = (TextView) vi.findViewById(R.id.main_feed_achievement_text);
        Button likeButton = (Button) vi.findViewById(R.id.main_feed_like_button);
        Button commentButton = (Button) vi.findViewById(R.id.main_feed_comment_button);

        achievement.setText(feed.get(position).getDescription());

        if (feed.get(position).getTypeOfUser().equals("Normal")) {
            ParseFile image = feed.get(position).getAvatarFile();
            new setNormalUserAvatar(userImage, image).setUserAvatar();
        } else if (feed.get(position).getTypeOfUser().equals("Twitter")) {
            new ImageDownloaderTask(userImage).execute(feed.get(position).getAvatarUrl());
        } else if (feed.get(position).getTypeOfUser().equals("Facebook")) {

        }



        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> userIdList = new ArrayList<String>();
                userIdList.add(feed.get(position).getUserId());
                sendPushNotification(userIdList);

            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Comment");

                final EditText input = new EditText(mContext);;
                builder.setView(input);

                builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArrayList<String> userIdList = new ArrayList<String>();
                        userIdList.add(feed.get(position).getUserId());
                        sendCommentNotification(userIdList, "From " + ParseUser.getCurrentUser().getUsername() + ": " + input.getText().toString());

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        return vi;
    }

    public void sendPushNotification(ArrayList<String> list) {
        ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
        query.whereContainedIn("userId", list);

        //Push Notification
        ParsePush push = new ParsePush();
        push.setQuery(query);
        push.setMessage(ParseUser.getCurrentUser().getUsername() + " liked your progress.");
        push.sendInBackground();

        Toast.makeText(mContext, "Thumbs Up :)", Toast.LENGTH_SHORT).show();
    }

    public void sendCommentNotification(ArrayList<String> list, String message) {
        ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
        query.whereContainedIn("userId", list);

        //Push Notification
        ParsePush push = new ParsePush();
        push.setQuery(query);
        push.setMessage(message);
        push.sendInBackground();

        Toast.makeText(mContext, "Comment Sent", Toast.LENGTH_SHORT).show();
    }

    class setNormalUserAvatar {
        private final WeakReference<ImageView> imageViewReference;
        ParseFile image;
        public setNormalUserAvatar(ImageView imageView, ParseFile image) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            this.image = image;
        }

        public void setUserAvatar() {
            final ImageView imageView = imageViewReference.get();

            image.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                    }
                }
            });
        }

    }

    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public ImageDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Drawable placeholder = imageView.getContext().getResources().getDrawable(R.mipmap.ic_launcher);
                        imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode != HttpStatus.SC_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

}

