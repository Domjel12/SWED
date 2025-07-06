package de.frauas.comparison;

public class ContentSizeComparison implements WebsiteComparisonStrategy {
    @Override
    public boolean isEqual(String oldContent, String newContent) {
        return oldContent.length() == newContent.length();
    }
} 