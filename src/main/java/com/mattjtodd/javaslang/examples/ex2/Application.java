package com.mattjtodd.javaslang.examples.ex2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;

@EnableAsync
@SpringBootApplication
public class Application {

    static final String ENCODING_EXECUTOR = "encodingExecutor";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // First Pool in our bulkhead pattern
    @Bean(name = ENCODING_EXECUTOR)
    public ExecutorService getAsyncExecutor() {

        // Hash function calculation is CPU bound so we should have thread count set to cpu count
        int poolSize = Runtime.getRuntime().availableProcessors();

        return null;
    }

//    @Bean
    public void random() {
        // a way to define the generation of a random number.

        // "NativePRNGNonBlocking", "SHA1PRNG", "Sun", then a default constructed.
        // SecureRandom.getInstance(...)

//        return null;
    }
}
