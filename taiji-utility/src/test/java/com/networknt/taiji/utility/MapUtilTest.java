package com.networknt.taiji.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapUtilTest {
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void testInteger() throws Exception {
        String s = "{\"value\":12}";
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {};
        Map<String, Object> map = mapper.readValue(s, typeRef);
        Long amount = MapUtil.getLongFromMap(map, "value");
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(amount, 12L);
    }

    @Test
    public void testLong() throws Exception {
        String s = "{\"value\":9223372036854775807}";
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {};
        Map<String, Object> map = mapper.readValue(s, typeRef);
        Long amount = MapUtil.getLongFromMap(map, "value");
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(amount, 9223372036854775807L);
    }

    @Test
    public void testNull() throws Exception {
        String s = "{\"v\":12}";
        TypeReference<HashMap<String, Object>> typeRef
                = new TypeReference<HashMap<String, Object>>() {};
        Map<String, Object> map = mapper.readValue(s, typeRef);
        Long amount = MapUtil.getLongFromMap(map, "value");
        Assertions.assertNull(amount);
    }

}
