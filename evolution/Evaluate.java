package evolution;

import java.util.Collections;
import java.util.List;

import main.Settings;
import main.Task;
import genotype.Genome;
import genotype.Phenome;

public abstract class Evaluate {
    private static final Task task = Settings.TASK;
    private static List<EvolvedNetwork> networks;

    private static int getHighestOutput(double[] outputs) {
        int highestOutput = 0;
        double highestOutputValue = outputs[0];
        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > highestOutputValue) {
                highestOutput = i;
                highestOutputValue = outputs[i];
            }
        }
        return highestOutput;
    }

    private static void taskAssessment() {
        task.next();
        double[] inputs = task.getInputs();
        int solution = task.getSolution();

        for (int i = 0; i < networks.size(); i++) {
            EvolvedNetwork evolvedNetwork = networks.get(i);
            Genome genome = evolvedNetwork.getGenome();
            Phenome phenome = new Phenome(genome);
            double[] outputs = phenome.run(inputs);
            int highestOutput = getHighestOutput(outputs);
            if (highestOutput == solution) {
                evolvedNetwork.addFitness();
            }
        }
    }

    public static void go() {
        networks = Population.getNetworks();

        for (EvolvedNetwork n : networks) {
            if (n.getFitness() > 0) {
                n.resetFitness();
            }
        }

        for (int count = 0; count < Settings.TASKS_PER_GENERATION; count++) {
            taskAssessment();
        }
        //TODO: If a network has solved every problem, give it a large series of problems to see if it has solved the entire task.
        Collections.sort(networks);
    }
}
