package de.frauas;

import de.frauas.Observer.Observer;
import de.frauas.Observer.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Subscription implements Subject {
    private final URI website;
    private LocalDateTime lastUpdate;
    private String content = "";
    private List<Observer> observers = new ArrayList<>();

    public Subscription(URI website) {
        this.website = website;
    }

    public URI getWebsite() {
        return website;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdate;
    }

    // Renamed from CheckUpdate for convention, and now triggers notification
    public void checkUpdate() {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = website.toURL().openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            if (content.hashCode() != sb.toString().hashCode()) {
                content = sb.toString();
                lastUpdate = LocalDateTime.now();
                String message = website.toString() + " has changed!";
                notifyObservers(message); // Notify observers on change
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void registerObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        // Create a new list for iteration to avoid ConcurrentModificationException
        // if an observer tries to unsubscribe within its update method.
        List<Observer> observersToNotify = new ArrayList<>(this.observers);
        for (Observer observer : observersToNotify) {
            observer.update(this, message);
        }
    }
}