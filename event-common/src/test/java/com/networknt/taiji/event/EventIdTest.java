package com.networknt.taiji.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventIdTest {

    @Test
    public void shouldParseFalse() {
        String s = "000085DF3b608aF058482391681C3a04C437776C-0000000000000001-false";
        EventId x = EventId.fromString(s);
        assertEquals(s, x.asString());
    }

    @Test
    public void shouldParseTrue() {
        String s = "000085DF3b608aF058482391681C3a04C437776C-0000000000000001-true";
        EventId x = EventId.fromString(s);
        assertEquals(s, x.asString());
    }

    @Test
    public void shouldFormatFalse() {
        EventId eventId = new EventId("000085DF3b608aF058482391681C3a04C437776C", 1L, false);
        String x = eventId.asString();
        assertEquals(x, "000085DF3b608aF058482391681C3a04C437776C-0000000000000001-false");
    }

    @Test
    public void shouldFormatTrue() {
        EventId eventId = new EventId("000085DF3b608aF058482391681C3a04C437776C", 1L, true);
        String x = eventId.asString();
        assertEquals(x, "000085DF3b608aF058482391681C3a04C437776C-0000000000000001-true");
    }

}
