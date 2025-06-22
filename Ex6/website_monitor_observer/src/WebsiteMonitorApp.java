import java.time.LocalDateTime;

public class WebsiteMonitorApp {
    public static void main(String[] args) throws InterruptedException {
        // Zwei User erstellen
        User user1 = new User("Dominik Jelic", "dominik@jelic.com");
        User user2 = new User("Max Mustermann", "max@mustermann.com");

        // NEU: Drei verschiedene Websites mit unterschiedlichen Vergleichsstrategien
        Website website1 = new Website("https://www.google.com", new ContentSizeComparisonStrategy());
        Website website2 = new Website("https://www.google.com", new HtmlContentComparisonStrategy());
        Website website3 = new Website("https://www.google.com", new TextContentComparisonStrategy());

        NotificationPreferences prefs = new NotificationPreferences("15", "terminal"); // alle 15 Sekunden

        // NEU: Subscriptions für jede Website
        Subscription sub1 = new Subscription(user1, website1, prefs);
        Subscription sub2 = new Subscription(user2, website2, prefs);
        Subscription sub3 = new Subscription(user1, website3, prefs);

        // NEU: Observer für jede Website registrieren
        website1.addObserver(user1);
        website2.addObserver(user2);
        website3.addObserver(user1);

        while (true) {
            // NEU: Ausgabe für bessere Übersichtlichkeit
            System.out.println("\nChecking websites with different strategies...");
            
            // NEU: Prüfen aller Websites mit ihren jeweiligen Strategien
            boolean changed1 = website1.checkForUpdates();
            if (!changed1) {
                // NEU: Angepasste Ausgaben für verschiedene Strategien
                System.out.println("[" + LocalDateTime.now() + "] Keine Änderung in der Größe auf " + website1.getUrl());
            }

            boolean changed2 = website2.checkForUpdates();
            if (!changed2) {
                // NEU: Angepasste Ausgaben für verschiedene Strategien
                System.out.println("[" + LocalDateTime.now() + "] Keine HTML-Änderung auf " + website2.getUrl());
            }

            boolean changed3 = website3.checkForUpdates();
            if (!changed3) {
                // NEU: Angepasste Ausgaben für verschiedene Strategien
                System.out.println("[" + LocalDateTime.now() + "] Keine Text-Änderung auf " + website3.getUrl());
            }

            Thread.sleep(Integer.parseInt(prefs.getFrequency()) * 1000);
        }
    }
}
