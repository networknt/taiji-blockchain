package com.networknt.taiji.utility;

import java.util.Map;

public class MapUtil {
    public static Long getLongFromMap(Map<String, Object> map, String key) {
        Long value;
        try {
            value = (Long) map.get(key);
        } catch (ClassCastException e) {
            Object obj = map.get(key);
            value = ((Integer)obj).longValue();
        }
        return value;
    }
}
