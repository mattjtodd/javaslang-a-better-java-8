package com.mattjtodd.javaslang.examples.ex1;

import java.io.IOException;

/**
 * We have a subsystem consisting of three nodes each of which consumes messages from a message queue.
 * Each of the messages is handled in each service by an IO bound process.
 *
 * MQ -> A -> MQ -> B -> MQ - > C
 *
 * A task in  each service takes roughly:
 *
 * A ~1200ms
 * B ~500ms
 * C ~ 100ms
 *
 * We're looking to merge this pipeline into a service.
 *
 * Initially we'd like to model in a POC to compare the approaches.
 *
 * Using Javalangs FO constructs implement these two scenarios.
 */
public class Main {

    public static void main(String[] args) {
        // start with an inifinte Stream
    }

    /**
     * The process each of the service needs to complete.
     *
     * @param value the value to return
     * @param delayMillis the delay in millis for the IO bound operation
     * @return the value
     * @throws IOException if there was a problem with anything
     */
    private static <T> T process(T value, int delayMillis) throws IOException {
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
        return value;
    }
}
