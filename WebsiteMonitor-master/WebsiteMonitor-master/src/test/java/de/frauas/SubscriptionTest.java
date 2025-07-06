package de.frauas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.URI;

public class SubscriptionTest {

    @Test
    void testValidHttpUrlIsReachable() throws Exception {
        Subscription subscription = new Subscription(new URI("http://example.com"));
        subscription.checkUpdate();
        assertTrue(true); // Wenn keine Exception, ist der Test bestanden
    }

    @Test
    void testValidHttpsUrlIsReachable() throws Exception {
        Subscription subscription = new Subscription(new URI("https://www.google.com"));
        subscription.checkUpdate();
        assertTrue(true);
    }

    @Test
    void testInvalidUrlFormat() {
        assertThrows(Exception.class, () -> {
            new URI("http://inva lid");
        });
    }

    @Test
    void testUnreachableUrl() throws Exception {
        Subscription subscription = new Subscription(new URI("http://thisurldoesnotexist.tld"));
        assertThrows(Exception.class, () -> {
            subscription.checkUpdate();
        });
    }
}
