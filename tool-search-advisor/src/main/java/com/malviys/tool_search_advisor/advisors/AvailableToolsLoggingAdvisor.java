package com.malviys.tool_search_advisor.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.model.tool.ToolCallingChatOptions;

public class AvailableToolsLoggingAdvisor implements BaseAdvisor {
    private static final int DEFAULT_ORDER = 100;

    private static final Logger logger = LoggerFactory.getLogger(AvailableToolsLoggingAdvisor.class);

    private final int order;

    public AvailableToolsLoggingAdvisor() {
        this(DEFAULT_ORDER);
    }

    public AvailableToolsLoggingAdvisor(int order) {
        this.order = order;
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        var chatOptions = chatClientRequest.prompt().getOptions();

        if (!(chatOptions instanceof ToolCallingChatOptions toolCallingChatOptions)) {
            return chatClientRequest;
        }

        var toolCallbacks = toolCallingChatOptions.getToolCallbacks();

        if (toolCallbacks == null || toolCallbacks.isEmpty()) {
            return chatClientRequest;
        }

        var toolNames = toolCallbacks.stream()
                .map(it -> it.getToolDefinition().name())
                .sorted()
                .toList();

        if (!toolNames.isEmpty()) {
            logger.info("Before LLM call, LLM has access to {} tools", toolNames);
        }


        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        var chatResponse = chatClientResponse.chatResponse();

        if (chatResponse == null) {
            return chatClientResponse;
        }

        var toolNames = chatResponse.getResults().stream()
                .map(Generation::getOutput)
                .flatMap(it -> it.getToolCalls().stream())
                .map(AssistantMessage.ToolCall::name)
                .sorted()
                .toList();

        if (!toolNames.isEmpty()) {
            logger.info("After LLM call, LLM called {} tools", toolNames);
        }

        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
