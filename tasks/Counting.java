package tasks;

import java.util.Arrays;

import main.Task;

/**
 * This task provides a random spread of inputs with the value 1.0 and must return the number of inputs with that value.
 */

public class Counting implements Task {
    //This task requires a number of input nodes to be defined below.
    public static final int INPUTS = 3;

    private final int outputs = INPUTS + 1;
    double[] inputs;
    int solution;

    public Counting() {
        next();
    }

    @Override
    public int getSensorCount() {return INPUTS;}

    @Override
    public int getOutputCount() {return outputs;}

    @Override
    public double[] getInputs() {
        return Arrays.copyOf(inputs, INPUTS);
    }

    @Override
    public int getSolution() {
        return solution;
    }

    int problem = 0;
    @Override
    public boolean next() {
        switch(problem) {
            case 0:
                inputs = new double[] {0.0, 0.0, 0.0};
                solution = 0;
            break;
            case 1:
                inputs = new double[] {1.0, 0.0, 0.0};
                solution = 1;
            break;
            case 2:
                inputs = new double[] {0.0, 1.0, 0.0};
                solution = 1;
            break;
            case 3:
                inputs = new double[] {0.0, 0.0, 1.0};
                solution = 1;
            break;
            case 4:
                inputs = new double[] {1.0, 1.0, 0.0};
                solution = 2;
            break;
            case 5:
                inputs = new double[] {1.0, 0.0, 1.0};
                solution = 2;
            break;
            case 6:
                inputs = new double[] {0.0, 1.0, 1.0};
                solution = 2;
            break;
            case 7:
                inputs = new double[] {1.0, 1.0, 1.0};
                solution = 3;
            break;
        }

        problem++;
        if (problem == 8) problem = 0;

        return true;
    }

    public static void main(String... args) {
        Task sct = new Counting();
        for (int count = 0; count < 8; count++) {
            System.out.println("Solution: " + sct.getSolution());
            double[] sctInputs = sct.getInputs();
            for (int i = 0; i < INPUTS; i++) {
                System.out.println("Input " + i + ": " + sctInputs[i]);
            }
            sct.next();
        }
    }

}
