public class HtmlContentComparisonStrategy implements WebsiteComparisonStrategy {
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        return !oldContent.equals(newContent);
    }
} 