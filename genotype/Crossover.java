package genotype;

import java.util.List;

import main.Resource;

public class Crossover extends Genome {
    private Crossover() {}

    public static Genome recombine(Genome dominant, Genome recessive) {
        Genome child = new Genome();

        int d = 0, r = 0;
        while (d < dominant.connections.size() && r < recessive.connections.size()) {
            Connection dcon = dominant.connections.get(d);
            Connection rcon = recessive.connections.get(r);
            int dinnov = dcon.getInnovation();
            int rinnov = rcon.getInnovation();

            if (dinnov == rinnov) {
                if (Resource.random.nextBoolean()) {
                    child.connections.add(new Connection(dcon));
                } else {
                    child.connections.add(new Connection(rcon));
                }
                d++;
                r++;
            } else {
                if (dinnov < rinnov) {
                    child.connections.add(new Connection(dcon));
                    d++;
                } else { //dinnov > rinnov
                    r++;
                }
            }
        }

        while (d < dominant.connections.size()) {
            Connection dcon = dominant.connections.get(d);
            child.connections.add(new Connection(dcon));
            d++;    
        }

        return child;
    }

    public static void main(String... args) {
        Genome parentA = getFirstGenome();
        Genome parentB = new Genome(parentA);
        Mutatable dominant = Mutatable.mutate(parentA);
        Mutatable recessive = Mutatable.mutate(parentB);

        for (int i = 0; i < 16; i++) dominant.reWeight();
        
        dominant.addNode();
        recessive.addConnection();
      
        Genome recombination = recombine(dominant, recessive);
        Mutatable child = Mutatable.mutate(recombination);

        System.out.println("Nodes");
        System.out.println("size = " + nodes.size());
        for (Node n : nodes) System.out.println(n);
        System.out.println();

        System.out.println("parentA");
        System.out.println(parentA);

        System.out.println("parentB");
        System.out.println(parentB);

        System.out.println("Local nodes");
        List<Node> localNodes = child.getLocalNodes();
        System.out.println("size = " + localNodes.size());
        for (Node n : localNodes) System.out.println(n);
        System.out.println();

        System.out.println("Connections");
        System.out.println("size = " + child.connections.size());
        for (Connection c : child.connections) System.out.println(c);
        System.out.println();

        System.out.println("Possible connections");
        List<Integer[]> possibleConnections = child.getPossibleConnections();
        System.out.println("size = " + possibleConnections.size());
        for (Integer[] line : possibleConnections) System.out.println(line[0] + " -> " + line[1]);
    }
}
