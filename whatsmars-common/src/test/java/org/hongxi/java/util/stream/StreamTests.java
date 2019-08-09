package org.hongxi.java.util.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by shenhongxi on 2019/4/14.
 */
public class StreamTests {

    @Test
    public void t() {
        List<String> words = new ArrayList<>();
        words.add("a");
        words.add("b");
        words.add("c");
        words = words.stream().filter(e -> e.equals("d")).collect(Collectors.toList());
        assert words.isEmpty();
    }

    @Test
    public void t2() {
        List<String> words = new ArrayList<>();
        words.add("a");
        words.add("b");
        words.add("c");
        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
        words.parallelStream().forEach(e -> result.add(e.toUpperCase()));
        assert result.size() == words.size();
    }

    @Test
    public void t3() {
        List<String> words = new ArrayList<>();
        words.add("a");words.add("b");words.add("c");words.add("d");words.add("e");words.add("f");
        words.add("g");words.add("h");words.add("i");words.add("j");words.add("k");words.add("l");
        words.add("m");words.add("n");words.add("o");words.add("p");words.add("q");words.add("r");
        words.add("s");words.add("t");words.add("u");words.add("v");words.add("w");words.add("x");
        words.add("y");words.add("z");
        Map<String, String> result = words.parallelStream()
                .collect(HashMap::new, (m, word) -> m.put(word, word.toUpperCase()), HashMap::putAll);
        System.out.println(result);
        assert result.size() == words.size();
    }
}
