package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// ==========================================
// 2. GENERAL MATH & CALCULATIONS (Tools 11 - 20)
// ==========================================
@Component
public class MathTools {

    @Tool(description = "11. Calculate tip, split total per person, and grand total")
    public Map<String, Double> calculateTip(
            @ToolParam(description = "Bill amount before tip") double bill,
            @ToolParam(description = "Tip percentage (e.g. 15, 18, 20)") double tipPercent,
            @ToolParam(description = "Number of people splitting", required = false) Integer splitBy) {
        double tip = bill * (tipPercent / 100.0);
        double total = bill + tip;
        int people = (splitBy != null && splitBy > 0) ? splitBy : 1;
        return Map.of("tipAmount", tip, "totalAmount", total, "perPerson", total / people);
    }

    @Tool(description = "12. Calculate percentage discount and savings")
    public Map<String, Double> calculateDiscount(
            @ToolParam(description = "Original price") double originalPrice,
            @ToolParam(description = "Discount percent (e.g. 25 for 25% off)") double percentOff) {
        double discount = originalPrice * (percentOff / 100.0);
        return Map.of("finalPrice", originalPrice - discount, "savings", discount);
    }

    @Tool(description = "13. Compute mean, median, min, max from list of numbers")
    public Map<String, Double> calculateStats(@ToolParam(description = "List of numbers") List<Double> numbers) {
        if (numbers.isEmpty()) return Map.of();
        double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
        double min = numbers.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double max = numbers.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        return Map.of("mean", sum / numbers.size(), "min", min, "max", max);
    }

    @Tool(description = "14. Calculate compound growth or interest over time")
    public double calculateCompoundGrowth(
            @ToolParam(description = "Initial value") double principal,
            @ToolParam(description = "Annual growth rate percentage") double rate,
            @ToolParam(description = "Years") int years) {
        return principal * Math.pow(1 + (rate / 100.0), years);
    }

    @Tool(description = "15. Solve greatest common divisor (GCD) of two integers")
    public int computeGcd(
            @ToolParam(description = "First integer") int a,
            @ToolParam(description = "Second integer") int b) {
        return b == 0 ? Math.abs(a) : computeGcd(b, a % b);
    }

    @Tool(description = "16. Calculate Body Mass Index (BMI) and health category")
    public Map<String, Object> calculateBmi(
            @ToolParam(description = "Weight in kilograms") double weightKg,
            @ToolParam(description = "Height in meters") double heightMeters) {
        double bmi = weightKg / (heightMeters * heightMeters);
        String category = (bmi < 18.5) ? "Underweight" : (bmi < 25) ? "Normal" : (bmi < 30) ? "Overweight" : "Obese";
        return Map.of("bmi", Math.round(bmi * 10.0) / 10.0, "category", category);
    }

    @Tool(description = "17. Calculate percentage value (e.g. What is X percent of Y)")
    public double calculatePercentage(
            @ToolParam(description = "Percentage, e.g. 15") double percent,
            @ToolParam(description = "Total number") double total) {
        return (percent / 100.0) * total;
    }

    @Tool(description = "18. Calculate fuel efficiency and trip cost")
    public Map<String, Double> calculateTripFuelCost(
            @ToolParam(description = "Total distance in km or miles") double distance,
            @ToolParam(description = "Fuel economy (distance per unit)") double fuelEfficiency,
            @ToolParam(description = "Fuel cost per unit") double pricePerUnit) {
        double fuelNeeded = distance / fuelEfficiency;
        return Map.of("fuelUnitsNeeded", fuelNeeded, "estimatedCost", fuelNeeded * pricePerUnit);
    }

    @Tool(description = "19. Check if a number is prime")
    public boolean checkPrime(@ToolParam(description = "Integer to check") int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Tool(description = "20. Clamp a numeric value between minimum and maximum bounds")
    public double clampNumber(
            @ToolParam(description = "Value to clamp") double val,
            @ToolParam(description = "Min bound") double min,
            @ToolParam(description = "Max bound") double max) {
        return Math.max(min, Math.min(val, max));
    }
}

