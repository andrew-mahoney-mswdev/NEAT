package evolution;

import genotype.Genome;
import genotype.Phenome;
import taxonomy.Classify;
import main.Settings;

public abstract class Demo {
    public static void main(String... args) {      
        System.out.println("Seeding initial population...");
        Population.initialise();
        Classify.initialise(Population.getNetworks().get(0));
        Classify.print();

        int generation = 0;
        do {
            System.out.println("Generation " + generation);
            Evaluate.go();
            Classify.go();
            Classify.print();

            double highest, lowest;
            highest = Population.getNetworks().get(0).getFitness();
            lowest = Population.getNetworks().get(Settings.POPULATION-1).getFitness();
            
            System.out.println(highest + "-" + lowest);

            Genome top = Population.getNetworks().get(0).getGenome();
            if (Evaluate.checkOptimality(top)) {
                System.out.println(top);
                Phenome phenome = new Phenome(top);
                Settings.TASK.next();
                phenome.putInputs(Settings.TASK.getInputs());
                phenome.run();
                System.out.println(phenome);
                System.exit(0);
            }
            
            Reproduce.sexual();
            generation++;
        } while (true);
    }

}
