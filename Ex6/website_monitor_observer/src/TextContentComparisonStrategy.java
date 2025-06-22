import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TextContentComparisonStrategy implements WebsiteComparisonStrategy {
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        Document oldDoc = Jsoup.parse(oldContent);
        Document newDoc = Jsoup.parse(newContent);
        
        String oldText = oldDoc.text().trim();
        String newText = newDoc.text().trim();
        
        return !oldText.equals(newText);
    }
} 