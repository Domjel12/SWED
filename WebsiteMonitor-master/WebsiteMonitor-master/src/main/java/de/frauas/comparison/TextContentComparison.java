package de.frauas.comparison;

public class TextContentComparison implements WebsiteComparisonStrategy {
    @Override
    public boolean isEqual(String oldContent, String newContent) {
        String oldText = oldContent.replaceAll("<[^>]*>", "");
        String newText = newContent.replaceAll("<[^>]*>", "");
        return oldText.equals(newText);
    }
} 