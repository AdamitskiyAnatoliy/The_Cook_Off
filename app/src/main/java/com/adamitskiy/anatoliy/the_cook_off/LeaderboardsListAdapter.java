package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class LeaderboardsListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;
    List<ParseUser> users;
    ImageView userImage;

    public LeaderboardsListAdapter (Context _mContext, List<ParseUser> _users) {
        mContext = _mContext;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        users = _users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.leaderboards_list_item, null);

        TextView rank = (TextView) vi.findViewById(R.id.leaderboard_rank);
        rank.setText(position + 1 + ".");

        TextView username = (TextView) vi.findViewById(R.id.leaderboard_name_text);
        username.setText(users.get(position).getUsername());

        TextView points = (TextView) vi.findViewById(R.id.leaderboard_score_text);
        points.setText("Score: " + users.get(position).getString("Points"));

        userImage = (ImageView) vi.findViewById(R.id.leaderboard_image_view);

        if (users.get(position).getString("Type").equals("Normal")) {
            ParseFile image = users.get(position).getParseFile("image");
            new setNormalUserAvatar(userImage, image).setUserAvatar();
        } else if (users.get(position).getString("Type").equals("Twitter")) {
            new ImageDownloaderTask(userImage).execute(users.get(position).getString("socialAvatarUrl"));
        } else if (users.get(position).getString("Type").equals("Facebook")) {

        }

        return vi;
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
