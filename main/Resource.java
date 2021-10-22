package main;

import java.util.Random;

public class Resource {
    static public Random random = new Random();
    static {random.nextInt();}

    static public double nextSignedDouble(double bound) {
        double value = random.nextDouble();
        value *= bound;
        if (random.nextBoolean()) {value *= -1;}
        return value;
    }

    public static void main(String... args) {
        System.out.println("TESTING class Resource");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Calling nextSignedDouble(" + i + ")...");
            System.out.println(nextSignedDouble(i));
        }
    }
}
