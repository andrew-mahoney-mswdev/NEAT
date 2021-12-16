package tasks;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import main.Task;
import main.Resource;

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

    @Override
    public boolean next() {
        solution = Resource.random.nextInt(outputs);
        inputs = new double[INPUTS];
        
        List<Integer> indices = new ArrayList<>(INPUTS);
        for (int i = 0; i < INPUTS; i++) indices.add(i);
        for (int i = 0; i < solution; i++) {
            Integer inputNode = indices.get(Resource.random.nextInt(INPUTS-i));
            inputs[inputNode] = 1.0;
            indices.remove(inputNode);
        }
        return true;
    }
    
    public static void main(String... args) {
        Task sct = new Counting();
        for (int count = 0; count < 3; count++) {
            System.out.println("Solution: " + sct.getSolution());
            double[] sctInputs = sct.getInputs();
            for (int i = 0; i < INPUTS; i++) {
                System.out.println("Input " + i + ": " + sctInputs[i]);
            }
            sct.next();
        }
    }

}
