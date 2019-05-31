package com.networknt.taiji.console;

import com.networknt.taiji.utility.Converter;
import org.junit.jupiter.api.Test;

public class TotalSupplyTest {
    @Test
    public void testSupply() {
        long total = 1000000000L;
        int decimals = 9;
        long factor = Converter.power(10, decimals);
        System.out.println("factor=" + factor);
        long supply = total * factor;
        System.out.println("supply=" + supply);
    }
}
