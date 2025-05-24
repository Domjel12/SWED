import java.util.ArrayList;
import java.util.List;

class User {
    private int userId;
    private String name;
    private String contactInfo;
    private List<Subscription> subscriptions;

    public User(int userId, String name, String contactInfo) {
        this.userId = userId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.subscriptions = new ArrayList<>();
    }

    public void register() {
    }

    public void manageSubscriptions(Subscription subscription, boolean add) {
        if (add) {
            this.subscriptions.add(subscription);
        } else {
            this.subscriptions.remove(subscription);
        }
    }
}

class Subscription {
    private int subscriptionId;
    private int frequency;
    private int communicationChannel;
    private Website website;

    public Subscription(int subscriptionId, int frequency, int communicationChannel, Website website) {
        this.subscriptionId = subscriptionId;
        this.frequency = frequency;
        this.communicationChannel = communicationChannel;
        this.website = website;
    }

    public void updateSettings(int newFrequency, int newChannel) {
        this.frequency = newFrequency;
        this.communicationChannel = newChannel;
    }

    public void cancel() {
    }
}

class Website {
    private String url;
    private long lastCheckedTimestamp;
    private NotificationService notificationService;

    public Website(String url, NotificationService notificationService) {
        this.url = url;
        this.lastCheckedTimestamp = System.currentTimeMillis();
        this.notificationService = notificationService;
    }

    public void checkForUpdates() {
        this.lastCheckedTimestamp = System.currentTimeMillis();
        boolean updateFound = Math.random() < 0.3; // Einfache Simulation

        if (updateFound) {
            Notification notification = new Notification(
                (int) (System.nanoTime() & 0xFFFF), // Einfache, kurze ID
                this.lastCheckedTimestamp,
                "Update detected for " + this.url,
                "PENDING"
            );
            this.notificationService.sendNotification(notification);
        }
    }

    public String getUrl() {
        return url;
    }
}

class Notification {
    private int notificationId;
    private long timestamp;
    private String content;
    private String status;

    public Notification(int notificationId, long timestamp, String content, String status) {
        this.notificationId = notificationId;
        this.timestamp = timestamp;
        this.content = content;
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

class Monitor {
    private List<Website> websitesToMonitor;

    public Monitor() {
        this.websitesToMonitor = new ArrayList<>();
    }

    public void addWebsite(Website website) {
        this.websitesToMonitor.add(website);
    }

    public void scheduleChecks() {
        
    }

    public void runCheckCycle() {
        for (Website website : websitesToMonitor) {
            website.checkForUpdates();
        }
    }
}

class NotificationService {
    public void sendNotification(Notification notification) {
        notification.setStatus("SENT");
    }
}