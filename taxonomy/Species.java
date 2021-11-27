package taxonomy;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import genotype.Genome;
import main.Resource;
import evolution.EvolvedNetwork;

public class Species implements Comparable<Species>{
    private static int speciesID = 0;

    private int id;
    private Genome specimen;
    double fitness;
    int offspring;
    List<EvolvedNetwork> members = new ArrayList<>();

    Species(EvolvedNetwork specimen) {
        id = speciesID++;
        this.specimen = specimen.getGenome();
        members.add(specimen);
    }

    public void chooseSpecimen() {
        int random = Resource.random.nextInt(members.size());
        EvolvedNetwork specimen = members.get(random);
        this.specimen = specimen.getGenome();
    }

    public double calculateFitness() {
        double total = 0.0;
        for (EvolvedNetwork en : members) {
            total += en.getFitness();
        }
        fitness = total / members.size();
        return fitness;
    }

    @Override
    public int compareTo(Species s) {
        if (s.fitness - this.fitness > 0)
            return 1;
        else if (s.fitness - this.fitness < 0)
            return -1;
        else return 0;
    }

    public int getID() {
        return id;
    }

    public Genome getSpecimen() {
        return specimen;
    }

    public double getFitness() {
        return fitness;
    }

    public int getOffspring() {
        return offspring;
    }

    public List<EvolvedNetwork> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public int size() {
        return members.size();
    }

    public String toString() {
        return id + ":\t" + members.size() + "\t" + getFitness() + "\t" + getOffspring();
    }

    public static void main(String... args) {
        Genome genome = new Genome(Genome.getFirstGenome());
        EvolvedNetwork en = EvolvedNetwork.newEvolvedNetwork4Testing(genome);
        Species species = new Species(en);
        for (int count = 0; count < 4; count++) {
            genome = new Genome(genome);
            en = EvolvedNetwork.newEvolvedNetwork4Testing(genome);
            species.members.add(en);
        }

        System.out.println(species);
        System.out.println("ID: " + species.getID());
        
        species.chooseSpecimen();
        for (int i = 0; i < 5; i++) {
            if (species.members.get(i).getGenome() == species.getSpecimen()) {
                System.out.println("specimen: " + i);
            }
        }

        for (int i = 0; i < species.members.size(); i++) {
            System.out.println(species.members.get(i));
        }
    }

}
