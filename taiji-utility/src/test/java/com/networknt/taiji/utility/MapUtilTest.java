package com.networknt.taiji.utility;

import com.networknt.config.JsonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MapUtilTest {
    @Test
    public void testInteger() {
        String s = "{\"value\":12}";
        Map<String, Object> map = JsonMapper.string2Map(s);
        Long amount = MapUtil.getLongFromMap(map, "value");
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(amount, 12L);
    }

    @Test
    public void testLong() {
        String s = "{\"value\":9223372036854775807}";
        Map<String, Object> map = JsonMapper.string2Map(s);
        Long amount = MapUtil.getLongFromMap(map, "value");
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(amount, 9223372036854775807L);
    }

    @Test
    public void testNull() {
        String s = "{\"v\":12}";
        Map<String, Object> map = JsonMapper.string2Map(s);
        Long amount = MapUtil.getLongFromMap(map, "value");
        Assertions.assertNull(amount);
    }

}
