package genotype;

public class Connection {
    private int in;
    private int out;
    private double weight;
    private boolean enabled;
    private int innovation;

    Connection(int in, int out, double weight) {
        this.in = in;
        this.out = out;
        this.weight = weight;
        this.enabled = true;
        this.innovation = ID.nextInnovationID();
    }

    Connection(Connection parent) {
        this.in = parent.in;
        this.out = parent.out;
        this.weight = parent.weight;
        this.enabled = parent.enabled;
        this.innovation = parent.innovation;
    }

    void setWeight(double weight) {this.weight = weight;}
    void disable() {enabled = false;}

    public int getIn() {return in;}
    public int getOut() {return out;}
    public double getWeight() {return weight;}
    public boolean isEnabled() {return enabled;}
    public int getInnovation() {return innovation;}

    public String toString() {
        return "Connection in from " + in + " out to " + out + " with weight " + weight + ", " + (enabled ? "enabled" : "disabled") + ", innovation " + innovation;
    }

    public static void main(String... args) {
        int test = 0;
        Connection connection = null;

        System.out.println("TESTING class Connection");
        do {
            switch (test) {
            case 0:
                System.out.println("Calling new Connection(0, 1, 0.5)...");
                connection = new Connection(0, 1, 0.5);
            break;
            case 1:
                System.out.println("Calling setWeight(-1.5)...");
                connection.setWeight(-1.5);
            break;
            case 2:
                System.out.println("Calling disable()...");
                connection.disable();
            break;
            case 3:
                System.out.println("Calling new Connecton(connection)...");
                connection = new Connection(connection);
            break;
            }
            
            System.out.println(connection);
            test++;
        } while (test < 4);
    }

}
