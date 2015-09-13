package com.adamitskiy.anatoliy.the_cook_off;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

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
    }

    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("userId", user.getObjectId());
        installation.saveInBackground();
    }

}
