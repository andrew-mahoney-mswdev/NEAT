package tasks;

import java.util.Arrays;

import main.Task;
import main.Resource;

/**
 * This task activates one input node with the value 1.0 and must return the corresponding output node.
 */

public class Matching implements Task {
//This task requires the same number of input and output nodes to be defined below.
    public static final int NODES = 23;

    double[] inputs;
    int solution;

    public Matching() {
        next();
    }

    @Override
    public int getSensorCount() {
        return NODES;
    }

    @Override
    public int getOutputCount() {
        return NODES;
    }

    @Override
    public double[] getInputs() {
        return Arrays.copyOf(inputs, NODES);
    }

    @Override
    public int getSolution() {
        return solution;
    }

    @Override
    public boolean next() {
        solution = Resource.random.nextInt(NODES);
        inputs = new double[NODES];
        inputs[solution] = 1.0;
        return true;
    }

    public static void main(String... args) {
        Task smt = new Matching();
        for (int count = 0; count < 3; count++) {
            System.out.println("Solution: " + smt.getSolution());
            double[] sctInputs = smt.getInputs();
            for (int i = 0; i < NODES; i++) {
                System.out.println("Input " + i + ": " + sctInputs[i]);
            }
            smt.next();
        }
    }

}
