package main;

/**
 * This task activates one input node and must return the corresponding output node.
 */

public class SampleMatchingTask implements Task {
//This task requires the same number of input and output nodes to be defined below.
    public static final int NODES = 15;

    double[] inputs;
    int solution;

    SampleMatchingTask() {
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
        return inputs;
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
        Task smt = new SampleMatchingTask();
        System.out.println("Solution: " + smt.getSolution());
        double[] sctInputs = smt.getInputs();
        for (int i = 0; i < NODES; i++) {
            System.out.println("Input " + i + ": " + sctInputs[i]);
        }
    }
    
}
