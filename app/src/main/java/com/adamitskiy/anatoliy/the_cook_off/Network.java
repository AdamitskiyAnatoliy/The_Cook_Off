package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class Network {

    Context mContext;

    public Network(Context con) {
        mContext = con;
    }

    public Boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}


