package de.frauas.Observer;

import de.frauas.Subscription; 

public interface Observer {
    void update(Subscription subscription, String message);
}
