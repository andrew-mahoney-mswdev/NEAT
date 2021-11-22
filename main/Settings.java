package main;

import tasks.*;

public abstract class Settings {
    public static final Task TASK = new Counting();

    public static final int POPULATION = 2000;
    public static final int CHILDREN_PER_PARENT = 20;
    
    public static final int TASKS_PER_GENERATION = 8;
    public static final int TASKS_FOR_OPTIMAL = 8;

    public static final int MAX_MUTATIONS = 4;
    public static final double MUTATION_WEIGHT_SHIFT_MAX = 1.0;
    public static final double MUTATION_NEW_CONNECTION_WEIGHT_MAX = 5.0;

    public static final double DELTA_THRESHOLD = 1.0;
    public static final double EXCESS_COEFFICIENT = 0.25;
    public static final double DISJOINT_COEFFICIENT = 0.25;
    public static final double AVG_WEIGHT_COEFFICIENT = 0.5;

    public static final int SENSOR = TASK.getSensorCount(); //TODO: Change these to plural?
    public static final int OUTPUT = TASK.getOutputCount();
}
