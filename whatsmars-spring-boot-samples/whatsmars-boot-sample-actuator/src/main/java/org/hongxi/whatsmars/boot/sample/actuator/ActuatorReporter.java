package org.hongxi.whatsmars.boot.sample.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenhongxi on 2020/7/30.
 */
@Slf4j
public class ActuatorReporter implements CommandLineRunner {

    @Autowired(required = false)
    private MetricsEndpoint metricsEndpoint;

    private ExecutorService executorService;

    @Override
    public void run(String... args) throws Exception {
        log.info("metrics endpoint is open : {}", metricsEndpoint != null);
        if (metricsEndpoint != null) {
            Set<String> names = metricsEndpoint.listNames().getNames();
            if (names.isEmpty()) {
                return;
            }

            executorService = Executors.newFixedThreadPool(4);
            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                    () -> report(names), 1, 3, TimeUnit.SECONDS
            );
        }
    }

    private void report(Set<String> names) {
        names.forEach(name -> {
            executorService.submit(() -> {
                // store in time series database
            });
        });
    }
}
