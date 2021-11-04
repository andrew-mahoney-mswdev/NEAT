package main;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * This task provides a random sequence of inputs with the value 1.0 and must return the number of inputs with that value.
 */

public class SampleCountingTask implements Task {
    //This task requires the same number of input and output nodes to be defined below.
    public static final int NODES = 4;

    double[] inputs = new double[NODES];
    int solution;

    SampleCountingTask() {
        next();
    }

    @Override
    public int getSensorCount() {return NODES;}

    @Override
    public int getOutputCount() {return NODES;}

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
        
        List<Integer> indices = new ArrayList<>(NODES);
        for (int i = 0; i < NODES; i++) indices.add(i);
        for (int i = 0; i < solution; i++) {
            Integer inputNode = indices.get(Resource.random.nextInt(NODES-i));
            inputs[inputNode] = 1.0;
            indices.remove(inputNode);
        }
        return true;
    }
    
    public static void main(String... args) {
        Task sct = new SampleCountingTask();
        System.out.println("Solution: " + sct.getSolution());
        double[] sctInputs = sct.getInputs();
        for (int i = 0; i < NODES; i++) {
            System.out.println("Input " + i + ": " + sctInputs[i]);
        }
    }

}
