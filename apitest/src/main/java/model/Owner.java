package model;

public class Owner {
    private int userId;
    private String displayName;
    private String ownerLink;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOwnerLink() {
        return ownerLink;
    }

    public void setOwnerLink(String ownerLink) {
        this.ownerLink = ownerLink;
    }

    //@Override
    //public String toString() {
    //    return "user_id: " + userId + " display_name: " + displayName + " link: " + ownerLink;
    //}
}
