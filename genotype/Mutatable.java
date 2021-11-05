package genotype;

import java.util.List;
import java.util.ArrayList;

import main.*;

public class Mutatable extends Genome {
    private Mutatable() {}

    public static Mutatable mutate(Genome genome) {
        Mutatable mutatable = new Mutatable();
        mutatable.connections = genome.connections;
        return mutatable;
    }

    public List<Integer[]> getPossibleConnections() { //Returns a list of possible connections that do or don't exist.
        List<Integer[]> possibleConnections = new ArrayList<Integer[]>();
        List<Node> localNodes = getLocalNodes();
        
        for (int i1 = 0; i1 < localNodes.size(); i1++) {
            Node node1 = localNodes.get(i1);
            float node1Layer = node1.getLayer();
            for (int i2 = i1+1; i2 < localNodes.size(); i2++) {
                Node node2 = localNodes.get(i2);
                float node2Layer = node2.getLayer();
                
                if (node1Layer != node2Layer) {
                    int in, out;
                    if (node1Layer < node2Layer) {in = node1.getID(); out = node2.getID();}
                    else {out = node1.getID(); in = node2.getID();}

                    possibleConnections.add(new Integer[]{in, out});
                }
            }
        }
        
        return possibleConnections;
    }

    public Connection getRandomConnection() { //Gets an enabled connection
        List<Connection> enabledConnections = getEnabledConnections();

        int index = Resource.random.nextInt(enabledConnections.size());
        return enabledConnections.get(index);
    }

    public Connection reWeight() {
        Connection connection = getRandomConnection();
        double weight = connection.getWeight();
        weight += Resource.nextSignedDouble(Settings.MUTATION_WEIGHT_SHIFT_MAX);
        connection.setWeight(weight);
        return connection;
    }

    public Node addNode() {
        Connection oldConnection = getRandomConnection();
        int in = oldConnection.getIn();
        int out = oldConnection.getOut();
        double weight = oldConnection.getWeight();

        float fromLayer = getNode(in).getLayer();
        float toLayer = getNode(out).getLayer();
        float layer = (fromLayer + toLayer) / 2;

        Node node = Node.newHiddenNode(layer);
        int id = node.getID();
        Connection leadIn = new Connection(in, id, 1.0);
        Connection leadOut = new Connection(id, out, weight);

        nodes.add(node);
        connections.add(leadIn);
        connections.add(leadOut);
        oldConnection.disable();
        
        return node;
    }

    public Connection addConnection() {
        List<Integer[]> possibleConnections = getPossibleConnections();

        if (possibleConnections.size() > 0) {
            int index = Resource.random.nextInt(possibleConnections.size());
            Integer[] line = possibleConnections.get(index);

            Connection oldConnection = getConnection(line[0], line[1], true);
            if (oldConnection != null) oldConnection.disable();

            double weight = Resource.nextSignedDouble(Settings.MUTATION_NEW_CONNECTION_WEIGHT_MAX);
            Connection connection = new Connection(line[0], line[1], weight);
            connections.add(connection);
            return connection;
        } else {
            return null;
        }
    }

    public void applyMutation() {
        if (Resource.random.nextBoolean()) {
            reWeight();
        } else {
            if (Resource.random.nextBoolean()) {
                addNode();
            } else {
                addConnection();
            }
        }
    }

    public static void main(String... args) {
        int test = 0;
        Genome genome = null;
        Mutatable mutatable = null;

        do {
            switch (test) {
            case 0:
                System.out.println("Calling genome = getFirstGenome()...");
                genome = getFirstGenome();
                System.out.println("Calling mutatable = mutate(genome)...");
                mutatable = mutate(genome);
            break;
            case 1:
                System.out.println("Calling mutatable.reWeight()...");
                System.out.println(mutatable.reWeight());
            break;
            case 2:
                System.out.println("Calling mutatable.addNode()...");
                System.out.println(mutatable.addNode());
            break;
            case 3:
                System.out.println("Calling mutatable.addConnection()...");
                System.out.println(mutatable.addConnection());
                System.out.println(mutatable.addConnection());
            break;
            }
           
            System.out.println("Calling mutatable.getLocalNodes()...");
            List<Node> localNodes = mutatable.getLocalNodes();
            System.out.println("size = " + localNodes.size());
            System.out.println(localNodes);
            for (Node n : nodes) System.out.println(n);

            System.out.println("Calling mutatable.getEnabledConnections()...");
            List<Connection> enabledConnections = mutatable.getEnabledConnections();
            System.out.println("size = " + enabledConnections.size());
            System.out.println(enabledConnections);
            System.out.println(mutatable);

            System.out.println("Calling mutatable.getPossibleConnections()...");
            List<Integer[]> possibleConnections = mutatable.getPossibleConnections();
            System.out.println("size = " + possibleConnections.size());
            for (Integer[] line : possibleConnections) System.out.println(line[0] + " -> " + line[1]);

            System.out.println();
            test++;
        } while (test < 4);
    }

}
