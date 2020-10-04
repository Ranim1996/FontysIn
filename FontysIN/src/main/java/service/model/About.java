package service.model;

public class About {
    private int id;
    private int profileId;
    private String content;

    public About(int id, int profileId, String content) {
        this.id = id;
        this.profileId = profileId;
        this.content = content;
    }


    public About() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
