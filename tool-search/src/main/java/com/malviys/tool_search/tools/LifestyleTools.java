package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

// ==========================================
// 10. LIFESTYLE, HOME & WELLNESS (Tools 91 - 100)
// ==========================================
@Component
public class LifestyleTools {

    @Tool(description = "91. Calculate egg boiling timer duration for soft, medium, or hard boiled")
    public int getEggBoilingTimeSeconds(
            @ToolParam(description = "Doneness: SOFT, MEDIUM, HARD") String doneness,
            @ToolParam(description = "Egg size: MEDIUM, LARGE", required = false) String size) {
        int base = "SOFT".equalsIgnoreCase(doneness) ? 360 : "MEDIUM".equalsIgnoreCase(doneness) ? 450 : 600;
        return "LARGE".equalsIgnoreCase(size) ? base + 60 : base;
    }

    @Tool(description = "92. Scale cooking recipe ingredients up or down for different serving sizes")
    public double scaleRecipeIngredient(
            @ToolParam(description = "Original ingredient amount") double originalAmount,
            @ToolParam(description = "Original serving count") int originalServings,
            @ToolParam(description = "Target serving count") int targetServings) {
        return (originalAmount / originalServings) * targetServings;
    }

    @Tool(description = "93. Estimate calories burned during common physical activities")
    public double calculateCaloriesBurned(
            @ToolParam(description = "Activity: WALKING, RUNNING, CYCLING, SWIMMING") String activity,
            @ToolParam(description = "Body weight in kg") double weightKg,
            @ToolParam(description = "Duration in minutes") int durationMins) {
        double met = switch (activity.toUpperCase()) {
            case "RUNNING" -> 9.8;
            case "CYCLING" -> 7.5;
            case "SWIMMING" -> 8.0;
            default -> 3.5;
        };
        return (durationMins * (met * 3.5 * weightKg)) / 200.0;
    }

    @Tool(description = "94. Recommend target heart rate zones for exercise")
    public Map<String, Integer> getTargetHeartRateZones(@ToolParam(description = "Age in years") int age) {
        int maxHr = 220 - age;
        return Map.of("fatBurnMin", (int) (maxHr * 0.6), "fatBurnMax", (int) (maxHr * 0.7),
                "aerobicMin", (int) (maxHr * 0.7), "aerobicMax", (int) (maxHr * 0.85));
    }

    @Tool(description = "95. Calculate coffee-to-water brewing ratio grams")
    public Map<String, Double> calculateCoffeeRatio(
            @ToolParam(description = "Desired water volume in milliliters") double waterMl,
            @ToolParam(description = "Brew strength: MILD (1:17), MEDIUM (1:15), STRONG (1:12)") String strength) {
        double ratio = "STRONG".equalsIgnoreCase(strength) ? 12.0 : "MILD".equalsIgnoreCase(strength) ? 17.0 : 15.0;
        double coffeeGrams = waterMl / ratio;
        return Map.of("coffeeGrams", Math.round(coffeeGrams * 10.0) / 10.0, "waterGrams", waterMl);
    }

    @Tool(description = "96. Calculate required paint gallons to cover a room's wall area")
    public double calculatePaintNeeded(
            @ToolParam(description = "Total wall square footage") double wallSqFt,
            @ToolParam(description = "Number of coats (usually 1 or 2)") int coats) {
        return Math.ceil((wallSqFt * coats) / 350.0);
    }

    @Tool(description = "97. Convert common clothing & shoe sizes between US, UK, and EU")
    public Map<String, Object> convertShoeSize(
            @ToolParam(description = "US Shoe size number") double usSize,
            @ToolParam(description = "Gender: MEN, WOMEN") String gender) {
        double eu = "WOMEN".equalsIgnoreCase(gender) ? usSize + 31 : usSize + 33;
        double uk = "WOMEN".equalsIgnoreCase(gender) ? usSize - 2 : usSize - 0.5;
        return Map.of("US", usSize, "UK", uk, "EU", eu);
    }

    @Tool(description = "98. Calculate pet food daily serving portion based on animal weight")
    public double calculatePetPortion(
            @ToolParam(description = "Pet type: DOG or CAT") String petType,
            @ToolParam(description = "Weight in kilograms") double weightKg) {
        return "CAT".equalsIgnoreCase(petType) ? weightKg * 15.0 : weightKg * 25.0;
    }

    @Tool(description = "99. Suggest indoor plant watering frequency based on season and sunlight")
    public String getPlantWateringGuide(
            @ToolParam(description = "Sunlight: DIRECT, INDIRECT, LOW_LIGHT") String light,
            @ToolParam(description = "Current season: SUMMER, WINTER, SPRING, FALL") String season) {
        if ("WINTER".equalsIgnoreCase(season) || "LOW_LIGHT".equalsIgnoreCase(light)) {
            return "Water once every 10-14 days. Ensure soil is dry 2 inches down.";
        }
        return "Water once every 4-7 days when top inch of soil feels dry.";
    }

    @Tool(description = "100. Estimate sleep cycles and optimal wake-up alarm times")
    public Map<String, String> calculateWakeTimes(
            @ToolParam(description = "Target bedtime (HH:MM in 24h format)") String bedtime) {
        String[] parts = bedtime.split(":");
        LocalTime bed = LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])).plusMinutes(15);
        return Map.of(
                "cycle4_6Hours", bed.plusMinutes(90 * 4).toString(),
                "cycle5_7.5Hours", bed.plusMinutes(90 * 5).toString(),
                "cycle6_9Hours", bed.plusMinutes(90 * 6).toString()
        );
    }
}
