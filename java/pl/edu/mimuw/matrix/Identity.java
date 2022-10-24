package pl.edu.mimuw.matrix;

import java.util.*;

public class Identity extends AbstractDiagonal {

    public Identity(int size){
        super(size, size);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assertTimes(other);
        return other;
    }

    @Override
    public IDoubleMatrix times(double scalar){
        if(scalar == 0){
            return new Zero(shape);
        }
        else if(scalar == 1){
            return this;
        }
        else{
            double[] tmp = new double[shape.rows];
            Arrays.fill(tmp, scalar);
            return new Diagonal(tmp);
        }
    }

    @Override
    public double get(int row, int column){
        shape.assertInShape(row,column);
        if(row == column) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public double normOne(){
        return 1;
    }

    @Override
    public double normInfinity(){
        return 1;
    }

    @Override
    public double frobeniusNorm(){
        return Math.sqrt(shape.rows);
    }


}
