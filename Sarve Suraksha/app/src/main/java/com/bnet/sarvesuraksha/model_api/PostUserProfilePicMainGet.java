package com.bnet.sarvesuraksha.model_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostUserProfilePicMainGet {

    @SerializedName("profilePicture")
    @Expose
    private PostUserProfilePicMainRes profilePicture;

    public PostUserProfilePicMainRes getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(PostUserProfilePicMainRes profilePicture) {
        this.profilePicture = profilePicture;
    }

}
