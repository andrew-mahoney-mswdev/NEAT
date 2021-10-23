package genotype;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import main.Settings;

public class Genome {
//STATIC IMPLEMENTATION
    protected static List<Node> nodes = new ArrayList<>();

    static {
        for (int in = 0; in < Settings.SENSOR; in++) {
            nodes.add(Node.newSensorNode());
        }

        for (int out = 0; out < Settings.OUTPUT; out++) {
            nodes.add(Node.newOutputNode());
        }
    }

    public static List<Node> getNodes() {return Collections.unmodifiableList(nodes);}

    public static Node getNode(int id) {
        for(Node n: nodes) {
            if (n.getID() == id) return n;
        }
        throw new NoSuchElementException("node.id " + id + " not found by Genome.getNode()");
    }

//INSTANCE IMPLEMENTATION
    protected List<Connection> connections;

    protected Genome() {connections = new ArrayList<>();}

    public static Genome getFirstGenome() {
        Genome first = new Genome();

        int totalNodes = Settings.SENSOR + Settings.OUTPUT;
        for (int in = 0; in < Settings.SENSOR; in++) {
            for (int out = Settings.SENSOR; out < totalNodes; out++) {
                first.connections.add(new Connection(in, out, 0.0));
            }
        }

        return first;
    }

    public Genome(Genome parent) {
        connections = new ArrayList<>();
        for (Connection pc : parent.connections) {
            connections.add(new Connection(pc));
        }
    }

    public List<Connection> getConnections() {return Collections.unmodifiableList(connections);}

    public String toString() {
        String endl = "\n";
        String value = "Genome" + endl;
        for (Connection c : connections) {value += c  + endl;}
        return value;
    }

    public static void main(String... args) {
        int test = 0;
        Genome genome = null;

        System.out.println("TESTING class Genome");
        System.out.println("Testing static features.");
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
        System.out.println("Testing instance features.");
        do {
            switch (test) {
            case 0:
                System.out.println("Calling getNodes()...");
                System.out.println(getNodes());
            break;
            case 1:
                System.out.println("Calling getConnections()...");
                System.out.println(genome.getConnections());
            break;
            }
            test++;
        } while (test < 2);

        test = 0;
        Node node = null;
        do {
            switch (test) {
            case 0:
                System.out.println("Calling getNode(1)...");
                node = getNode(1);
            break;
            case 1:
                System.out.println("Calling getNode(1000)... (expecting NoSuchElementException)");
                node = getNode(1000);
            break;
            }
            
            System.out.println(node);
            test++;
        } while (test < 2);
    }

}
