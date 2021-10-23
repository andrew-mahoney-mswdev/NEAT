package genotype;

import java.util.Set;
import java.util.TreeSet;
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

    public List<Node> getLocalNodes() {
        Set<Integer> nodeIDs = new TreeSet<>();
        for (Connection c : connections) {
            if (c.isEnabled()) {
                nodeIDs.add(c.getIn());
                nodeIDs.add(c.getOut());
            }
        }

        List<Node> localNodes = new ArrayList<>();
        for (Integer id : nodeIDs) {
            localNodes.add(getNode(id));
        }
        return localNodes;
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

                    if (!hasConnection(in, out))
                        potentialConnections.add(new Integer[]{in, out});
                }
            }
        }
        
        return potentialConnections;
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

            System.out.println("Calling mutatable.getPotentialConnections()...");
            List<Integer[]> potentialConnections = mutatable.getPotentialConnections();
            System.out.println("size = " + potentialConnections.size());
            for (Integer[] line : potentialConnections) System.out.println(line[0] + " -> " + line[1]);

            System.out.println("Calling mutatable.hasConnection(0, 4)...");
            System.out.println(mutatable.hasConnection(0, 4));

            System.out.println();
            test++;
        } while (test < 4);
    }

}
