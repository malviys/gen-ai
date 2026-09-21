package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// ==========================================
// 6. DECISIONS & RANDOMIZERS (Tools 51 - 60)
// ==========================================
@Component
public class DecisionTools {

    private final SecureRandom random = new SecureRandom();

    @Tool(description = "51. Randomly pick an item from a list of options")
    public String pickRandomOption(@ToolParam(description = "List of options to choose from") List<String> options) {
        if (options.isEmpty()) return "No options provided";
        return options.get(random.nextInt(options.size()));
    }

    @Tool(description = "52. Flip a coin (Heads or Tails)")
    public String flipCoin() {
        return random.nextBoolean() ? "Heads" : "Tails";
    }

    @Tool(description = "53. Roll multi-sided dice (e.g., roll 2 dice with 6 sides)")
    public List<Integer> rollDice(
            @ToolParam(description = "Number of dice to roll") int count,
            @ToolParam(description = "Sides per die (e.g. 6, 20)") int sides) {
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < Math.max(1, count); i++) {
            rolls.add(random.nextInt(sides) + 1);
        }
        return rolls;
    }

    @Tool(description = "54. Generate a random integer within a range [min, max]")
    public int getRandomNumber(
            @ToolParam(description = "Minimum value") int min,
            @ToolParam(description = "Maximum value") int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    @Tool(description = "55. Generate a secure random password with specific criteria")
    public String generatePassword(
            @ToolParam(description = "Length (default 12)") int length,
            @ToolParam(description = "Include symbols (!@#$)") boolean includeSymbols) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" + (includeSymbols ? "!@#$%^&*()" : "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.max(length, 8); i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Tool(description = "56. Shuffle or randomize the order of a list")
    public List<String> shuffleList(@ToolParam(description = "List to shuffle") List<String> list) {
        List<String> copy = new ArrayList<>(list);
        Collections.shuffle(copy, random);
        return copy;
    }

    @Tool(description = "57. Split people or items into equal teams or groups")
    public List<List<String>> splitIntoTeams(
            @ToolParam(description = "List of names") List<String> members,
            @ToolParam(description = "Number of teams to create") int teamCount) {
        List<String> shuffled = new ArrayList<>(members);
        Collections.shuffle(shuffled, random);
        List<List<String>> teams = new ArrayList<>();
        for (int i = 0; i < teamCount; i++) teams.add(new ArrayList<>());
        for (int i = 0; i < shuffled.size(); i++) {
            teams.get(i % teamCount).add(shuffled.get(i));
        }
        return teams;
    }

    @Tool(description = "58. Generate a random color hex code (#RRGGBB)")
    public String getRandomHexColor() {
        return String.format("#%06x", random.nextInt(0xFFFFFF + 1));
    }

    @Tool(description = "59. Generate a unique random UUID v4")
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }

    @Tool(description = "60. Perform a weighted lottery draw from options with weights")
    public String weightedRandomChoice(
            @ToolParam(description = "Options") List<String> items,
            @ToolParam(description = "Corresponding integer weights") List<Integer> weights) {
        int totalWeight = weights.stream().mapToInt(Integer::intValue).sum();
        int r = random.nextInt(totalWeight);
        int current = 0;
        for (int i = 0; i < items.size(); i++) {
            current += weights.get(i);
            if (r < current) return items.get(i);
        }
        return items.get(0);
    }
}
