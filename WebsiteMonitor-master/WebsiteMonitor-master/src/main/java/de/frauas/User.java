package de.frauas;

import de.frauas.Channels.IResponseChannel;
import de.frauas.Observer.Observer; // Import the Observer interface

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User implements Observer { // Implement Observer
    private final String name;
    private final int frequency; // User-specific notification interval factor
    private LocalDateTime lastNotification;
    private final List<IResponseChannel> responseChannels = new ArrayList<>();

    public User(String name, int frequency, IResponseChannel initialChannel) {
        this.name = name;
        this.frequency = frequency;
        this.lastNotification = LocalDateTime.MIN; // Ensure the first notification can be sent
        addResponseChannel(initialChannel);
    }

    // Method for a user to subscribe to a website (Subscription)
    public void subscribeTo(Subscription subscription) {
        subscription.registerObserver(this);
    }

    // Method for a user to unsubscribe from a website
    public void unsubscribeFrom(Subscription subscription) {
        subscription.removeObserver(this);
    }

    public void addResponseChannel(IResponseChannel responseChannel) {
        if (!this.responseChannels.contains(responseChannel)) {
            this.responseChannels.add(responseChannel);
        }
    }

    public void removeResponseChannel(IResponseChannel responseChannel) {
        this.responseChannels.remove(responseChannel);
    }

    // This method is called by the Subject (Subscription) when there's an update
    @Override
    public void update(Subscription subscription, String baseMessage) {
        // Check if it's time to notify this user based on their frequency and system settings
        if (lastNotification.plus((long) frequency * Settings.NOTIFICATION_INTERVAL, Settings.TIME_UNIT).isBefore(LocalDateTime.now())) {
            String message = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " - " + name + " - " + baseMessage;
            for (IResponseChannel channel : responseChannels) {
                channel.send(message);
            }
            lastNotification = LocalDateTime.now();
        }
    }

    public String getName() {
        return name;
    }
}
