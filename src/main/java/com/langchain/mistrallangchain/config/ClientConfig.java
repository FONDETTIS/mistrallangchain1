package com.langchain.mistrallangchain.config;

import java.io.InputStream;
import java.util.Properties;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Scanner;



import org.springframework.ai.model.chat.client.autoconfigure.ChatClientBuilderConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;


import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel.MistralAiChatModelBuilder;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel.MistralAiChatModelBuilder;
import dev.langchain4j.model.mistralai.MistralAiChatModelName;



@Configuration
@PropertySource("classpath:application.properties")
//public class ClientConfig<MistralAiChatModelBuilderConfigurer> {
public class ClientConfig {
	
	
//	@Bean
//    public MistralAiChatModel mistralAiChatModel(MistralAiApi mistralAiApi) {
//        // Define default settings for the model
//        MistralAiChatOptions defaultOptions = MistralAiChatOptions.builder()
//                .withModel("mistral-tiny")
//                .withTemperature(0.7)
//                .withMaxTokens(500)
//                .build();
//
//        // Instantiate and return the ChatModel bean
//        return new MistralAiChatModel(mistralAiApi, defaultOptions);
//    }
	
	
//	@Bean
//	RestClient restClient(RestClient.Builder builder) {
//			return builder	
//				.baseUrl("https://api.example.com")
//              .defaultHeader("Content-Type", "application/json")
//              .build();
//				
//	}
	
	@Bean
    public ChatModel chatModel1() {
        return MistralAiChatModel.builder()
        		.baseUrl("https://api.mistral.ai/v1")
                .apiKey(System.getenv("MISTRAL_API_KEY"))
                .modelName("mistral-small-latest")
                .temperature(0.7)
                .maxTokens(1024)
                .build();
    }
	
	
		
	@Bean
    public ChatModel chatModel2() {
        return MistralAiChatModel.builder()
        		.baseUrl("https://api.mistral.ai/v1")
                .apiKey(System.getenv("MISTRAL_API_KEY"))
                .modelName("mistral-tiny")
                .temperature(0.7)
                .maxTokens(1024)
                .build();
    }
	
		
		
	@Bean
	public MistralAiChatModel modelbean() {
		return MistralAiChatModel.builder() 
				.baseUrl("https://api.mistral.ai/v1")
				.apiKey(System.getenv("MISTRAL_AI_API_KEY"))
				.modelName("mistral-tiny")
				.logRequests(true)
				.logResponses(true)
				.temperature(0.7)
                .maxTokens(1024)
                //.maxLength(10)
				.build();
	}
	
