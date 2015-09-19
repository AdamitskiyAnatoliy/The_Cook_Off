package com.adamitskiy.anatoliy.the_cook_off;

import android.app.Application;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import java.io.IOException;

/**
 * Created by Anatoliy on 9/7/2015.
 */
public class TheCookOff extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "NmlHibFZqo8D6anM56zLid80ZnHOG4R9LDUEVoNZ",
                "Z83VxBJolBG1rvWdZpUbNytqGZNAG3kADGrUlTHm");
        ParseTwitterUtils.initialize("FsIWKmg8KncvAQcxFKiEEJINZ", "9znkQcTRmraqYvDJtyiELvjuhwgHC0tmd2aw9nA78gpOrPMOP3");
        ParseFacebookUtils.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        /*
        Uri mediaUri = Uri.parse("android.resource://com.adamitskiy.anatoliy.the_cook_off/raw/media");
        MediaPlayer mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(getApplicationContext(), mediaUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setLooping(true);
        mPlayer.start();
        */
    }

    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("userId", user.getObjectId());
        installation.saveInBackground();
    }

}
