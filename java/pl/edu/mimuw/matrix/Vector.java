package pl.edu.mimuw.matrix;

public class Vector extends AbstractMatrix {

    private final double[] values;

    public Vector(double... vectorValues){
        super(vectorValues);
        this.values = new double[vectorValues.length];
        System.arraycopy(vectorValues, 0, this.values, 0, vectorValues.length);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return values[row];
    }

    @Override
    public double normOne() {
        double result = 0;
        for(double value: values){
            result += Math.abs(value);
        }
        return result;
    }

    @Override
    public double normInfinity() {
        double max = values[0];
        for(double value: values){
            if(max < Math.abs(value)){
                max = Math.abs(value);
            }
        }
        return max;
    }

    @Override
    public double frobeniusNorm() {
        double tmp = 0;
        for (double value : values) {
            tmp += value * value;
        }
        return Math.sqrt(tmp);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        for(double value: values){
            result.append(value).append('\n');
        }
        return result.toString();
    }
}
