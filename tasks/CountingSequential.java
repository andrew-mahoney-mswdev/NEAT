package tasks;

import java.util.Arrays;

import main.Task;

/**
 * This task provides a random spread of inputs with the value 1.0 and must return the number of inputs with that value.
 * The task provides problems in sequence.
 */

public class CountingSequential implements Task {
    //This task requires a number of input nodes to be defined below.
    public static final int INPUTS = 4;

    private final int outputs = INPUTS + 1;
    double[] inputs;

    private void resetInputs() {
        for (int i = 0; i < INPUTS; i++) {
            inputs[i] = 0.0;
        }
    }

    public CountingSequential() {
        inputs = new double[INPUTS];
        resetInputs();
    }

    @Override
    public int getSensorCount() {
        return INPUTS;
    }

    @Override
    public int getOutputCount() {
        return outputs;
    }

    @Override
    public double[] getInputs() {
        return Arrays.copyOf(inputs, INPUTS);
    }

    @Override
    public int getSolution() {
        int solution = 0;
        for (int i = 0; i < INPUTS; i++) {
            if (inputs[i] == 1.0) {
                solution++;
            }
        }
        return solution;
    }

    @Override
    public boolean next() {
        for (int i = INPUTS-1; i >= 0; i--) {
            if (inputs[i] == 0.0) {
                inputs[i]++;
                for (int j = INPUTS-1; j > i; j--) {
                    inputs[j] = 0.0;
                }
                break;
            } else if (i == 0) {
                resetInputs();
            }
        }
        return true;
    }

    public static void main(String... args) {
        Task cst = new CountingSequential();
        for (int count = 0; count < 18; count++) {
            System.out.println("Solution: " + cst.getSolution());
            double[] sctInputs = cst.getInputs();
            for (int i = 0; i < INPUTS; i++) {
                System.out.println("Input " + i + ": " + sctInputs[i]);
            }
            cst.next();
        }
    }
    
}
