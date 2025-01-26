package com.service.firstapp.controller;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class DemoController {
    private static final org. slf4j. Logger logger
            = LoggerFactory. getLogger(DemoController.class);


    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() throws ExecutionException, InterruptedException {
        logger.info("hello from fist service");
        // Simulate work
        Span parentSpan = Span.current();
        parentSpan.addEvent("Starting main flow");

        // Capture the current context
        Context parentContext = Context.current();

        // Pass context to a CompletableFuture
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try (Scope scope = parentContext.makeCurrent()) {
                // The trace context is active here
                Span currentSpan = Span.current();
                currentSpan.addEvent("Async operation started");

                // Simulate work
                performAsyncTask();

                currentSpan.addEvent("Async operation completed");
            }
        });
        future.get();
        return  "hello";
    }
    @GetMapping("/sayHello")
    public String sayHello(){
        logger.info("hello from fist service");
        return  "sayHello";
    }

    @GetMapping("/hello1")
    public String helloToSecond(){
        logger.info("say hello");
       return restTemplate.getForEntity("http://localhost:8081/hello2",String.class).toString();
    }

    private static void performAsyncTask() {
        logger.info("performAsyncTask");
    }
}
