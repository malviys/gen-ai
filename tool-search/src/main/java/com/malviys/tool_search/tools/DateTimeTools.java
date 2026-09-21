package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Map;

// ==========================================
// 1. DATE, TIME & CALENDAR (Tools 1 - 10)
// ==========================================
@Component
public class DateTimeTools {

    @Tool(description = "1. Get current date, time, and UTC offset")
    public String getCurrentDateTime() {
        return ZonedDateTime.now(ZoneId.of("UTC")).toString();
    }

    @Tool(description = "2. Calculate the difference in days between two ISO dates (YYYY-MM-DD)")
    public long daysBetween(
            @ToolParam(description = "Start date (YYYY-MM-DD)") String start,
            @ToolParam(description = "End date (YYYY-MM-DD)") String end) {
        return ChronoUnit.DAYS.between(LocalDate.parse(start), LocalDate.parse(end));
    }

    @Tool(description = "3. Add or subtract days from a given date")
    public String addDaysToDate(
            @ToolParam(description = "Base date (YYYY-MM-DD)") String baseDate,
            @ToolParam(description = "Number of days (can be negative)") int days) {
        return LocalDate.parse(baseDate).plusDays(days).toString();
    }

    @Tool(description = "4. Get the day of the week for a specific date")
    public String getDayOfWeek(@ToolParam(description = "Date (YYYY-MM-DD)") String date) {
        return LocalDate.parse(date).getDayOfWeek().name();
    }

    @Tool(description = "5. Convert a timestamp between timezones")
    public String convertTimezone(
            @ToolParam(description = "Date and time ISO string") String isoDateTime,
            @ToolParam(description = "Source IANA Zone ID, e.g. UTC") String fromZone,
            @ToolParam(description = "Target IANA Zone ID, e.g. America/New_York") String toZone) {
        ZonedDateTime src = LocalDateTime.parse(isoDateTime).atZone(ZoneId.of(fromZone));
        return src.withZoneSameInstant(ZoneId.of(toZone)).toString();
    }

    @Tool(description = "6. Check if a given year is a leap year")
    public boolean isLeapYear(@ToolParam(description = "Year (e.g. 2024)") int year) {
        return Year.of(year).isLeap();
    }

    @Tool(description = "7. Calculate exact age in years, months, and days from birthdate")
    public Map<String, Integer> calculateAge(@ToolParam(description = "Birthdate (YYYY-MM-DD)") String birthDate) {
        Period p = Period.between(LocalDate.parse(birthDate), LocalDate.now());
        return Map.of("years", p.getYears(), "months", p.getMonths(), "days", p.getDays());
    }

    @Tool(description = "8. Get Unix epoch timestamp in seconds")
    public long getUnixTimestamp() {
        return Instant.now().getEpochSecond();
    }

    @Tool(description = "9. Convert seconds into formatted human duration (HH:MM:SS)")
    public String formatDurationSeconds(@ToolParam(description = "Duration in seconds") long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    @Tool(description = "10. Calculate business days (Monday-Friday) between two dates")
    public int getBusinessDaysBetween(
            @ToolParam(description = "Start date (YYYY-MM-DD)") String start,
            @ToolParam(description = "End date (YYYY-MM-DD)") String end) {
        LocalDate cur = LocalDate.parse(start);
        LocalDate last = LocalDate.parse(end);
        int count = 0;
        while (!cur.isAfter(last)) {
            if (cur.getDayOfWeek() != DayOfWeek.SATURDAY && cur.getDayOfWeek() != DayOfWeek.SUNDAY) {
                count++;
            }
            cur = cur.plusDays(1);
        }
        return count;
    }
}
