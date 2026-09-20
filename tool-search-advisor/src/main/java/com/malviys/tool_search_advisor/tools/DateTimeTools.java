package com.malviys.tool_search_advisor.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

public class DateTimeTools {
    @Tool(description = "Get the current date & time in user's timezone")
    public String getCurrentDateTime() {
        return LocalDateTime.now(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }
}
