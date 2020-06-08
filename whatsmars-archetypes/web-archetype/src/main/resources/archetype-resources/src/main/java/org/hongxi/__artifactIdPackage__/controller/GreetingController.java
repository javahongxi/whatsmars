package org.hongxi.${artifactIdPackage}.controller;

import com.hongxi.${artifactIdPackage}.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name") String name) {
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch(Exception e) {}

        return new Greeting(counter.incrementAndGet(),
                String.format("Hello, %s!", name));
    }

}