package evolution;

import genotype.Genome;
import genotype.Phenome;
import main.Settings;

public abstract class Demo {
    public static void main(String... args) {      
        System.out.println("Seeding initial population...");
        Population.initialise();

        int generation = 0;
        do {
            System.out.println("Generation " + generation);
            Evaluate.go();

            int highest, lowest;
            highest = Population.getNetworks().get(0).getFitness();
            lowest = Population.getNetworks().get(Population.SIZE-1).getFitness();
            
            System.out.println(lowest + "-" + highest);
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
            
            Reproduce.sexual();
            generation++;
        } while (true);
    }

}
