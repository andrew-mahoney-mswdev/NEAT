package main;

import tasks.*;

public abstract class Settings {
    public static final Task TASK = new Counting();

    public static final int POPULATION = 2000;
    public static final int CHILDREN_PER_PARENT = 20;
    public static final double SPECIES_SELECT_PROPORTION = 0.9;
    
    public static final int TASKS_PER_GENERATION = 60;
    public static final int TASKS_FOR_OPTIMAL = 600;

    public static final int MAX_MUTATIONS = 2;
    public static final double MUTATION_REWEIGHT_PROBABILTY = 0.9;
    public static final double MUTATION_NEW_CONNECTION_PROBABILITY = 0.067; //The probabilty of a new node is 1 minus the sum of these two probabilites.
    public static final double MUTATION_WEIGHT_SHIFT_MAX = 1.0;
    public static final double MUTATION_NEW_CONNECTION_WEIGHT_MAX = 5.0;

    public static final double TARGET_SPECIES_COUNT = 50;
    public static final double DELTA_THRESHOLD_AT_START = 0.05;
    public static final double DELTA_INCREMENT = 0.01;

    public static final double EXCESS_COEFFICIENT = 0.25;
    public static final double DISJOINT_COEFFICIENT = 0.25;
    public static final double AVG_WEIGHT_COEFFICIENT = 0.5;

    public static final int SENSOR = TASK.getSensorCount(); //TODO: Change these to plural?
    public static final int OUTPUT = TASK.getOutputCount();
}
