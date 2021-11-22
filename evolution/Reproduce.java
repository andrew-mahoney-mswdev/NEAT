package evolution;

import java.util.List;
import java.util.ArrayList;

import main.Resource;
import main.Settings;
import genotype.ID;
import genotype.Genome;
import genotype.Mutatable;
import genotype.Crossover;


public abstract class Reproduce {
    private static final int numParents = Settings.POPULATION / Settings.CHILDREN_PER_PARENT;

    public static void sexual() {
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
