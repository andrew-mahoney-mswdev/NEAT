package evolution;

import java.util.List;
import java.util.ArrayList;

import genotype.Genome;
import genotype.Mutatable;
import genotype.ID;
import main.Settings;

public abstract class Reproduce {
    private static final int numParents = Settings.PARENTS_PER_GENERATION;

    public static void go() {
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
