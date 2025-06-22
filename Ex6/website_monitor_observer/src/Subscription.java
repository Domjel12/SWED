import java.util.UUID;

public class Subscription {
    private String subscriptionID;
    private NotificationPreferences preferences;
    private Website website;
    private User user;

    public Subscription(User user, Website website, NotificationPreferences preferences) {
        this.subscriptionID = UUID.randomUUID().toString();
        this.user = user;
        this.website = website;
        this.preferences = preferences;
    }

    public NotificationPreferences getPreferences() {
        return preferences;
    }

    public Website getWebsite() {
        return website;
    }

    public User getUser() {
        return user;
    }
}
