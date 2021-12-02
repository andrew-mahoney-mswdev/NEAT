package evolution;

import genotype.Genome;
import genotype.Phenome;
import taxonomy.Classify;
import main.Resource;
import main.Settings;

public abstract class Demo {
    public static void main(String... args) {      
        System.out.println("Seeding initial population...");
        Population.initialise();
        Classify.initialise(Population.getNetworks().get(0));

        int record = 0;
        int generation = 0;
        do {
            System.out.println("Generation " + generation);
            Evaluate.go();
            Classify.go();
            System.out.println("Species: " + Classify.getSpeciesCount());
            System.out.println("delta " + (float)Resource.getDelta());
            Classify.calculateOffspring();
            Classify.print();

            int highest;
            highest = Population.getNetworks().get(0).getFitness();
            
            System.out.println("Highest: " + highest);
            if (highest > record) record = highest;
            System.out.println("Record: " + record);
            if (highest == Settings.TASKS_FOR_OPTIMAL) {
                Genome genome = Population.getNetworks().get(0).getGenome();
                System.out.println(genome);
                Phenome phenome = new Phenome(genome);
                Settings.TASK.next();
                phenome.putInputs(Settings.TASK.getInputs());
                phenome.run();
                System.out.println(phenome);
                System.exit(0);
            }
            
            Reproduce.speciate();
            Resource.adjustDelta();
            System.out.println();
            generation++;
        } while (true);
    }

}
