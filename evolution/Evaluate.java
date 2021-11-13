package evolution;

import java.util.Collections;
import java.util.List;

import main.Settings;
import main.Task;
import taxonomy.Classify;
import taxonomy.Species;
import genotype.Genome;
import genotype.Phenome;

public abstract class Evaluate {
    private static final Task task = Settings.TASK;
    private static List<EvolvedNetwork> networks;
    private static List<Species> taxa;
    private static double[] inputs;
    private static int solution;

    static {
        readyTask();
    }

    private static void readyTask() {
        task.next();
        inputs = task.getInputs();
        solution = task.getSolution();
    }

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

    private static void assessNetwork(int index) {
        EvolvedNetwork evolvedNetwork = networks.get(index);
        Genome genome = evolvedNetwork.getGenome();
        Phenome phenome = new Phenome(genome);
        double[] outputs = phenome.run(inputs);
        int highestOutput = getHighestOutput(outputs);
        if (highestOutput == solution) {
            evolvedNetwork.addFitness();
        }
    }

    private static void assessAllNetworks() {
        for (int i = 0; i < networks.size(); i++) {
            assessNetwork(i);
        }
    }

    public static void resetAllFitness() {
        for (EvolvedNetwork n : networks) {
            n.resetFitness();
        }
    }

    private static double adjustFitness() {
        double total = 0.0;
        for (Species s : taxa) {
            List<EvolvedNetwork> members = s.getMembers();
            for (EvolvedNetwork en : members) {
                en.adjustedFitness = (double)en.getFitness() / members.size();
            }
            total += s.sumAdjustedFitness();
        }
        return total;
    }

    public static void go() {
        networks = Population.getNetworks();

        resetAllFitness();

        for (int count = 0; count < Settings.TASKS_PER_GENERATION; count++) {
            readyTask();
            assessAllNetworks();
        }

        for (int i = 0; i < networks.size(); i++) {
            if (networks.get(i).getFitness() == Settings.TASKS_PER_GENERATION) {
                for (int count = Settings.TASKS_PER_GENERATION; count < Settings.TASKS_FOR_OPTIMAL; count++) {
                    readyTask();
                    assessNetwork(i);
                }
            }
        }

        taxa = Classify.getTaxa();
        double populationFitness = adjustFitness();
        for (Species s : taxa) { //Assign offspring
            double offspring = (s.getAdjustedFitnessTotal() / populationFitness) * Settings.POPULATION;
            s.setOffspring((int)offspring);
        }


        Collections.sort(networks);
    }
}
