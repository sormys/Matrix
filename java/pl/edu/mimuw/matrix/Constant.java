package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class Constant extends AbstractMatrix {
    private final double value;

    public Constant(Shape shape, double matrixValue){
        super(shape.rows, shape.columns);
        value = matrixValue;
    }

    @Override
    public double get(int row, int column){
        shape.assertInShape(row, column);
        return value;
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        if(scalar == 0){
            return this;
        }
        else {
            return new Constant(shape, value + scalar);
        }
    }

    @Override
    public double[][] data() {
        double[][] data = new double[shape.rows][shape.columns];
        for(int i = 0; i < shape.rows; i++){
            Arrays.fill(data[i], value);
        }
        return data;
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if(scalar == 1){
            return this;
        }
        else {
            return new Constant(shape, value * scalar);
        }
    }

    @Override
    public double normOne() {
        return Math.abs(shape.rows * value);
    }

    @Override
    public double normInfinity() {
        return Math.abs(shape.columns * value);
    }

    @Override
    public double frobeniusNorm() {
        return shape.columns * shape.rows * value * value;
    }


    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        //result.append("Columns: ").append(shape.columns).append(" Rows: ").append(shape.rows).append('\n');
        result.append(super.toString());
        if(shape.columns >= 3){
            for(int i = 0; i < shape.rows; i++){
                result.append(value).append(" ... ").append(value).append("\n");
            }
        }
        else{
            for(int i = 0; i < shape.rows; i++){
                result.append(value);
                if(shape.columns > 1){
                    result.append(" ").append(value);
                }
                result.append('\n');
            }
        }
        return result.toString();
    }
}
