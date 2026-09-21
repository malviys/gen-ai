package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HexFormat;

// ==========================================
// 9. ENCODING & HASHING (Tools 81 - 90)
// ==========================================
@Component
public class EncodingTools {

    @Tool(description = "81. Base64 encode a plain text string")
    public String encodeBase64(@ToolParam(description = "Raw text") String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    @Tool(description = "82. Base64 decode an encoded string back to plain text")
    public String decodeBase64(@ToolParam(description = "Base64 encoded string") String encoded) {
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }

    @Tool(description = "83. URL-encode special characters in a query parameter or URL")
    public String urlEncode(@ToolParam(description = "Raw string") String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    @Tool(description = "84. URL-decode encoded string back to human-readable form")
    public String urlDecode(@ToolParam(description = "Encoded URL string") String encoded) {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }

    @Tool(description = "85. Calculate SHA-256 cryptographic hash of a text string")
    public String computeSha256(@ToolParam(description = "Text to hash") String input) throws Exception {
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }

    @Tool(description = "86. Calculate MD5 checksum of a string")
    public String computeMd5(@ToolParam(description = "Text to hash") String input) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(input.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }

    @Tool(description = "87. Convert text to hexadecimal representation")
    public String textToHex(@ToolParam(description = "Text to convert") String text) {
        return HexFormat.of().formatHex(text.getBytes(StandardCharsets.UTF_8));
    }

    @Tool(description = "88. Convert hexadecimal string back to readable text")
    public String hexToText(@ToolParam(description = "Hex string") String hex) {
        return new String(HexFormat.of().parseHex(hex), StandardCharsets.UTF_8);
    }

    @Tool(description = "89. Format raw bytes count into human readable units (KB, MB, GB)")
    public String formatBytesHumanReadable(@ToolParam(description = "Byte count") long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), unit);
    }

    @Tool(description = "90. Escape HTML special characters (&, <, >, \", ') to safe entities")
    public String escapeHtmlEntities(@ToolParam(description = "Raw text") String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
