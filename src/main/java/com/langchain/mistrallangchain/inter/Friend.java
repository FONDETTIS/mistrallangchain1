package com.langchain.mistrallangchain.inter;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.ArrayList;

import dev.langchain4j.data.message.AudioContent;
import dev.langchain4j.data.message.Content;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.lang.annotation.*;

@SystemMessage("You are a good friend of mine. Answer using slang.")
public interface Friend {
	
		
	
	@UserMessage("You are a good friend of mine. Answer using slang. {{it}}")
    String chat(String userMessage);
	
	String chatOther(@V("message") String userMessage);

	@UserMessage(fromResource = "my-prompt-template.txt")
    String chatAgain(String userMessage);
	
	String chat(@UserMessage String userMessage, ChatRequestParameters parameters);

	String chat(@UserMessage String userMessage, @V("country") String country); 
	
	String chat(@UserMessage String userMessage, @UserMessage Content content);

	String chat(@UserMessage String userMessage, @UserMessage ImageContent image);

	String chat(@UserMessage String userMessage, @UserMessage ImageContent image, @UserMessage AudioContent audio);

	String chat(@UserMessage String userMessage, @UserMessage List<Content> contents);

	//String chat(@UserMessage String userMessage, @UserMessage List<ImageContent> images);

	String chat(Content content);

	String chat(AudioContent content);

	String chat(List<Content> contents);

	//String chat(List<AudioContent> contents);

	String chat(@UserMessage Content content1, @UserMessage Content content2);

	String chat(@UserMessage AudioContent audio, @UserMessage ImageContent image);
	
	
	
	
	@UserMessage("What is the capital of {{it}}?")
	String chatEncore(String country);

	@UserMessage("What is the capital of {{country}}?")
	String chatPlus(@V("country") String country);
    
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(AiConstraints.class)
    public @interface AiConstraint {
        String value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface AiConstraints {
        AiConstraint[] value();
    }

}
