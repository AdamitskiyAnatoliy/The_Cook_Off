package com.adamitskiy.anatoliy.the_cook_off;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
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

public class MyProfileActivity extends AppCompatActivity {

    String requestType = "";
    String stringUser;
    ParseUser user;
    ListView profileList;
    TextView userScoreProfile, profileUsername;
    ImageView userAvatar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Gson gson = new Gson();

        requestType = getIntent().getStringExtra("requestType");
        stringUser = getIntent().getStringExtra("User");
        user = gson.fromJson(stringUser, ParseUser.class);
        Bundle bundle = new Bundle();

        if (requestType.equals("nav")) {

            PointSystem pointSystem = new PointSystem(this);
            ParseUser parseUser = ParseUser.getCurrentUser();
            profileUsername = (TextView) findViewById(R.id.username_profile);
            profileUsername.setText(parseUser.getUsername());
            userScoreProfile = (TextView) findViewById(R.id.user_score_profile);
            userAvatar = (ImageView) findViewById(R.id.user_avatar_profile);
            userScoreProfile.setText("Score: " + Integer.toString(pointSystem.getPoints()));

            String myMessage = ParseUser.getCurrentUser().getObjectId();
            bundle.putString("userId", myMessage);

            if (ParseUser.getCurrentUser() != null) {
                if (ParseUser.getCurrentUser().getString("Type").equals("Normal")) {
                    ParseFile image = ParseUser.getCurrentUser().getParseFile("image");
                    image.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                userAvatar.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length) );
                            } else {
                                Log.d("Error", "Error Retrieving Avatar Navigation Image");
                            }
                        }
                    });
                } else if (ParseUser.getCurrentUser().getString("Type").equals("Twitter")) {

                    new ProfileAvatarDownloader(this).execute(ParseTwitterUtils.getTwitter().getScreenName());

                } else if (ParseUser.getCurrentUser().getString("Type").equals("Facebook")) {



                }
            }

        } else {

            profileUsername = (TextView) findViewById(R.id.username_profile);
            profileUsername.setText(getIntent().getStringExtra("name"));
            userAvatar = (ImageView) findViewById(R.id.user_avatar_profile);
            userScoreProfile = (TextView) findViewById(R.id.user_score_profile);
            userScoreProfile.setText("Score: " + getIntent().getStringExtra("score"));

            String myMessage = getIntent().getStringExtra("userId");
            bundle.putString("userId", myMessage);

            if (getIntent().getStringExtra("type").equals("Normal")) {
                byte[] userByteArray = new byte[0];
                try {
                    userByteArray = (byte[]) InternalStorage.readObject(this, "leaderboardImage");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                userAvatar.setImageBitmap(BitmapFactory.decodeByteArray(userByteArray, 0, userByteArray.length));
            } else if (getIntent().getStringExtra("type").equals("Twitter")) {
                new ProfileImageDownloaderTask(userAvatar).execute(getIntent().getStringExtra("avatarUrl"));
            } else if (getIntent().getStringExtra("type").equals("Facebook")) {

            }
        }

//        profileList = (ListView) findViewById(R.id.listView_profile);
//        ProfileListAdapter profileListAdapter = new ProfileListAdapter(this);
//        profileList.setAdapter(profileListAdapter);
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ProfileFeedFragment mainFrag = new ProfileFeedFragment();
        ProfileFeedFragment fragInfo = new ProfileFeedFragment();
        fragInfo.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_layout_profile, fragInfo, "HELLO");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    class ProfileAvatarDownloader extends AsyncTask<String, Void, Bitmap> {

        Context mContext;

        public ProfileAvatarDownloader(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String url = null;

            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet verifyGet = new HttpGet(
                        "https://api.twitter.com/1.1/users/show.json?screen_name=" + params[0]);
                ParseTwitterUtils.getTwitter().signRequest(verifyGet);
                HttpResponse response = client.execute(verifyGet);
                InputStream is = response.getEntity().getContent();
                JSONObject responseJson = new JSONObject(IOUtils.toString(is));
                url = responseJson.getString("profile_image_url");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                URL url1 = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);

            userAvatar.setImageBitmap(s);

        }
    }

    class ProfileImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public ProfileImageDownloaderTask(ImageView imageView) {
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