	@Bean
	public ChatModel modelbean2() {
		return MistralAiChatModel.builder() 
				.baseUrl("https://api.mistral.ai/v1")
				.apiKey(System.getenv("MISTRAL_AI_API_KEY"))
				.modelName("mistral-tiny")
				//.logRequests(true)
				//.logResponses(true)
				//.temperature(0.7)
                //.maxTokens(1024)
				.build();
	}
	
	
	
//	MistralAiChatModel model2 = MistralAiChatModel.builder()
//			.apiKey(System.getenv("MISTRAL_AI_API_KEY")) // Please use your own Mistral AI API key
//			.modelName("mistral-tiny")
//			.logRequests(true)
//			.logResponses(true)
//			.build();
	

		
	
	
	
//	@Bean
//	ChatClient chatClient(ChatClient.Builder builder) {
//			return builder.build();
//	}
	
	
/*	
	@Bean
    ChatClient defaultChatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean
    ChatClient customChatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("You are a helpful assistant.").build();
    }
    
    
    //For the common single-model case you don't need ChatClientBuilderConfigurer at all
    @Bean
    @Qualifier
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder
            .defaultSystem("You are a helpful assistant")
            .build();
    }
    
    //it customizes the builder, it does not set MistralAiChatOptions. 
    //Default model options belong on the MistralChatModel bean (via MistralChatOptions.builder()...
    @Bean
    ChatClientBuilderCustomizer mistralDefaultsCustomizer() {
        return builder -> builder.defaultSystem("You are a helpful assistant");
    }
    
    @Bean
    @Primary
    //FLORENT COUDERT c'est là que l'on a le problème.
    public ChatClient mistralAiChatClient(MistralAiChatModel chatModel,
    		  ChatClientBuilderConfigurer configurer
//            ObjectProvider<ObservationRegistry> observationRegistry,
//            ObjectProvider<ChatClientObservationConvention> chatClientObservationConvention,
//            ObjectProvider<AdvisorObservationConvention> advisorObservationConvention,
//            ObjectProvider<ToolCallingAdvisor.Builder<?>> toolCallingAdvisorBuilder
    		)
    {
//        return buildChatClient(chatModel, configurer,
//                chatClientObservationConvention, advisorObservationConvention, toolCallingAdvisorBuilder);
    	//return buildChatClient(chatModel, configurer, null, null, null);
    	return buildMistralAiChatClient(chatModel, configurer);
    	
	}
    
     
    //MODELS
    @Bean
    @Primary
    public MistralAiChatModel chatModel1(MistralAiApi mistralAiApi) {
        
    	MistralAiChatOptions options1 = MistralAiChatOptions.builder()
                .temperature(0.7)       // Controls randomness (0.0 = deterministic, 1.0 = creative)
                .maxTokens(500)         // Maximum tokens to generate
                .topP(0.9)              // Nucleus sampling (0.0 to 1.0)
                .build();
    	
    	MistralAiChatModel chatModel1 = new MistralAiChatModel(mistralAiApi, options1,null,null,null);
    	
    	return chatModel1;
    }
    
    @Bean
    public MistralAiChatModel chatModel2(MistralAiApi mistralAiApi) {
    	
        MistralAiChatOptions options2 = MistralAiChatOptions.builder()
            .temperature(0.5)               // Lower temperature for more deterministic output
            .maxTokens(1000)                // Generate up to 1000 tokens
            .topP(0.8)                      // Nucleus sampling threshold
            .topK(40)                       // Top-k sampling
            .stopSequences(List.of("\n\n")) // Stop generation on double newline
            .randomSeed((int) 42L)          // Fixed random seed for reproducibility
            .presencePenalty(0.1)          // Slight penalty for repeating tokens
            .frequencyPenalty(0.2)         // Slight penalty for frequent tokens
            .build();
        
        MistralAiChatModel chatModel2 = new MistralAiChatModel(mistralAiApi, options2,null,null,null);

        return chatModel2;
    }
    

    
    
//    @Bean
//    public ChatClient anthropicChatClient(AnthropicChatModel chatModel, ChatClientBuilderConfigurer configurer,
//            ObjectProvider<ObservationRegistry> observationRegistry,
//            ObjectProvider<ChatClientObservationConvention> chatClientObservationConvention,
//            ObjectProvider<AdvisorObservationConvention> advisorObservationConvention,
//            ObjectProvider<ToolCallingAdvisor.Builder<?>> toolCallingAdvisorBuilder) {
//        return buildChatClient(chatModel, configurer, observationRegistry,
//                chatClientObservationConvention, advisorObservationConvention, toolCallingAdvisorBuilder);
//    }

//    private ChatClient buildChatClient(ChatModel chatModel, ChatClientBuilderConfigurer configurer,
//            ObjectProvider<ObservationRegistry> observationRegistry,
//            ObjectProvider<ChatClientObservationConvention> chatClientObservationConvention,
//            ObjectProvider<AdvisorObservationConvention> advisorObservationConvention,
//            ObjectProvider<ToolCallingAdvisor.Builder<?>> toolCallingAdvisorBuilder) {
//        ChatClient.Builder builder = ChatClient.builder(chatModel,
//                observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP),
//                chatClientObservationConvention.getIfUnique(),
//                advisorObservationConvention.getIfUnique(),
//                toolCallingAdvisorBuilder.getIfAvailable());
//        return configurer.configure(builder).build();
//    }
    
    
    
  //BUILDERS
  private ChatClient buildChatClient(
		  ChatModel chatModel, 
		  ChatClientBuilderConfigurer configurer)
	  {
	  		ChatClient.Builder builder = ChatClient.builder(chatModel,null,null,null,null);
	  		
	  		return ((org.springframework.ai.model.chat.client.autoconfigure.ChatClientBuilderConfigurer) configurer).configure(builder).build();
	   }
  
  
  public ChatClient buildMistralAiChatClient(
          ChatModel mistralAiChatModel,
          ChatClientBuilderConfigurer configurer) 
  	{
	  
	  ChatClient.Builder builder = ChatClient.builder(mistralAiChatModel,null,null,null,null);
      
      return ((org.springframework.ai.model.chat.client.autoconfigure.ChatClientBuilderConfigurer) configurer).configure(builder).build();
  	}
  	
*/
  	
  	 
	  		

}


