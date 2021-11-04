package genotype;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import main.Resource;
import main.Settings;

public class Phenome {
    private Map<Integer, Double> neurons = new HashMap<>();
    private Genome genome;
    List<Node> localNodes;

    public Phenome(Genome genome) {
        this.genome = genome;              
        localNodes = genome.getLocalNodes();
        
        for (Node n : localNodes) {
            neurons.put(n.getID(), 0.0);
        }
    }

    private void sigmoid(int neuron) {
        double total = neurons.get(neuron);
        double sigmoid =  1.0 / (1.0 + Math.exp(-total));
        neurons.put(neuron, sigmoid);
    }

    private double fireSynapse(Connection connection) {
        int in = connection.getIn();
        int out = connection.getOut();
        double valueIn = neurons.get(in);
        double valueOut = valueIn * connection.getWeight();
        double currentValueAtOutNode = neurons.get(out);
        
        neurons.put(out, currentValueAtOutNode + valueOut);
        return localNodes.get(out).getLayer();
    }

    private void putInputs(double[] inputs) { //All inputs should be values between 0 and 1
        if (inputs.length < Settings.SENSOR) throw new IllegalArgumentException("phenome.getInputs() received a number of arguments fewer than Settings.SENSOR");
        for (int i = 0; i < Settings.SENSOR; i++) {
            neurons.put(i, inputs[i]);
        }
    }

    private void run() {
        double layer = 0.0;
       
        do {
            double nextLayer = 1.0;
            for (Connection c : genome.getEnabledConnections()) {
                int in = c.getIn();
                if (localNodes.get(in).getLayer() == layer) {
                    double depth = fireSynapse(c);
                    if (depth < nextLayer) nextLayer = depth;
                }
            }
            
            layer = nextLayer;

            for (Node n : localNodes) {
                if (n.getLayer() == layer) {
                    sigmoid(n.getID());
                }
            }

        } while (layer < 1.0);
    }

    private double[] close() {
        double[] outputs = new double[Settings.OUTPUT];

        int arrayIndex = 0;
        for (int i = Settings.SENSOR; arrayIndex < Settings.OUTPUT; i++) {
            Node node = localNodes.get(i);
            if (node.getType() == NodeType.OUTPUT) {
                int id = node.getID();
                outputs[arrayIndex] = neurons.get(id);
                arrayIndex++;
            }
        }

        neurons = null;
        genome = null;
        localNodes = null;
        return outputs;
    }

    public double[] run(double[] inputs) {
        double[] outputs;
        putInputs(inputs);
        run();
        outputs = close();
        return outputs;
    }

    public String toString() {
        String endl = "\n";
        String value = "Phenome" + endl;
        for (Integer i : neurons.keySet()) {
            value += (i + ": " + neurons.get(i) + endl);
        }
        return value;
    }

    public static void main(String... args) {
        Genome genome = Genome.getFirstGenome();
        
        Mutatable mutatable = Mutatable.mutate(genome);
        boolean loop;
        do {
            loop = false;
            mutatable.reWeight();
            for (Connection c : genome.getEnabledConnections()) {
                if (c.getWeight() == 0.0) loop = true;
            }
        } while (loop == true);

        for (int c = 0; c < 6; c++) {
            mutatable.addNode();
            mutatable.addConnection();
        }

        mutatable.addConnection();

        System.out.println(genome);
        
        double[] inputs = new double[Settings.SENSOR];
        for (int i = 0; i < Settings.SENSOR; i++) {
            inputs[i] = Resource.random.nextDouble();
        }
        
        Phenome phenome = new Phenome(genome);
        phenome.putInputs(inputs);
        phenome.run();
        System.out.println(phenome);
        double[] outputs = phenome.close();

        phenome = new Phenome(genome);        
        outputs = phenome.run(inputs);
        for (int i = 0; i < Settings.OUTPUT; i++) {
            System.out.println(Settings.SENSOR + i + ": " + outputs[i]);
        }
    }
}
