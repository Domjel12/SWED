import java.time.LocalDateTime;
import java.util.UUID;

public class User implements Observer {
    private String userID;
    private String name;
    private String contactInfo;

    public User(String name, String contactInfo) {
        this.userID = UUID.randomUUID().toString();
        this.name = name;
        this.contactInfo = contactInfo;
    }

    @Override
    public void update(String websiteUrl) {
        System.out.println("[" + LocalDateTime.now() + "] Benachrichtigung an " + name + ": Änderung erkannt auf " + websiteUrl);
    }
}
