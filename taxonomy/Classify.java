package taxonomy;

import java.util.List;

import evolution.EvolvedNetwork;
import evolution.Population;
import main.Settings;

import java.util.ArrayList;

public abstract class Classify {
    private static List<Species> taxa;

    public static void initialise(EvolvedNetwork first) {
        taxa = new ArrayList<Species>();
        Species s = new Species(first);
        taxa.add(s);
        go();
    }

    public static void go() {
        for (Species s : taxa) {
            s.members.clear();
        }

        for(EvolvedNetwork en : Population.getUnmodifiableNetworks()) {
            boolean classified = false;            
            for (int i = 0; i < taxa.size(); i++) {
                Species species = taxa.get(i);
                double delta = Delta.calculate(en.getGenome(), species.getSpecimen());
                
                if (delta < Settings.DELTA_THRESHOLD) {
                    species.members.add(en);
                    classified = true;
                    break;
                }
            }
            
            if (classified == false) { //New species discovered
                taxa.add(new Species(en));
            }
        }

        for (int i = 0; i < taxa.size(); i++) {
            Species species = taxa.get(i);
            if (species.members.isEmpty()) {
                taxa.remove(species);
            } else {
                species.chooseSpecimen();
            }
        }
    }

    public static void print() {
        System.out.println("Species");
        for (Species s : taxa) {
            System.out.println(s);
        }
    }
}
