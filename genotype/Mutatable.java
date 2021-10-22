package genotype;

import java.util.List;
import java.util.ArrayList;

import main.*;

public class Mutatable extends Genome {

    public static Mutatable mutate(Genome genome) {
        Mutatable mutatable = new Mutatable();
        mutatable.nodes = genome.nodes;
        mutatable.connections = genome.connections;
        return mutatable;
    }

    public boolean hasConnection(int in, int out) { //Returns true if connection exists and is enabled
        for (Connection c : connections) {
            if (c.isEnabled() && c.getIn() == in && c.getOut() == out) return true;
        }
        return false;
    }

    public List<Connection> getEnabledConnections() {
        List<Connection> enabledConnections = new ArrayList<Connection>();
        for (Connection c: connections) {
            if (c.isEnabled()) {enabledConnections.add(c);}
        }
        return enabledConnections;
    }

    public List<Integer[]> getPotentialConnections() { //Returns a list of potential connections that currently don't exist.
        List<Integer[]> potentialConnections = new ArrayList<Integer[]>();
        
        for (int n1 = 0; n1 < nodes.size(); n1++) {
            float n1Layer = nodes.get(n1).getLayer();
            for (int n2 = n1+1; n2 < nodes.size(); n2++) {
                float n2Layer = nodes.get(n2).getLayer();
                
                if (n1Layer != n2Layer) {
                    int in, out;
                    if (n1Layer < n2Layer) {in = n1; out = n2;}
                    else {out = n1; in = n2;}

                    if (!hasConnection(in, out))
                        potentialConnections.add(new Integer[]{in, out});
                }
            }
        }
        
        return potentialConnections;
    }

    // private Node getRandomNode() {
    //     int index = Resource.random.nextInt(nodes.size());
    //     return nodes.get(index);
    // }

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
        List<Integer[]> potentialConnections = getPotentialConnections();

        if (potentialConnections.size() > 0) {
            int index = Resource.random.nextInt(potentialConnections.size());
            Integer[] line = potentialConnections.get(index);
            double weight = Resource.nextSignedDouble(Settings.MUTATION_NEW_CONNECTION_WEIGHT_MAX);
            Connection connection = new Connection(line[0], line[1], weight);
            connections.add(connection);
            return connection;
        } else {
            return null;
        }
    }

    public static void main(String... args) {
        int test = 0;
        Mutatable mutatable = null;

        System.out.println("TESTING class Mutatable");
        Genome parent = getFirstGenome();
        Genome child = new Genome(parent);
        do {
            switch (test) {
            case 0:
                System.out.println("Calling new Mutatable(getFirstGenome())...");
                mutatable = mutate(child);
            break;
            case 1:
                System.out.println("Calling mutatable.reWeight()");
                System.out.println(mutatable.reWeight());
            break;
            case 2:
                System.out.println("Calling mutatable.addNode()");
                System.out.println(mutatable.addNode());
            break;
            case 3:
                System.out.println("Calling mutatable.addConnection()");
                System.out.println(mutatable.addConnection());
            }

        System.out.println(child);
        test++;
        } while (test < 4);

        test = 0;
        do {
            switch (test) {
                case 0:
                    System.out.println("Calling mutatable.hasConnection(0, 1)...");
                    System.out.println(mutatable.hasConnection(0, 1));
                break;
                case 1:
                    System.out.println("Calling mutatable.getEnabledConnections().size()...");
                    System.out.println(mutatable.getEnabledConnections().size());
                break;
                case 2:
                    System.out.println("Calling mutatable.getPotentialConnections().size()...");
                    System.out.println(mutatable.getPotentialConnections().size());
                break;
                case 3:
                    System.out.println("Calling mutatable.getRandomConnection()...");
                    System.out.println(mutatable.getRandomConnection());
                break;
            }
            test++;
        } while (test < 4);
    }

}
