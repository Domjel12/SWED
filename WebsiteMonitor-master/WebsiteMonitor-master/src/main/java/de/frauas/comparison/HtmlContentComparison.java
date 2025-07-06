package de.frauas.comparison;

public class HtmlContentComparison implements WebsiteComparisonStrategy {
    @Override
    public boolean isEqual(String oldContent, String newContent) {
        return oldContent.equals(newContent);
    }
} 