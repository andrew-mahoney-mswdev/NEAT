package evolution;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import genotype.Genome;

public class Species {
    //STATIC DECLARATION
    private static List<Species> taxa = new ArrayList<>();
    private static int speciesID = 0;

    static List<Species> getTaxa() {
        return Collections.unmodifiableList(taxa);
    }

    static void addTaxon(EvolvedNetwork en) {
        Species s = new Species(en.getGenome());
        taxa.add(s);
        en.setSpecies(s.getID());
    }

    //INSTANCE DECLARATION
    private int id;
    private Genome specimen;

    Species(Genome specimen) {
        id = speciesID++;
        this.specimen = specimen;
    }

    public void setSpecimen(Genome specimen) {
        this.specimen = specimen;
    }

    public int getID() {
        return id;
    }

    public Genome getSpecimen() {
        return specimen;
    }
}
