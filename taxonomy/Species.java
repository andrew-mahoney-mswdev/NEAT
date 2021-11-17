package taxonomy;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import genotype.Genome;
import main.Resource;
import evolution.EvolvedNetwork;

public class Species {
    private static int speciesID = 0;

    private int id;
    private Genome specimen;
    private double adjustedFitnessTotal;
    private int offspring = 0;
    List<EvolvedNetwork> members = new ArrayList<>();

    Species(EvolvedNetwork specimen) {
        id = speciesID++;
        this.specimen = specimen.getGenome();
        members.add(specimen);
    }

    public double sumAdjustedFitness() {
        adjustedFitnessTotal = 0.0;
        for (EvolvedNetwork en : members) {
            adjustedFitnessTotal += en.getAdjustedFitness();
        }
        return adjustedFitnessTotal;
    }

    public void chooseSpecimen() {
        int random = Resource.random.nextInt(members.size());
        EvolvedNetwork specimen = members.get(random);
        this.specimen = specimen.getGenome();
    }

    public void setOffspring(int numberOffspring) {
        this.offspring = numberOffspring;
    }

    public int getID() {
        return id;
    }

    public Genome getSpecimen() {
        return specimen;
    }

    public double getAdjustedFitnessTotal() {
        return adjustedFitnessTotal;
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
        return id + ": " + members.size() + ", fitness: " + adjustedFitnessTotal + ", offspring: " + offspring;
    }
}
