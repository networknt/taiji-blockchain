package com.networknt.taiji.utility;

/**
 * Taiji Currency converter from Shell to Taiji
 *
 * 1 Taiji = 100,000,000 Shell
 *
 * @author Steve Hu
 */
public class Converter {

    private Converter() { }

    public static long fromShell(String number, Unit unit) {
        return fromShell(new Long(number), unit);
    }

    public static long fromShell(long number, Unit unit) {
        return number/unit.getShellFactor();
    }

    public static long toShell(String number, Unit unit) {
        return toShell(new Long(number), unit);
    }

    public static long toShell(long number, Unit unit) {
        return number*unit.getShellFactor();
    }

    public static enum Unit {
        SHELL("shell", 0),
        KSHELL("kshell", 3),
        MSHELL("mshell", 6),
        TAIJI("taiji", 8),  // One TAIJI is 100 MSHELL
        KTAIJI("ktaiji", 11),
        MTAIJI("mtaiji", 14);

        private String name;
        private long shellFactor;

        private Unit(String name, int factor) {
            this.name = name;
            this.shellFactor = power(10, factor);
        }

        public long getShellFactor() {
            return this.shellFactor;
        }

        public String toString() {
            return this.name;
        }

        public static Converter.Unit fromString(String name) {
            if (name != null) {
                for (Unit unit : Unit.values()) {
                    if (name.equalsIgnoreCase(unit.name)) {
                        return unit;
                    }
                }
            }
            return Unit.valueOf(name);
        }
    }

    public static long power(long number, int power){
        long res = 1;
        long sq = number;
        while(power > 0){
            if(power % 2 == 1){
                res *= sq;
            }
            sq = sq * sq;
            power /= 2;
        }
        return res;
    }
}
