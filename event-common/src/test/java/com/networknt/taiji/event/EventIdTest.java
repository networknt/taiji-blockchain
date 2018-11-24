package com.networknt.taiji.event;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventIdTest {

    @Test
    public void shouldParse() {
        String s = "000085DF3b608aF058482391681C3a04C437776C-0000000000000001";
        EventId x = EventId.fromString(s);
        assertEquals(s, x.asString());
    }

    @Test
    public void shouldFormat() {
        EventId eventId = new EventId("000085DF3b608aF058482391681C3a04C437776C", 1L);
        String x = eventId.asString();
        Assert.assertEquals(x, "000085DF3b608aF058482391681C3a04C437776C-0000000000000001");
    }

}
