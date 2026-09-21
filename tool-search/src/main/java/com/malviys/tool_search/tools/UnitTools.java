package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;

// ==========================================
// 3. UNIT & MEASUREMENT CONVERSIONS (Tools 21 - 30)
// ==========================================
@Component
public class UnitTools {

    @Tool(description = "21. Convert temperature between Celsius, Fahrenheit, and Kelvin")
    public double convertTemperature(
            @ToolParam(description = "Value to convert") double val,
            @ToolParam(description = "From scale (C, F, K)") String from,
            @ToolParam(description = "To scale (C, F, K)") String to) {
        double c = from.equalsIgnoreCase("F") ? (val - 32) * 5 / 9 : from.equalsIgnoreCase("K") ? val - 273.15 : val;
        return to.equalsIgnoreCase("F") ? (c * 9 / 5) + 32 : to.equalsIgnoreCase("K") ? c + 273.15 : c;
    }

    @Tool(description = "22. Convert distance/length between kilometers, miles, meters, and feet")
    public double convertDistance(
            @ToolParam(description = "Length value") double val,
            @ToolParam(description = "From unit: km, miles, meters, feet") String from,
            @ToolParam(description = "To unit: km, miles, meters, feet") String to) {
        double meters = switch (from.toLowerCase()) {
            case "km" -> val * 1000;
            case "miles" -> val * 1609.344;
            case "feet" -> val * 0.3048;
            default -> val;
        };
        return switch (to.toLowerCase()) {
            case "km" -> meters / 1000.0;
            case "miles" -> meters / 1609.344;
            case "feet" -> meters / 0.3048;
            default -> meters;
        };
    }

    @Tool(description = "23. Convert weight between kilograms, pounds, ounces, and grams")
    public double convertWeight(
            @ToolParam(description = "Weight value") double val,
            @ToolParam(description = "From: kg, lbs, oz, g") String from,
            @ToolParam(description = "To: kg, lbs, oz, g") String to) {
        double grams = switch (from.toLowerCase()) {
            case "kg" -> val * 1000;
            case "lbs" -> val * 453.592;
            case "oz" -> val * 28.3495;
            default -> val;
        };
        return switch (to.toLowerCase()) {
            case "kg" -> grams / 1000.0;
            case "lbs" -> grams / 453.592;
            case "oz" -> grams / 28.3495;
            default -> grams;
        };
    }

    @Tool(description = "24. Convert kitchen cooking volume (tablespoons, teaspoons, cups, ml)")
    public double convertCookingVolume(
            @ToolParam(description = "Volume amount") double val,
            @ToolParam(description = "From: tsp, tbsp, cup, ml") String from,
            @ToolParam(description = "To: tsp, tbsp, cup, ml") String to) {
        double ml = switch (from.toLowerCase()) {
            case "tsp" -> val * 4.92892;
            case "tbsp" -> val * 14.7868;
            case "cup" -> val * 240.0;
            default -> val;
        };
        return switch (to.toLowerCase()) {
            case "tsp" -> ml / 4.92892;
            case "tbsp" -> ml / 14.7868;
            case "cup" -> ml / 240.0;
            default -> ml;
        };
    }

    @Tool(description = "25. Convert speed between km/h, mph, and knots")
    public double convertSpeed(
            @ToolParam(description = "Speed value") double val,
            @ToolParam(description = "From: kmh, mph, knots") String from,
            @ToolParam(description = "To: kmh, mph, knots") String to) {
        double kmh = switch (from.toLowerCase()) {
            case "mph" -> val * 1.60934;
            case "knots" -> val * 1.852;
            default -> val;
        };
        return switch (to.toLowerCase()) {
            case "mph" -> kmh / 1.60934;
            case "knots" -> kmh / 1.852;
            default -> kmh;
        };
    }

    @Tool(description = "26. Calculate straight-line distance in km between two coordinates")
    public double calculateCoordinatesDistance(
            @ToolParam(description = "Latitude 1") double lat1,
            @ToolParam(description = "Longitude 1") double lon1,
            @ToolParam(description = "Latitude 2") double lat2,
            @ToolParam(description = "Longitude 2") double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 6371.0 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    @Tool(description = "27. Convert digital storage units (bytes, KB, MB, GB, TB)")
    public double convertDigitalStorage(
            @ToolParam(description = "Size value") double val,
            @ToolParam(description = "From: b, kb, mb, gb, tb") String from,
            @ToolParam(description = "To: b, kb, mb, gb, tb") String to) {
        Map<String, Double> multipliers = Map.of("b", 1.0, "kb", 1024.0, "mb", Math.pow(1024, 2), "gb", Math.pow(1024, 3), "tb", Math.pow(1024, 4));
        double bytes = val * multipliers.getOrDefault(from.toLowerCase(), 1.0);
        return bytes / multipliers.getOrDefault(to.toLowerCase(), 1.0);
    }

    @Tool(description = "28. Convert land area between square meters, square feet, and acres")
    public double convertArea(
            @ToolParam(description = "Area value") double val,
            @ToolParam(description = "From: sqft, sqm, acre") String from,
            @ToolParam(description = "To: sqft, sqm, acre") String to) {
        double sqm = switch (from.toLowerCase()) {
            case "sqft" -> val * 0.092903;
            case "acre" -> val * 4046.86;
            default -> val;
        };
        return switch (to.toLowerCase()) {
            case "sqft" -> sqm / 0.092903;
            case "acre" -> sqm / 4046.86;
            default -> sqm;
        };
    }

    @Tool(description = "29. Convert angles between degrees and radians")
    public double convertAngle(
            @ToolParam(description = "Angle value") double val,
            @ToolParam(description = "Direction: DEG_TO_RAD or RAD_TO_DEG") String mode) {
        return "DEG_TO_RAD".equalsIgnoreCase(mode) ? Math.toRadians(val) : Math.toDegrees(val);
    }

    @Tool(description = "30. Convert tire pressure between PSI, Bar, and kPa")
    public double convertPressure(
            @ToolParam(description = "Pressure value") double val,
            @ToolParam(description = "From: psi, bar, kpa") String from,
            @ToolParam(description = "To: psi, bar, kpa") String to) {
        double bar = switch (from.toLowerCase()) {
            case "psi" -> val * 0.0689476;
            case "kpa" -> val * 0.01;
            default -> val;
        };
        return switch (to.toLowerCase()) {
            case "psi" -> bar / 0.0689476;
            case "kpa" -> bar * 100.0;
            default -> bar;
        };
    }
}
