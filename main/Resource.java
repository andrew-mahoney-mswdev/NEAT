package main;

import java.util.Random;

import taxonomy.Classify;

public abstract class Resource {
    //Randomisation
    static public Random random = new Random();
    static {random.nextInt();}

    static public double nextSignedDouble(double bound) {
        double value = random.nextDouble();
        value *= bound;
        if (random.nextBoolean()) {value *= -1;}
        return value;
    }

    //Delta threshold
    private static double delta = Settings.DELTA_THRESHOLD_AT_START;

    public static void adjustDelta() {
        int speciesCount = Classify.getSpeciesCount();
        
        if (speciesCount > Settings.TARGET_SPECIES_COUNT) {
            delta += Settings.DELTA_INCREMENT;
        } else if (speciesCount < Settings.TARGET_SPECIES_COUNT) {
            delta -= Settings.DELTA_INCREMENT;
        }
    }

    public static double getDelta() {
        return delta;
    }

    //Generation counter
    private static int generation = 0;

    public static void nextGeneration() {
        generation++;
    }

    public static int getGeneration() {
        return generation;
    }

    public static void main(String... args) {
        System.out.println("TESTING class Resource");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Calling nextSignedDouble(" + i + ")...");
            System.out.println(nextSignedDouble(i));
        }
    }
}
