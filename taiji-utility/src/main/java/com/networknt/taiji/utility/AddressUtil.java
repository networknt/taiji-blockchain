package com.networknt.taiji.utility;

/**
 * The static utility class for crypto address.
 * @author Steve Hu
 */
public class AddressUtil {
    public static boolean sameChain(String address1, String address2) {
        if(address1 != null && address2 != null && address1.substring(0, 4).equals(address2.substring(0, 4))) {
            return true;
        } else {
            return false;
        }
    }
}
