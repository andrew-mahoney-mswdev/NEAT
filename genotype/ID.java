package genotype;

import java.util.Map;
import java.util.HashMap;

public abstract class ID {
    private static int nodeID = 0;
    private static int innovationID = 0;
    private static Map<String, Integer> nodes = new HashMap<>();
    private static Map<String, Integer> connections = new HashMap<>();

    static int nextNodeID() {
        return nodeID++;
    }

    static int nextNodeID(int in, int out) {
        String key = in + "-" + out;
        Integer id = nodes.get(key);
        if (id == null) {
            id = nextNodeID();
            nodes.put(key, id);
        }
        return id;
    }

    static int nextInnovationID(int in, int out) {
        String key = in + "-" + out;
        Integer id = connections.get(key);
        if (id == null) {
            id = innovationID++;
            connections.put(key, id);
        }
        return id;
    }

    public static void resetInnovationIDs() {
        nodes = new HashMap<>();
        connections = new HashMap<>();
    }

    public static void main(String... args) {
        System.out.println("TESTING class ID");
        
        System.out.println("Calling nextNodeID() 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextNodeID());
        }
        
        System.out.println("Calling nextNodeID(x, y) 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextNodeID(i, i+1));
        }

        System.out.println("Repeat calling nextNodeID(x, y) 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextNodeID(i, i+1));
        }

        System.out.println("Calling nextInnovationID(x, y) 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextInnovationID(i, i+1));
        }

        System.out.println("Repeat calling nextInnovationID(x, y) 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextInnovationID(i, i+1));
        }

        System.out.println("Calling resetInnovationIDs()");
        resetInnovationIDs();

        System.out.println("Repeat calling nextNodeID(x, y) 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextNodeID(i, i+1));
        }

        System.out.println("Repeat calling nextInnovationID(x, y) 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextInnovationID(i, i+1));
        }

        System.out.println("nodeID: " + nodeID);
        System.out.println("innovationID: " + innovationID);
    }
}
