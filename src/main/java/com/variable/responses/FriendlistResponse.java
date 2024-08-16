package com.variable.responses;

public class FriendlistResponse {
    private String username;

    private Object profilePicture;

    public FriendlistResponse(String username, Object profilePicture) {
        this.username = username;
        this.profilePicture = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Object profilePicture) {
        this.profilePicture = profilePicture;
    }
}
