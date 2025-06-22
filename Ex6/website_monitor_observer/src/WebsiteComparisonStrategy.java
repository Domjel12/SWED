public interface WebsiteComparisonStrategy {
    boolean hasChanged(String oldContent, String newContent);
} 