package com.adamitskiy.anatoliy.the_cook_off;

import java.io.Serializable;

/**
 * Created by Anatoliy on 9/4/15.
 */
public class MainFeedObject implements Serializable {

    byte[] avatarByteArray;
    String description;

    public MainFeedObject(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getAvatarByteArray() {
        return avatarByteArray;
    }
}
