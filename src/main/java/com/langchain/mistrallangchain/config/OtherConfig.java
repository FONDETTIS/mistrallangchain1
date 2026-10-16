package com.langchain.mistrallangchain.config;

import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel.MistralAiChatModelBuilder;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel.MistralAiChatModelBuilder;
import dev.langchain4j.model.mistralai.MistralAiChatModelName;




@Configuration
@PropertySource("classpath:application.properties")
public class OtherConfig {
	
//	@Bean
//    public ChatClient.Builder chatClientBuilder(MistralAiChatModel mistralChatModel) {
//        return ChatClient.builder(mistralChatModel);
//    }
//
	@Bean
    public ChatModel otherchatModel() {
        return MistralAiChatModel.builder()
        	   	  .baseUrl("https://api.mistral.ai/v1")
                .apiKey(System.getenv("MISTRAL_API_KEY"))
                .modelName("mistral-small-latest")
                .temperature(0.7)
                .maxTokens(1024)
                .build();
    }

	@Bean
	public InMemoryEmbeddingStore<TextSegment> embeddingStore()	{
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		return embeddingStore;
	}
	
	@Bean
	public InMemoryEmbeddingStore<TextSegment> embeddingStorebis()	{
		InMemoryEmbeddingStore<TextSegment> embeddingStorebis = new InMemoryEmbeddingStore<>();
		return embeddingStorebis;
	}
	
	
	
		
//	
//	@Bean
//    public ChatClient.Builder chatClientBuilder(ChatModel chatModel) {
//        return ChatClient.builder(chatModel);
//    }


}
