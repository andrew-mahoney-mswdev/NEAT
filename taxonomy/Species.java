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
