package evolution;

import genotype.Genome;
import main.Resource;

public class EvolvedNetwork implements Comparable<EvolvedNetwork> {
    private Genome genome;
    private double fitness;
    private int random;

    EvolvedNetwork(Genome g) {
        genome = g;
        resetFitness();
    }
    
    @Override
    public int compareTo(EvolvedNetwork en) { //Higher fitness sorts first
        if (fitness < en.fitness) return 1;
        else if (fitness > en.fitness) return -1;
        else { //Fitness is equal
            if (random < en.random) return 1;
            else return -1;
        }
    }

    void resetFitness() {
        fitness = 0;
        random = Resource.random.nextInt(Integer.MAX_VALUE);
    }

    void addFitness(double value) {
        fitness += value;
    }

    public Genome getGenome() {
        return genome;
    }

    public double getFitness() {
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

    public static void main(String... args) {
        Genome genome = new Genome(Genome.getFirstGenome());
        
        EvolvedNetwork en = new EvolvedNetwork(genome);
        System.out.println(en.toString());
        System.out.println("Calling addFitness(1.0)...");
        en.addFitness(1.0);
        System.out.println(en.toString());
        System.out.println("Calling resetFitness()...");
        en.resetFitness();
        System.out.println(en.toString());

        EvolvedNetwork other = new EvolvedNetwork(genome);
        other.addFitness(1.0);
        System.out.println("Calling compareTo() against a network with higher fitness");
        System.out.println(en.compareTo(other));

        en = new EvolvedNetwork(genome);
        en.addFitness(1.0);
        other = new EvolvedNetwork(genome);
        System.out.println("Calling compareTo() against a network with lower fitness");
        System.out.println(en.compareTo(other));

        en = new EvolvedNetwork(genome);
        other = new EvolvedNetwork(genome);
        System.out.println("Calling compareTo() on networks with equal fitness");
        System.out.println("This network has a random value of: " + en.random);
        System.out.println("The other network has a random value of: " + other.random);
        System.out.println(en.compareTo(other));
    }
}
