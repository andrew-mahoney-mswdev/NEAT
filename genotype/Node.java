package genotype;

public class Node {
    private int id;
    private NodeType type;
    private float layer;

    private Node(NodeType type, float layer) {
        this.id = ID.nextNodeID();
        this.type = type;
        this.layer = layer;
    }

    static public Node newSensorNode() {
        return new Node(NodeType.SENSOR, 0.0f);
    }

    static public Node newOutputNode() {
        return new Node(NodeType.OUTPUT, 1.0f);
    }

    static public Node newHiddenNode(float layer) {
        return new Node(NodeType.HIDDEN, layer);
    }

    public int getID() {return id;}
    public NodeType getType() {return type;}
    public float getLayer() {return layer;}

    public String toString() {
        return "Node " + id + ": " + type + " on layer " + layer;
    }

    public static void main(String... args) {
        int test = 0;
        Node node = null;
        
        System.out.println("TESTING class Node");
        do {
            switch (test) {
            case 0:
                System.out.println("Calling newSensorNode()...");
                node = newSensorNode();
            break;
            case 1:
                System.out.println("Calling newOutputNode()...");
                node = newOutputNode();
            break;
            case 2:
                System.out.println("Calling newHiddenNode(0.5f)...");
                node = newHiddenNode(0.5f);
            break;
            }
            
            System.out.println(node);
            test++;
        } while (test < 3);
    }

}
