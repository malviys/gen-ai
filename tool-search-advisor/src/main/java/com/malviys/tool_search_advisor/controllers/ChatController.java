package com.malviys.tool_search_advisor.controllers;

import com.malviys.tool_search_advisor.advisors.AvailableToolsLoggingAdvisor;
import com.malviys.tool_search_advisor.tools.DateTimeTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping({"/before"})
    public String before() {
        return chatClient
                .prompt()
                .advisors(new AvailableToolsLoggingAdvisor())
                .tools(new DateTimeTools())
                .user("What is the capital of India and current date & time")
                .call()
                .content();
    }
}
