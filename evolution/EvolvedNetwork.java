package evolution;

import genotype.Genome;
import main.Resource;

public class EvolvedNetwork implements Comparable<EvolvedNetwork> {
    private Genome genome;
    private int fitness = 0;
    int random = 0;

    EvolvedNetwork(Genome g) {
        genome = g;
    }
    
    @Override
    public int compareTo(EvolvedNetwork en) {
        int compareFitness = en.fitness - this.fitness;
        if (compareFitness != 0) {
            return compareFitness;
        } else {
            return en.random - this.random;
        }
    }

    void resetFitness() {
        fitness = 0;
        random = Resource.random.nextInt(Integer.MAX_VALUE);
    }

    void addFitness() {
        fitness++;
    }

    public Genome getGenome() {
        return genome;
    }

    public int getFitness() {
        return fitness;
    }

    public String toString() {
        String endl = "\n";
        String value = "Evolved Network" + endl;
        value += "fitness: " + fitness + endl;
        value += "random: " + random + endl;
        value += genome + endl;
        return value;
    }

    public static EvolvedNetwork newEvolvedNetwork4Testing(Genome g) {
        return new EvolvedNetwork(g);
    }
}
