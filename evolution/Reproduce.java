package evolution;

import java.util.List;
import java.util.ArrayList;

import main.Settings;
import genotype.ID;
import genotype.Genome;
import genotype.Mutatable;
import genotype.Crossover;
import taxonomy.Classify;
import taxonomy.Species;

public abstract class Reproduce {
    static List<EvolvedNetwork> networks;

    private static void birth(Genome parent) {
        Genome clone = new Genome(parent);
        Mutatable child = Mutatable.mutate(clone);
        child.applyMutation(); //If more than one mutation is to be applied here, then ID will need to be revised to ensure repeat innovation numbers are allocated sequentially within a genome.
        networks.add(new EvolvedNetwork(child));
    }

    private static void fission(Species s) {
        EvolvedNetwork parent = s.getMembers().get(0);
        for (int c = 0; c < s.getOffspring(); c++) {
            birth(parent.getGenome());
        }
    }

    private static void recombine(Species species) {
        int numParents = (int)Math.ceil(species.size() * Settings.PROPORTION_PARENTS);
        if (numParents < 2) numParents = 2;
        int offSpringPerParent = (int)Math.ceil((double)species.getOffspring() / numParents);

        for (int c = 0, parent2Index = 1; c < species.getOffspring(); c++, parent2Index++) {
            int parent1Index = (c / offSpringPerParent) % numParents;

            if (parent2Index == parent1Index) parent2Index++;
            if (parent2Index >= numParents) {
                if (parent1Index != 0) parent2Index = 0;
                else parent2Index = 1;
            }

            if (parent1Index == parent2Index) throw new RuntimeException("Reproduce.sexual(species), parent1Index is equal to parent2Index");
            
            EvolvedNetwork parent1 = species.getMembers().get(parent1Index);
            EvolvedNetwork parent2 = species.getMembers().get(parent2Index);
            Genome child;
            if (parent1.compareTo(parent2) < 0) child = Crossover.recombine(parent1.getGenome(), parent2.getGenome());
            else child = Crossover.recombine(parent2.getGenome(), parent1.getGenome());
            birth(child);
        }
    }

    public static void speciate() {
        List<Species> taxa = Classify.getTaxa();
        networks = Population.getNetworks();
        networks.clear();
        ID.resetInnovationIDs();

        for (Species s : taxa) {
            if (s.getOffspring() > 0) {
                if (s.size() > 1) { //sexual reproduction
                    recombine(s);
                } else { //asexual reproduction
                    fission(s);
                }
            }
        }
    }
    
    public static void sexual() {
        int numParents = (int)(Settings.POPULATION * Settings.PROPORTION_PARENTS);
        int offSpringPerParent = Settings.POPULATION / numParents;

        List<EvolvedNetwork> parents = new ArrayList<>(Population.getNetworks().subList(0, numParents));
        List<EvolvedNetwork> networks = Population.getNetworks();
        networks.clear();
        ID.resetInnovationIDs();

        for (int p1 = 0, p2 = 1; p1 < numParents; p1++) {
            Genome parent1 = parents.get(p1).getGenome();
            for (int c = 0; c < offSpringPerParent; c++, p2++) {
                if (p2 == p1) p2++;
                if (p2 >= numParents) p2 = 0;
                
                Genome recombination;
                if (p1 < p2) recombination = Crossover.recombine(parent1, parents.get(p2).getGenome());
                else recombination = Crossover.recombine(parents.get(p2).getGenome(), parent1);
                Mutatable child = Mutatable.mutate(recombination);
                child.applyMutation(); //If more than one mutation is to be applied here, then ID will need to be revised to ensure repeat innovation numbers are allocated sequentially within a genome.
                networks.add(new EvolvedNetwork(child));
            }
        }
    }

    public static void fission() { //Asexual reproduction
        int numParents = (int)(Settings.POPULATION * Settings.PROPORTION_PARENTS);
        int offSpringPerParent = Settings.POPULATION / numParents;

        List<EvolvedNetwork> parents = new ArrayList<>(Population.getNetworks().subList(0, numParents));
        List<EvolvedNetwork> networks = Population.getNetworks();
        networks.clear();
        ID.resetInnovationIDs();

        for (int p = 0; p < numParents; p++) {
            EvolvedNetwork parent = parents.get(p);
            for (int c = 0; c < offSpringPerParent; c++) {
                Genome clone = new Genome(parent.getGenome());
                Mutatable child = Mutatable.mutate(clone);
                child.applyMutation();
                networks.add(new EvolvedNetwork(child));
            }
        }
    }

}
