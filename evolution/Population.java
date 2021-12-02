package evolution;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import genotype.Genome;
import main.Settings;

public abstract class Population {
    private static List<EvolvedNetwork> networks = null;

    static List<EvolvedNetwork> getNetworks() {
        return networks;
    }

    static void setNetworks(List<EvolvedNetwork> _networks) {
        networks = _networks;
    }

    public static List<EvolvedNetwork> getUnmodifiableNetworks() {
        return Collections.unmodifiableList(networks);
    }

    public static void initialise() {
        networks = new ArrayList<EvolvedNetwork>();

        Genome luca = Genome.getFirstGenome();
        int parents = Settings.POPULATION / Settings.CHILDREN_PER_PARENT;
        for (int i = 0; i < parents; i++) {
            Genome lucaClone = new Genome(luca);
            EvolvedNetwork network = new EvolvedNetwork(lucaClone);
            networks.add(network);
        }

        Reproduce.fission(); //Apply a basic set of mutations to everything.
    }

    public static void initialise4Testing(List<EvolvedNetwork> testNetworks) {
        networks = testNetworks;
    }

}
