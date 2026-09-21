package com.malviys.tool_search.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// ==========================================
// 5. PERSONAL PRODUCTIVITY & TASKS (Tools 41 - 50)
// ==========================================
@Component
public class ProductivityTools {

    private final Map<String, String> notesStorage = new ConcurrentHashMap<>();
    private final List<Map<String, Object>> todoList = Collections.synchronizedList(new ArrayList<>());

    @Tool(description = "41. Save a quick text note with an assigned title")
    public boolean saveNote(
            @ToolParam(description = "Unique note title or key") String title,
            @ToolParam(description = "Note body") String content) {
        notesStorage.put(title.toLowerCase(), content);
        return true;
    }

    @Tool(description = "42. Read a saved text note by title")
    public String getNote(@ToolParam(description = "Note title") String title) {
        return notesStorage.getOrDefault(title.toLowerCase(), "Note not found.");
    }

    @Tool(description = "43. List all saved note titles")
    public List<String> listAllNotes() {
        return new ArrayList<>(notesStorage.keySet());
    }

    @Tool(description = "44. Delete a saved note")
    public boolean deleteNote(@ToolParam(description = "Note title") String title) {
        return notesStorage.remove(title.toLowerCase()) != null;
    }

    @Tool(description = "45. Add an item to user's daily to-do list")
    public String addTodo(
            @ToolParam(description = "Task description") String task,
            @ToolParam(description = "Priority: LOW, MEDIUM, HIGH", required = false) String priority) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        todoList.add(Map.of("id", id, "task", task, "priority", priority != null ? priority : "MEDIUM", "done", false));
        return id;
    }

    @Tool(description = "46. View all active to-do list items")
    public List<Map<String, Object>> getActiveTodos() {
        return todoList.stream().filter(t -> !((Boolean) t.get("done"))).toList();
    }

    @Tool(description = "47. Mark a to-do item as completed")
    public boolean markTodoDone(@ToolParam(description = "Todo ID") String todoId) {
        for (int i = 0; i < todoList.size(); i++) {
            if (todoId.equals(todoList.get(i).get("id"))) {
                Map<String, Object> updated = new HashMap<>(todoList.get(i));
                updated.put("done", true);
                todoList.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Tool(description = "48. Set a reminder alert message for a future ISO datetime")
    public String createReminder(
            @ToolParam(description = "Reminder message") String text,
            @ToolParam(description = "Target trigger ISO datetime") String triggerTime) {
        return "REMINDER-" + UUID.randomUUID().toString().substring(0, 6) + " scheduled for " + triggerTime;
    }

    @Tool(description = "49. Calculate daily recommended water intake based on body weight")
    public double getRecommendedWaterLiters(
            @ToolParam(description = "Weight in kg") double weightKg,
            @ToolParam(description = "Workout minutes", required = false) Integer workoutMins) {
        double base = weightKg * 0.033;
        double exerciseBonus = (workoutMins != null ? workoutMins : 0) * 0.012;
        return Math.round((base + exerciseBonus) * 10.0) / 10.0;
    }

    @Tool(description = "50. Generate a daily study or work Pomodoro schedule breakdown")
    public Map<String, Object> planPomodoroBlocks(
            @ToolParam(description = "Available hours") int availableHours,
            @ToolParam(description = "Focus duration minutes (e.g. 25, 50)") int focusMins) {
        int totalMinutes = availableHours * 60;
        int cycleDuration = focusMins + 5;
        int cycles = totalMinutes / cycleDuration;
        return Map.of("totalFocusMinutes", cycles * focusMins, "totalCycles", cycles, "breakMinutes", cycles * 5);
    }
}
