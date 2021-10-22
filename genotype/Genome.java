package genotype;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import main.Settings;

public class Genome {
    protected List<Node> nodes;
    protected List<Connection> connections;

    protected Genome() {}
    public static Genome getFirstGenome() {
        Genome first = new Genome();
        first.nodes = new ArrayList<>();
        first.connections = new ArrayList<>();

        for (int in = 0; in < Settings.SENSOR; in++) {
            first.nodes.add(Node.newSensorNode());
        }
        for (int out = 0; out < Settings.OUTPUT; out++) {
            first.nodes.add(Node.newOutputNode());
        }

        int totalNodes = Settings.SENSOR + Settings.OUTPUT;
        for (int in = 0; in < Settings.SENSOR; in++) {
            for (int out = Settings.SENSOR; out < totalNodes; out++) {
                first.connections.add(new Connection(in, out, 0.0));
            }
        }

        return first;
    }

    public Genome(Genome parent) {
        nodes = new ArrayList<>(parent.nodes);
        connections = new ArrayList<>(parent.connections);
    }

    public Node getNode(int id) {
        for(Node n: nodes) {
            if (n.getID() == id) return n;
        }
        throw new NoSuchElementException("node.id " + id + " not found by mutatable.getNode()");
    }

    public List<Node> getNodes() {return Collections.unmodifiableList(nodes);}
    public List<Connection> getConnections() {return Collections.unmodifiableList(connections);}

    public String toString() {
        String endl = "\n";
        String value = "Genome" + endl;
        value += "Nodes" + endl;
        for (Node n : nodes) {value += n + endl;}
        value += "Connections"  + endl;
        for (Connection c : connections) {value += c  + endl;}
        return value;
    }

    public static void main(String... args) {
        int test = 0;
        Genome genome = null;

        System.out.println("TESTING class Genome");
        do {
            switch (test) {
            case 0:
                System.out.println("Calling getFirstGenome()...");
                genome = getFirstGenome();
            break;
            case 1:
                System.out.println("Calling new Genome(genome)...");
                genome = new Genome(genome);
            break;
            }
            
            System.out.println(genome);
            test++;
        } while (test < 2);

        test = 0;
        Node node = null;

        do {
            switch (test) {
            case 0:
                System.out.println("Calling getNode(1)...");
                node = genome.getNode(1);
            break;
            case 1:
                System.out.println("Calling getNode(1000)... (expecting NoSuchElementException)");
                node = genome.getNode(1000);
            break;
            }
            
            System.out.println(node);
            test++;
        } while (test < 2);
    }

}
