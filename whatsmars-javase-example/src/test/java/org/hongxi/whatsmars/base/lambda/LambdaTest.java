package org.hongxi.whatsmars.base.lambda;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by javahongxi on 2018/1/2.
 */
public class LambdaTest {

    @Test
    public void t() {
        new Thread(() -> System.out.println("Hi, I am lambda!")).start();
        System.out.println("main thread ...");
    }

    @Test
    public void t2() {
        Arrays.asList("a", "b", "c").forEach(e -> System.out.println(e));
    }

    @Test
    public void t3() {
        Arrays.asList("a", "b", "c").forEach(e -> {
            System.out.println(System.currentTimeMillis());
            System.out.println(e);
        });
    }

    @Test
    public void t4() {
        List<String> names = new ArrayList<>();
        names.add("d");
        names.add("a");
        names.add("ax");
        names.add("bbb");
        names.sort(String::compareTo);
        System.out.println(names);
    }

    @Test
    public void t5() {
        Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("Full Name is set? " + fullName.isPresent());
        System.out.println("Full Name: " + fullName.orElseGet(() -> "[none]"));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));
    }

    @Test
    public void t6() {
        long[] arr = new long [20000];

        Arrays.parallelSetAll(arr,
                index -> ThreadLocalRandom.current().nextInt(1000000));
        Arrays.stream(arr).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();

        Arrays.parallelSort(arr);
        Arrays.stream(arr).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();
    }

    @Test
    public void t7() {
        String text = "Base64 finally in Java 8!";
        String encoded = Base64
                .getEncoder()
                .encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);

        String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        System.out.println(decoded);
    }

    @Test
    public void t8() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        System.out.println(engine.getClass().getName());
        System.out.println("Result:" + engine.eval("function f() { return 1; }; f() + 1;"));
    }

    @Test
    public void t9() {
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
