package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// ==========================================
// 8. WEB, WEATHER & PUBLIC INFO (Tools 71 - 80)
// ==========================================
@Component
public class PublicInfoTools {

    @Tool(description = "71. Get current weather conditions for a city")
    public Map<String, Object> getWeather(@ToolParam(description = "City name") String city) {
        return Map.of("city", city, "tempC", 22.0, "condition", "Partly Cloudy", "humidity", "55%");
    }

    @Tool(description = "72. Get 3-day weather forecast summary for a city")
    public List<Map<String, String>> getWeatherForecast(@ToolParam(description = "City name") String city) {
        return List.of(
                Map.of("day", "Tomorrow", "forecast", "Sunny", "high", "24C"),
                Map.of("day", "Day 2", "forecast", "Scattered Showers", "high", "20C"),
                Map.of("day", "Day 3", "forecast", "Clear", "high", "22C")
        );
    }

    @Tool(description = "73. Search internet keywords for real-time information")
    public List<Map<String, String>> searchWeb(
            @ToolParam(description = "Query keywords") String query,
            @ToolParam(description = "Max results", required = false) Integer maxResults) {
        return List.of(
                Map.of("title", "Result for " + query, "snippet", "Summary content for query...", "url", "https://example.com/item")
        );
    }

    @Tool(description = "74. Fetch cleaned plain text content from a web page URL")
    public String fetchWebPageText(@ToolParam(description = "URL to read") String url) {
        return "Cleaned article text retrieved from " + url;
    }

    @Tool(description = "75. Look up definition and short encyclopedic summary")
    public String getWikipediaSummary(@ToolParam(description = "Topic entity") String topic) {
        return "Encyclopedic summary definition for: " + topic;
    }

    @Tool(description = "76. Get air quality index (AQI) rating and health advisory for a city")
    public Map<String, Object> getAirQualityIndex(@ToolParam(description = "City name") String city) {
        return Map.of("city", city, "aqi", 42, "category", "Good", "advisory", "Air quality is satisfactory.");
    }

    @Tool(description = "77. Get sunrise and sunset times for a location today")
    public Map<String, String> getSunTimes(@ToolParam(description = "City or coordinates") String location) {
        return Map.of("location", location, "sunrise", "06:15 AM", "sunset", "06:45 PM");
    }

    @Tool(description = "78. Find public national holidays for a country this year")
    public List<String> getPublicHolidays(
            @ToolParam(description = "2-letter Country code, e.g. US, IN, UK") String countryCode,
            @ToolParam(description = "Year") int year) {
        return List.of("New Year's Day", "Labor Day", "Independence Day");
    }

    @Tool(description = "79. Look up country capital, currency, and primary languages")
    public Map<String, String> getCountryInfo(@ToolParam(description = "Country name") String country) {
        return Map.of("country", country, "capital", "Capital City", "currency", "Local Currency");
    }

    @Tool(description = "80. Translate a phrase between two natural languages")
    public String translatePhrase(
            @ToolParam(description = "Phrase to translate") String text,
            @ToolParam(description = "Target language (e.g. Spanish, French, Hindi)") String targetLang) {
        return "[Translated to " + targetLang + "]: " + text;
    }
}
