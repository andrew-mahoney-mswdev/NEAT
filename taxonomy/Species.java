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

    public int getID() {
        return id;
    }

    public Genome getSpecimen() {
        return specimen;
    }

    public List<EvolvedNetwork> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public String toString() {
        return id + ": " + members.size();
    }
}
