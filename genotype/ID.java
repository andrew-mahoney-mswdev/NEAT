package genotype;

class ID {
    private static int nodeID = 0;
    private static int innovationID = 0;

    static int nextNodeID() {
        int id = nodeID;
        nodeID++;
        return id;
    }

    static int nextInnovationID() {
        int id = innovationID;
        innovationID++;
        return id;
    }

    public static void main(String... args) {
        System.out.println("TESTING class ID");
        System.out.println("Calling nextNodeID() 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextNodeID());
        }

        System.out.println("Calling nextInnovationID() 5 times...");
        for (int i = 0; i < 5; i++) {
            System.out.println(nextInnovationID());
        }
    }
}
