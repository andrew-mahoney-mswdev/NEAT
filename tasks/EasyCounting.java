package tasks;

import java.util.Arrays;

import main.Task;
import main.Resource;

/**
 * This task provides the first x input nodes with a value of 1.0 and expects an output of x
 */

public class EasyCounting implements Task {
    //This task requires a number of input nodes to be defined below.
    public static final int INPUTS = 10;

    private final int outputs = INPUTS + 1;
    private double[] inputs;
    private int solution;

    public EasyCounting() {
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
        
        for (int i = 0; i < solution; i++) {
            inputs[i] = 1.0;
        }
        return true;
    }
    
    public static void main(String... args) {
        Task sct = new EasyCounting();
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
