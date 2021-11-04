package evolution;

import genotype.Genome;
import main.Resource;

class EvolvedNetwork implements Comparable<EvolvedNetwork> {
    private Genome genome;
    private int fitness;
    int random;

    EvolvedNetwork(Genome g) {
        genome = g;
        resetFitness();
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

    public void resetFitness() {
        fitness = 0;
        random = Resource.random.nextInt(Integer.MAX_VALUE);
    }

    public void addFitness() {
        fitness++;
    }

    public Genome getGenome() {
        return genome;
    }

    public int getFitness() {
        return fitness;
    }
}
