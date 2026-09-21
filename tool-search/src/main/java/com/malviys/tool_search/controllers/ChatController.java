package com.malviys.tool_search.controllers;

import com.malviys.tool_search.advisors.TokenConsumedAdvisor;
import com.malviys.tool_search.tools.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.toolsearch.ToolSearchToolCallingAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.toolsearch.index.lucene.LuceneToolIndex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping
    public String chat(@RequestParam(name = "query") String query) {

        return chatClient.prompt()
                .advisors(new TokenConsumedAdvisor())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, UUID.randomUUID().toString()))
                .tools(
                        new DateTimeTools(),
                        new DecisionTools(),
                        new EncodingTools(),
                        new LifestyleTools(),
                        new MathTools(),
                        new ProductivityTools(),
                        new PublicInfoTools(),
                        new TextTools(),
                        new UnitTools(),
                        new ValidationTools()
                )
                .user(query)
                .call()
                .content();
    }

    @GetMapping("/search-advisor")
    public String chatWithAdvisor(@RequestParam(name = "query") String query) {
        var toolSearchAdvisor = ToolSearchToolCallingAdvisor.builder()
                .toolIndex(new LuceneToolIndex())
                .build();

        return chatClient.prompt()
                .advisors(toolSearchAdvisor, new TokenConsumedAdvisor())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, UUID.randomUUID().toString()))
                .tools(
                        new DateTimeTools(),
                        new DecisionTools(),
                        new EncodingTools(),
                        new LifestyleTools(),
                        new MathTools(),
                        new ProductivityTools(),
                        new PublicInfoTools(),
                        new TextTools(),
                        new UnitTools(),
                        new ValidationTools()
                )
                .user(query)
                .call()
                .content();
    }
}
