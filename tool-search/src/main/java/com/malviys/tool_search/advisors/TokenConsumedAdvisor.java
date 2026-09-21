package com.malviys.tool_search.advisors;

import com.google.common.util.concurrent.AtomicDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.model.tool.ToolCallingChatOptions;

import java.util.concurrent.atomic.AtomicInteger;

public class TokenConsumedAdvisor implements BaseAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(TokenConsumedAdvisor.class);

    private final AtomicInteger requests = new AtomicInteger();

    private final AtomicInteger promptToken = new AtomicInteger();

    private final AtomicInteger completionToken = new AtomicInteger();

    private final AtomicInteger totalToken = new AtomicInteger();

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        var chatOptions = chatClientRequest.prompt().getOptions();

        if (!(chatOptions instanceof ToolCallingChatOptions toolCallingChatOptions)) {
            return chatClientRequest;
        }

        logger.info("{}", toolCallingChatOptions.getMaxTokens());

        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        var chatResponse = chatClientResponse.chatResponse();

        if (chatResponse == null) {
            return chatClientResponse;
        }

        var usage = chatResponse.getMetadata().getUsage();
        requests.incrementAndGet();
        totalToken.addAndGet(usage.getTotalTokens());
        completionToken.addAndGet(usage.getCompletionTokens());
        promptToken.addAndGet(usage.getPromptTokens());

        logger.info("token consumed: {} prompt tokens, {} completion tokens and {} total tokens", promptToken, completionToken, totalToken);

        return chatClientResponse;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
