package evolution;

import java.util.List;
import java.util.ArrayList;

import main.Resource;
import main.Settings;
import genotype.ID;
import genotype.Genome;
import genotype.Mutatable;
import genotype.Crossover;
import taxonomy.Species;
import taxonomy.Classify;

public abstract class Reproduce {
    private static List<EvolvedNetwork> networks;
    private static ParentsCounter parentCount = new ParentsCounter();

    private static class ParentsCounter {
        private int number = 0;
        private int initial = 0;
    }

    static int getParentCount() {
        return parentCount.number;
    }

    static int getNewParentCount() {
        return parentCount.initial;
    }

    private static void addParent(EvolvedNetwork parent) {
        networks.add(parent);
        parentCount.number++;
        if (parent.getAge() <= 1) parentCount.initial++;
    }

    static void birth(Genome parent) {
        Genome clone = new Genome(parent);
        Mutatable child = Mutatable.mutate(clone);
        int mutationCount = Resource.random.nextInt(Settings.MAX_MUTATIONS) + 1;
        for (int count = 0; count < mutationCount; count++) {
            child.applyMutation();
            child.sort();
        }
        networks.add(new EvolvedNetwork(child));
    }

    private static void fission(Species s) { //Called by speciate when there is only one member so recombination is not possible.
        EvolvedNetwork parent = s.getMembers().get(0);
        addParent(parent);
        for (int c = 1; c < s.getOffspring(); c++) {
            birth(parent.getGenome());
        }
    }

    private static void recombine(Species species) {
        List<EvolvedNetwork> members = species.getMembers();
        int numParents = species.size() / Settings.CHILDREN_PER_PARENT;
        if (numParents < 2) numParents = 2;

        for (int i = 0; i < numParents; i++) {
            addParent(members.get(i));
        }

        for (int c = numParents, parent2Index = 1; c < species.getOffspring(); c++, parent2Index++) {
            int parent1Index = (c / Settings.CHILDREN_PER_PARENT) % numParents;

            if (parent2Index == parent1Index) parent2Index++;
            if (parent2Index >= numParents) {
                if (parent1Index != 0) parent2Index = 0;
                else parent2Index = 1;
            }
          
            EvolvedNetwork parent1 = members.get(parent1Index);
            EvolvedNetwork parent2 = members.get(parent2Index);
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
        parentCount = new ParentsCounter();
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
        int numParents = Settings.POPULATION / Settings.CHILDREN_PER_PARENT;

        List<EvolvedNetwork> parents = new ArrayList<>(Population.getNetworks().subList(0, numParents));
        List<EvolvedNetwork> networks = Population.getNetworks();
        networks.clear();
        ID.resetInnovationIDs();

        for (int p1 = 0, p2 = 1; p1 < numParents; p1++) {
            Genome parent1 = parents.get(p1).getGenome();
            for (int c = 0; c < Settings.CHILDREN_PER_PARENT; c++, p2++) {
                if (p2 == p1) p2++;
                if (p2 >= numParents) p2 = 0;
                
                Genome recombination;
                if (p1 < p2) recombination = Crossover.recombine(parent1, parents.get(p2).getGenome());
                else recombination = Crossover.recombine(parents.get(p2).getGenome(), parent1);
                Mutatable child = Mutatable.mutate(recombination);
                int mutationCount = Resource.random.nextInt(Settings.MAX_MUTATIONS) + 1;
                for (int count = 0; count < mutationCount; count++) {
                    child.applyMutation();
                    child.sort();
                }
                networks.add(new EvolvedNetwork(child));
            }
        }
    }

    public static void fission() { //Asexual reproduction
        int numParents = Settings.POPULATION / Settings.CHILDREN_PER_PARENT;

        List<EvolvedNetwork> parents = new ArrayList<>(Population.getNetworks().subList(0, numParents));
        List<EvolvedNetwork> networks = Population.getNetworks();
        networks.clear();
        ID.resetInnovationIDs();

        for (int p = 0; p < numParents; p++) {
            EvolvedNetwork parent = parents.get(p);
            for (int c = 0; c < Settings.CHILDREN_PER_PARENT; c++) {
                Genome clone = new Genome(parent.getGenome());
                Mutatable child = Mutatable.mutate(clone);
                child.applyMutation();
                networks.add(new EvolvedNetwork(child));
            }
        }
    }

}
