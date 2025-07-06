package de.frauas.comparison;

public interface WebsiteComparisonStrategy {
    boolean isEqual(String oldContent, String newContent);
} 