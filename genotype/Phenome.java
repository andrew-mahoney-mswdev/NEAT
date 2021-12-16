package genotype;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import main.Resource;
import main.Settings;

public class Phenome {
    private class Neuron {
        private double value;
        private float layer;
        Neuron(double value, float layer) {this.value = value; this.layer = layer;}
    }

    private Map<Integer, Neuron> neurons = new HashMap<>();
    private Genome genome;

    public Phenome(Genome genome) {
        this.genome = genome;              
        List<Node> localNodes = genome.getLocalNodes();
        
        for (Node n : localNodes) {
            neurons.put(n.getID(), new Neuron(0.0, n.getLayer()));
        }
    }

    private void sigmoid(int neuron) {
        double total = neurons.get(neuron).value;
        double sigmoid =  1.0 / (1.0 + Math.exp(-total));
        neurons.get(neuron).value = sigmoid;
    }

    private double fireSynapse(Connection connection) {
        int in = connection.getIn();
        int out = connection.getOut();
        double valueIn = neurons.get(in).value;
        double valueOut = valueIn * connection.getWeight();
        
        neurons.get(out).value += valueOut;
        return neurons.get(out).layer;
    }

    public void putInputs(double[] inputs) { //All inputs should be values between 0 and 1
        if (inputs.length < Settings.SENSOR) throw new IllegalArgumentException("phenome.getInputs() received a number of arguments fewer than Settings.SENSOR");
        for (int i = 0; i < Settings.SENSOR; i++) {
            neurons.get(i).value = inputs[i];
        }
    }

    public void run() {
        double layer = 0.0;
       
        do {
            double nextLayer = 1.0;
            for (Connection c : genome.getEnabledConnections()) {
                int in = c.getIn();
                if (neurons.get(in).layer == layer) {
                    double depth = fireSynapse(c);
                    if (depth < nextLayer) nextLayer = depth;
                }
            }
            
            layer = nextLayer;

            for (Integer id : neurons.keySet()) {
                if (neurons.get(id).layer == layer) {
                    sigmoid(id);
                }
            }

        } while (layer < 1.0);
    }

    public double[] close() {
        double[] outputs = new double[Settings.OUTPUT];

        for (int arrayIndex = 0, id = Settings.SENSOR; arrayIndex < Settings.OUTPUT; arrayIndex++, id++) {
            outputs[arrayIndex] = neurons.get(id).value;
        }

        neurons = null;
        genome = null;
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
        for (Integer id : neurons.keySet()) {
            value += (id + ": " + neurons.get(id).value + endl);
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

        for (int c = 0; c < 1; c++) {
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
