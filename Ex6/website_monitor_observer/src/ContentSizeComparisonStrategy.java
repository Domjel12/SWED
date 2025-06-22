public class ContentSizeComparisonStrategy implements WebsiteComparisonStrategy {
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        return oldContent.length() != newContent.length();
    }
} 