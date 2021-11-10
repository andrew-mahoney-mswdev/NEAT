package evolution;

import java.util.List;
import java.util.ArrayList;

import genotype.Genome;
import genotype.Mutatable;
import main.Settings;

public abstract class Population {
    private static List<EvolvedNetwork> networks = null;

    static List<EvolvedNetwork> getNetworks() {
        return networks;
    }

    static void setNetworks(List<EvolvedNetwork> _networks) {
        networks = _networks;
    }

    public static void initialise() {
        networks = new ArrayList<EvolvedNetwork>();

        Genome luca = Genome.getFirstGenome();
        for (int i = 0; i < Settings.POPULATION; i++) {
            Genome lucaClone = new Genome(luca);
            Mutatable child = Mutatable.mutate(lucaClone);
            child.reWeight();
            EvolvedNetwork network = new EvolvedNetwork(child);
            networks.add(network);
        }
    }

}
