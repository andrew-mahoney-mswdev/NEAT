package main;

/**
 * A Task object will supply the inputs and solution of training problems for neural nets.
 */
public interface Task {
    public int getSensorCount(); //Returns the number of sensor nodes for the task.
    
    public int getOutputCount(); //Returns the number of output nodes for the task.

    public double[] getInputs(); //Returns the inputs for the neural net (values between 0-1)

    public int getSolution(); //Returns the correct solution for the inputs

    public boolean next(); //Generates another set of inputs and a solution.  Returns false if generation failed (eg. there are no new sets available).
}
