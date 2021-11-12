package evolution;

import genotype.Genome;
import main.Resource;

class EvolvedNetwork implements Comparable<EvolvedNetwork> {
    private Genome genome;
    private int species = -1; //Set to -1 for now for debugging
    private int fitness = 0;
    private int random = 0;

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

    public void resetFitness() {
        fitness = 0;
        random = Resource.random.nextInt(Integer.MAX_VALUE);
    }

    void setSpecies(int species) {
        this.species = species;
    }

    public void addFitness() {
        fitness++;
    }

    public Genome getGenome() {
        return genome;
    }

    public int getSpecies() {
        return species;
    }

    public int getFitness() {
        return fitness;
    }
}
