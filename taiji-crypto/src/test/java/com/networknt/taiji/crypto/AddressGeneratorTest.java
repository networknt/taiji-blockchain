package com.networknt.taiji.crypto;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class AddressGeneratorTest {
    @Test
    public void testGenerator() {
        AddressGenerator generator = new AddressGenerator("0000");
        final long startTime = System.nanoTime();
        ECKeyPair pair = generator.generate();
        System.out.println("Found in " + getElapsedTimeMinSec(startTime));
        System.out.println(Keys.getAddress(pair));
    }

    /**
     * Get elapsed time in minutes and seconds e.g, 1 min, 30 sec
     *
     * @param startTime the start time
     * @return elapsed time in minutes and seconds
     */
    private static String getElapsedTimeMinSec(long startTime) {
        long elapsedTime = System.nanoTime() - startTime;

        return String.format("%d min, %d sec",
                TimeUnit.NANOSECONDS.toMinutes(elapsedTime),
                TimeUnit.NANOSECONDS.toSeconds(elapsedTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(elapsedTime))
        );
    }
}
