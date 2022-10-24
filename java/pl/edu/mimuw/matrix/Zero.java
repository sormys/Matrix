package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class Zero extends Constant {

    public Zero(Shape shape){
        super(shape, 0);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assertTimes(other);
        return this;
    }

    @Override
    public IDoubleMatrix times(double scalar){
        return this;
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other){
        assertPlusMinus(other);
        return other;
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other){
        assertPlusMinus(other);
        return other.times(-1);
    }

    @Override
    public double normOne(){
        return 0;
    }

    @Override
    public double normInfinity() {
        return 0;
    }
    @Override
    public double frobeniusNorm() {
        return 0;
    }

}