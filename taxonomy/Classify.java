package taxonomy;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import evolution.EvolvedNetwork;
import evolution.Population;
import genotype.Genome;
import genotype.Mutatable;
import main.Resource;
import main.Settings;

public abstract class Classify {
    private static List<Species> taxa;

    public static void initialise(EvolvedNetwork first) {
        taxa = new ArrayList<Species>();
        Species s = new Species(first);
        taxa.add(s);
        go();
    }

    public static void calculateOffspring() {
        double totalFitness = 0.0;
        for (Species s : taxa) {
            totalFitness += s.calculateFitness();
            s.offspring = 0;
        }
        
        sort();

        double fitnessSelect = totalFitness * Settings.SPECIES_SELECT_PROPORTION;
        double fitnessSelectConsumed = 0.0;
        for (int i = 0; i < taxa.size(); i++) {
            Species s = taxa.get(i);

            double fitnessShare;
            if (fitnessSelectConsumed + s.fitness <= fitnessSelect) {
                fitnessShare = s.fitness;
                fitnessSelectConsumed += s.fitness;
            } else { //Selectable fitness is consumed, only remaining selected fitness can be used.
                fitnessShare = fitnessSelect - fitnessSelectConsumed;
                fitnessSelectConsumed = fitnessSelect;
            }

            s.offspring = (int)((fitnessShare / fitnessSelect) * Settings.POPULATION);

            if (fitnessSelectConsumed == fitnessSelect) {
                break;
            }
        }
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
                
                if (delta < Resource.getDelta()) {
                    species.members.add(en);
                    classified = true;
                    break;
                }
            }
            
            if (classified == false) { //New species discovered
                taxa.add(new Species(en));
            }
        }

        for (Species s : taxa) {
            if (!s.members.isEmpty()) {
                s.chooseSpecimen();
            }
        }

        taxa.removeIf((s) -> s.members.isEmpty());
    }

    public static void sort() {
        Collections.sort(taxa);
    }

    public static List<Species> getTaxa() {
        return Collections.unmodifiableList(taxa);
    }

    public static int getSpeciesCount() {
        return taxa.size();
    }

    public static void print() {
        System.out.println("Species");
        for (int i = 0; i < taxa.size(); i++) {
            System.out.println(taxa.get(i));
        }
    }

    public static void main(String... args) {
        //Delta threshold should be set to 0.05 for this to work.
        Genome a = Genome.getFirstGenome();
        Genome b = Genome.getFirstGenome();
        Mutatable alpha = Mutatable.mutate(a);
        Mutatable beta = Mutatable.mutate(b);

        for (int count = 0; count < 5; count++) {
            alpha.applyMutation();
            beta.applyMutation();
        }

        List<EvolvedNetwork> testNetworks = new ArrayList<>();
        EvolvedNetwork first = EvolvedNetwork.newEvolvedNetwork4Testing(alpha);
        testNetworks.add(first);
        testNetworks.add(EvolvedNetwork.newEvolvedNetwork4Testing(beta));
        for (int count = 0; count < 5; count++) {
            Genome cloneAlpha = new Genome(alpha);
            Genome cloneBeta = new Genome(beta);
            Mutatable childAlpha = Mutatable.mutate(cloneAlpha);
            Mutatable childBeta = Mutatable.mutate(cloneBeta);
            childAlpha.applyMutation();
            childBeta.applyMutation();
            testNetworks.add(EvolvedNetwork.newEvolvedNetwork4Testing(childAlpha));
            testNetworks.add(EvolvedNetwork.newEvolvedNetwork4Testing(childBeta));
        }

        Population.initialise4Testing(testNetworks);

        Classify.initialise(first);
        Classify.go();
        Classify.print();

        //Specimen testing - Configure Delta Threshold to identify 2 species
        Species firstTaxon = taxa.get(0);
        Species secondTaxon = taxa.get(1);
        
        Genome specimenAlpha = firstTaxon.getSpecimen();
        Genome specimenBeta = secondTaxon.getSpecimen();
        
        for (int i = 0; i < testNetworks.size(); i++) {
            if (testNetworks.get(i).getGenome() == specimenAlpha) {
                System.out.println("Alpha specimen is at testNetworks:" + i);
                for (int j = 0; j < firstTaxon.members.size(); j++) {
                    if (testNetworks.get(i).getGenome() == firstTaxon.members.get(j).getGenome()) {
                        System.out.println("Alpha specimen is at taxa.get(0):" + j);
                    }
                }
            }
            if (testNetworks.get(i).getGenome() == specimenBeta) {
                System.out.println("Beta specimen is at testNetworks:" + i);
                for (int j = 0; j < secondTaxon.members.size(); j++) {
                    if (testNetworks.get(i).getGenome() == secondTaxon.members.get(j).getGenome()) {
                        System.out.println("Beta specimen is at taxa.get(1):" + j);
                    }
                }
            }
        }
    }

}
