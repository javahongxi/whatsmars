package org.hongxi.java.util;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Base64;

public class Java8Test {

    @Test
    public void join() {
        String[] countries = {"China", "America", "Japan"};
        System.out.println(String.join(",", countries));
    }

    @Test
    public void base64() {
        String text = "Base64 finally in Java 8!";
        String encoded = Base64
                .getEncoder()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);

        String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        System.out.println(decoded);
    }

    @Test
    public void script() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        System.out.println(engine.getClass().getName());
        System.out.println("Result:" + engine.eval("function f() { return 1; }; f() + 1;"));
    }

    @Test
    public void clock() {
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());
        LocalDate localDate = LocalDate.now(); // LocalDate.now(clock)
        System.out.println(localDate);
        LocalTime localTime = LocalTime.now(); // LocalTime.now(clock)
        System.out.println(localTime);
        LocalDateTime localDateTime = LocalDateTime.now(); // LocalDateTime.now(clock)
        System.out.println(localDateTime);

        LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
        LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );
        Duration duration = Duration.between(from, to);
        System.out.println("Duration in days: " + duration.toDays());
        System.out.println("Duration in hours: " + duration.toHours());
    }
}
