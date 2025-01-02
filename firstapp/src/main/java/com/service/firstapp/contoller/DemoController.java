package com.service.firstapp.contoller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {
    private static final org. slf4j. Logger logger
            = LoggerFactory. getLogger(DemoController.class);


    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(){
        logger.info("hello");
        return  "hello";
    }
    @GetMapping("/hello1")
    public String helloToSecond(){
        logger.info("hello to second service");
       return restTemplate.getForEntity("http://localhost:8081/hello2",String.class).toString();
    }
}
