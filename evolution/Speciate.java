package evolution;

import java.util.List;
import java.util.ArrayList;

import genotype.Connection;
import genotype.Crossover;
import genotype.Genome;
import genotype.ID;
import genotype.Mutatable; //for testing
import main.Settings;

public class Speciate {

    public static void initialise() {
        EvolvedNetwork first = Population.getNetworks().get(0);
        Species.addTaxon(first);
    }

    public static void go() {
        List<EvolvedNetwork> networks = Population.getNetworks();
        List<Species> taxa = Species.getTaxa();

        for (int n = 0; n < networks.size(); n++) {
            for (int t = 0; t < taxa.size(); t++) {
                EvolvedNetwork en = networks.get(n);
                Species taxon = taxa.get(t);
                Genome g = en.getGenome();
                Genome s = taxon.getSpecimen();

                if (calculateDelta(g, s) < Settings.DELTA_THRESHOLD) {
                    en.setSpecies(taxon.getID());
                } else { //New species detected
                    Species.addTaxon(en);
                }
            }
        }
    }

    public static double calculateDelta(Genome a, Genome b) {
        int n = 0; //Size of largest genome
        int d = 0; //Number of disjoint genes
        int e = 0; //Number of excess genes
        double w = 0.0; //Average weight difference of matching genes
        int mG = 0; //Number of matching genes

        List<Connection> aConnections = a.getConnections();
        List<Connection> bConnections = b.getConnections();

        int aSize = aConnections.size();
        int bSize = bConnections.size();
        if (aSize > bSize) {n = aSize;}
        else {n = bSize;}

        int aGene = 0, bGene = 0;
        double totalDifference = 0.0;
        do {
            Connection aConnection = aConnections.get(aGene);
            Connection bConnection = bConnections.get(bGene);
            int aInnovation = aConnection.getInnovation();
            int bInnovation = bConnection.getInnovation();
            if (aInnovation == bInnovation) {
                totalDifference += Math.abs(aConnection.getWeight() - bConnection.getWeight());
                mG++;

                aGene++;
                bGene++;
            } else { //Innovation numbers of genes don't match
                d++;

                if (aInnovation < bInnovation) {
                    aGene++;
                } else { //bInnovation is smaller
                    bGene++;
                }
            }
        } while (aGene < aSize && bGene < bSize);

        e = (aSize - aGene) + (bSize - bGene); //Only one of these values will be above zero, denoting excess genes.
        w = totalDifference / mG;

        double delta = ((Settings.EXCESS_COEFFICIENT * e) / n) +
                       ((Settings.DISJOINT_COEFFICIENT * d) / n) +
                       (Settings.AVG_WEIGHT_COEFFICIENT * w);

        return delta;
    }

    public static void main(String... args) {
        Genome a = new Genome(Genome.getFirstGenome());    
        Genome b = new Genome(a);
        
        ID.resetInnovationIDs();
        Mutatable mA = Mutatable.mutate(a);
        Mutatable mB = Mutatable.mutate(b);
        mA.applyMutation();
        mB.applyMutation();
        Genome p1 = Crossover.recombine(a, b); 
        Genome p2 = Crossover.recombine(b, a);
        
        ID.resetInnovationIDs();
        Mutatable mP1 = Mutatable.mutate(p1);
        Mutatable mP2 = Mutatable.mutate(p2);
        mP1.applyMutation();
        mP2.applyMutation();
        Genome c1 = Crossover.recombine(p1, p2);
        ID.resetInnovationIDs();
        Genome c2 = Crossover.recombine(p2, p1);
        
        ID.resetInnovationIDs();
        Mutatable mC1 = Mutatable.mutate(c1);
        Mutatable mC2 = Mutatable.mutate(c2);
        mC1.applyMutation();
        mC2.applyMutation();

        System.out.println(c1);
        System.out.println(c2);
        System.out.println("delta " + calculateDelta(c1, c2));
    }
}
