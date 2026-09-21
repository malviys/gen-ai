package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.regex.Pattern;

// ==========================================
// 7. FORM & DATA VALIDATION (Tools 61 - 70)
// ==========================================
@Component
public class ValidationTools {

    @Tool(description = "61. Check if email format is syntactically valid")
    public boolean isValidEmail(@ToolParam(description = "Email string") String email) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches();
    }

    @Tool(description = "62. Check if a string is a valid web URL (HTTP/HTTPS)")
    public boolean isValidUrl(@ToolParam(description = "URL string") String url) {
        try {
            new URL(url).toURI();
            return url.startsWith("http://") || url.startsWith("https://");
        } catch (Exception e) {
            return false;
        }
    }

    @Tool(description = "63. Check if phone number contains 10 to 15 digits")
    public boolean isValidPhoneNumber(@ToolParam(description = "Phone string") String phone) {
        String digits = phone.replaceAll("\\D", "");
        return digits.length() >= 10 && digits.length() <= 15;
    }

    @Tool(description = "64. Check if string is a valid IPv4 address")
    public boolean isValidIpv4(@ToolParam(description = "IP string") String ip) {
        return Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$").matcher(ip).matches();
    }

    @Tool(description = "65. Check if string contains only ASCII characters")
    public boolean isAsciiOnly(@ToolParam(description = "Text") String text) {
        return text.chars().allMatch(c -> c < 128);
    }

    @Tool(description = "66. Validate password strength against criteria")
    public String assessPasswordStrength(@ToolParam(description = "Password to check") String password) {
        int score = 0;
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;
        if (Pattern.compile("[0-9]").matcher(password).find()) score++;
        if (Pattern.compile("[A-Z]").matcher(password).find()) score++;
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) score++;
        return score >= 4 ? "STRONG" : score >= 2 ? "MEDIUM" : "WEAK";
    }

    @Tool(description = "67. Validate hex color code format (#RGB or #RRGGBB)")
    public boolean isValidHexColor(@ToolParam(description = "Hex string") String hex) {
        return Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$").matcher(hex).matches();
    }

    @Tool(description = "68. Check if string is a valid UUID")
    public boolean isValidUuid(@ToolParam(description = "UUID string") String uuidStr) {
        return Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$").matcher(uuidStr).matches();
    }

    @Tool(description = "69. Check if string is formatted in standard ISO-8601 date (YYYY-MM-DD)")
    public boolean isValidIsoDate(@ToolParam(description = "Date string") String dateStr) {
        return Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$").matcher(dateStr).matches();
    }

    @Tool(description = "70. Check if a string is a valid numeric value")
    public boolean isNumeric(@ToolParam(description = "String to check") String str) {
        return Pattern.compile("-?\\d+(\\.\\d+)?").matcher(str).matches();
    }
}
