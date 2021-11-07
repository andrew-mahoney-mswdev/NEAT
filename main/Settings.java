package main;

public abstract class Settings {
    public static final Task TASK = new main.SampleMatchingTask();

    public static final int PARENTS_PER_GENERATION = 100;
    public static final int CHILDREN_PER_PARENT = 10;
    public static final int TASKS_PER_GENERATION = 100;

    public static final double MUTATION_WEIGHT_SHIFT_MAX = 1.0;
    public static final double MUTATION_NEW_CONNECTION_WEIGHT_MAX = 5.0;

    public static final int SENSOR = TASK.getSensorCount(); //TODO: Change these to plural?
    public static final int OUTPUT = TASK.getOutputCount();
}
