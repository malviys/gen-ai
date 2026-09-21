package com.malviys.tool_search;

import org.springframework.ai.tool.toolsearch.ToolIndex;
import org.springframework.ai.tool.toolsearch.index.lucene.LuceneToolIndex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToolSearchApplication {

	@Bean
	ToolIndex toolIndex() {
		return new LuceneToolIndex();
	}

	public static void main(String[] args) {
		SpringApplication.run(ToolSearchApplication.class, args);
	}


}
