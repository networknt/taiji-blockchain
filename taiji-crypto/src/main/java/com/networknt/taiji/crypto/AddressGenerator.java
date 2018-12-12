package com.networknt.taiji.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.LongStream;

public class AddressGenerator {
    public static final Logger logger = LoggerFactory.getLogger(AddressGenerator.class);

    private final String chainId;

    /**
     * Constructor for AddressGenerator
     *
     * @param chainId the first 4 digits of the addresss
     */
    public AddressGenerator(final String chainId) {
        this.chainId = chainId;
    }

    /**
     * Generates a stream of keys and returns the first match.
     *
     * @return an ECKeyPair with an address that contains the search string
     */
    public ECKeyPair generate() {
        Optional<ECKeyPair> found = LongStream.iterate(0L, n -> n + 1)
                .parallel()
                //.peek(AddressGenerator::logAttempts)
                .mapToObj(ignore -> Keys.createEcKeyPairUnsafe())
                .filter(key -> Keys.getAddress(key).startsWith(chainId))
                .findAny();
        return found.get();
    }

    /**
     * For every 1 million attempts, log the total number of attempts
     *
     * @param attempts the current number of attempts
     */
    private static void logAttempts(long attempts) {
        if(logger.isDebugEnabled()) {
            if (attempts == 0) {
                logger.debug("Address generation initiated.");
            } else if (attempts % 1000000 == 0) {
                logger.debug("Address generation in progress, attempts made: " +
                        NumberFormat.getNumberInstance(Locale.US).format(attempts));
            }
        }
    }
}
