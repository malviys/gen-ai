package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

// ==========================================
// 4. TEXT ANALYSIS & STRING UTILITIES (Tools 31 - 40)
// ==========================================
@Component
public class TextTools {

    @Tool(description = "31. Count words, characters, sentences, and paragraphs in text")
    public Map<String, Integer> countTextMetrics(@ToolParam(description = "Input text") String text) {
        if (text == null || text.isBlank()) return Map.of("words", 0, "chars", 0, "sentences", 0);
        String[] words = text.trim().split("\\s+");
        String[] sentences = text.split("[.!?]+");
        return Map.of("words", words.length, "chars", text.length(), "sentences", sentences.length);
    }

    @Tool(description = "32. Estimate reading time for a text passage in minutes")
    public double estimateReadingTime(
            @ToolParam(description = "Text content") String text,
            @ToolParam(description = "Words per minute speed (default 200)", required = false) Integer wpm) {
        int words = text.trim().split("\\s+").length;
        int speed = (wpm != null && wpm > 0) ? wpm : 200;
        return (double) words / speed;
    }

    @Tool(description = "33. Convert string to URL-friendly slug")
    public String createSlug(@ToolParam(description = "Input phrase") String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(normalized).replaceAll("")
                .toLowerCase().replaceAll("[^a-z0-9\\s-]", "").replaceAll("[\\s-]+", " ").trim().replace(' ', '-');
    }

    @Tool(description = "34. Truncate text cleanly to a word boundary with an ellipsis")
    public String truncateWords(
            @ToolParam(description = "Original text") String text,
            @ToolParam(description = "Max character length") int maxLen) {
        if (text.length() <= maxLen) return text;
        int lastSpace = text.substring(0, maxLen).lastIndexOf(' ');
        return (lastSpace > 0 ? text.substring(0, lastSpace) : text.substring(0, maxLen)) + "...";
    }

    @Tool(description = "35. Strip all HTML/XML tags from text")
    public String stripHtmlTags(@ToolParam(description = "HTML content") String html) {
        return html.replaceAll("<[^>]*>", "").replaceAll("&nbsp;", " ").trim();
    }

    @Tool(description = "36. Mask personal sensitive numbers showing only last 4 digits")
    public String maskSensitiveInfo(@ToolParam(description = "Raw number string") String raw) {
        if (raw.length() <= 4) return raw;
        return "*".repeat(raw.length() - 4) + raw.substring(raw.length() - 4);
    }

    @Tool(description = "37. Check if a word or phrase is a palindrome")
    public boolean checkPalindrome(@ToolParam(description = "Word or phrase") String phrase) {
        String clean = phrase.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return clean.equals(new StringBuilder(clean).reverse().toString());
    }

    @Tool(description = "38. Change text case: UPPERCASE, LOWERCASE, SNAKE_CASE")
    public String changeCase(
            @ToolParam(description = "Input text") String text,
            @ToolParam(description = "Target case") String targetCase) {
        return switch (targetCase.toUpperCase()) {
            case "UPPERCASE" -> text.toUpperCase();
            case "LOWERCASE" -> text.toLowerCase();
            case "SNAKE_CASE" -> text.toLowerCase().replaceAll("\\s+", "_");
            default -> text;
        };
    }

    @Tool(description = "39. Extract all email addresses found within a body of text")
    public List<String> extractEmails(@ToolParam(description = "Text containing emails") String text) {
        List<String> matches = new ArrayList<>();
        var matcher = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}").matcher(text);
        while (matcher.find()) matches.add(matcher.group());
        return matches;
    }

    @Tool(description = "40. Find and replace multiple words in text using a dictionary mapping")
    public String batchReplace(
            @ToolParam(description = "Source text") String text,
            @ToolParam(description = "Key-value mapping of search to replacement") Map<String, String> replacements) {
        String result = text;
        for (var entry : replacements.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
