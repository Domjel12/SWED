package de.frauas;

import de.frauas.Channels.IResponseChannel;
import de.frauas.Channels.MailChannel;
import de.frauas.Channels.SmsChannel;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebsiteMonitor {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Subscription> subscriptions = new HashMap<>(); // Key: URI.toString()

    // Private constructor for singleton or factory pattern, ensure it's used consistently
    private WebsiteMonitor() {}

    public void start() {
        System.out.println("WebsiteMonitor started!");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1); // Only one task: checking subscriptions
        // Periodically tell all subscriptions to check for updates.
        // If an update occurs, the Subscription itself will notify its observers (Users).
        executor.scheduleAtFixedRate(this::checkAllSubscriptions, 0, Settings.MONITOR_INTERVAL, TimeUnit.SECONDS);
    }

    public WebsiteMonitor registerUser(String userName, int frequency, URI websiteUri, IResponseChannel channel) {
        Subscription subscription = addOrGetSubscription(websiteUri);
        User user = users.get(userName);

        if (user == null) {
            user = new User(userName, frequency, channel);
            users.put(userName, user);
        } else {
            // User exists, add channel if not present. Frequency update logic could be added.
            user.addResponseChannel(channel);
        }
        user.subscribeTo(subscription); // User observes the subscription
        return this;
    }

    public WebsiteMonitor unregisterUser(String userName) {
        User user = users.remove(userName);
        if (user != null) {
            // The user should be removed from all subscriptions they were observing.
            for (Subscription sub : subscriptions.values()) {
                sub.removeObserver(user); // Make sure Subscription.removeObserver handles non-existent observers gracefully
            }
        }
        return this;
    }

    public WebsiteMonitor addUserWebsite(String userName, URI websiteUri) {
        User user = users.get(userName);
        if (user == null) {
            System.out.println("User " + userName + " not found. Cannot add website.");
            return this;
        }
        Subscription subscription = addOrGetSubscription(websiteUri);
        user.subscribeTo(subscription); // User observes the new subscription
        return this;
    }

    public WebsiteMonitor addUserResponseChannel(String userName, IResponseChannel channel) {
        User user = users.get(userName);
        if (user != null) {
            user.addResponseChannel(channel);
        } else {
            System.out.println("User " + userName + " not found. Cannot add response channel.");
        }
        return this;
    }

    private Subscription addOrGetSubscription(URI websiteUri) {
        return subscriptions.computeIfAbsent(websiteUri.toString(), key -> new Subscription(websiteUri));
    }

    private void checkAllSubscriptions() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " - Checking for updates on all subscriptions...");
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions to check.");
            return;
        }
        for (Subscription sub : subscriptions.values()) {
            sub.checkUpdate();
        }
    }

    public static void main(String[] args) {
        WebsiteMonitor monitor = new WebsiteMonitor();

        // Register "Somebody" for ycombinator with MailChannel
        monitor.registerUser("Somebody", 2, URI.create("https://news.ycombinator.com/"), new MailChannel());
        // Add another website for "Somebody"
        monitor.addUserWebsite("Somebody", URI.create("https://gist.githubusercontent.com/Descus/30d64f7141b03fb6536da4d58f88c0c2/raw/Test"));

        // Register "SomebodyElse" for ycombinator with MailChannel
        monitor.registerUser("SomebodyElse", 1, URI.create("https://news.ycombinator.com/"), new MailChannel());
        // Add SmsChannel for "SomebodyElse"
        monitor.addUserResponseChannel("SomebodyElse", new SmsChannel());
        
        // Example: "SomebodyElse" also wants to monitor a different site
        monitor.addUserWebsite("SomebodyElse", URI.create("https://www.perplexity.ai/"));


        monitor.start();
    }
}