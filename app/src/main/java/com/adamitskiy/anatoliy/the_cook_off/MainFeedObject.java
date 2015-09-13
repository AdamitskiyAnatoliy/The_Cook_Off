package com.adamitskiy.anatoliy.the_cook_off;

import com.parse.ParseFile;

import java.io.Serializable;

/**
 * Created by Anatoliy on 9/4/15.
 */
public class MainFeedObject implements Serializable {

    String avatarUrl;
    String typeOfUser;
    String description;
    ParseFile avatarFile;
    String userId;

    public MainFeedObject(String description, String avatarUrl, String typeOfUser, ParseFile avatarFile, String userId) {
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.typeOfUser = typeOfUser;
        this.avatarFile = avatarFile;
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public ParseFile getAvatarFile() {
        return avatarFile;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public String getUserId() {
        return userId;
    }
}
