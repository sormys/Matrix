package pl.edu.mimuw.matrix;

public abstract class AbstractMatrix implements IDoubleMatrix{
    protected final Shape shape;

    protected AbstractMatrix(int rows, int columns) {
        shape = Shape.matrix(rows, columns);
    }

    protected AbstractMatrix(double... vectorValues) {
        shape = Shape.vector(vectorValues.length);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other){
        assertTimes(other);
        double[][] tmp = new double[shape.rows][other.shape().columns];
        double value = 0;
        for(int i = 0; i < shape.rows; i++){
            for(int j = 0; j < other.shape().columns; j++){
                for(int k = 0; k < shape.columns; k++){
                    value += this.get(i, k) * other.get(k,j);
                }
                tmp[i][j] = value;
                value = 0;
            }
        }
        return new Full(tmp);
    }

    @Override
    public IDoubleMatrix times(double scalar){
        if(scalar == 0){
            return new Zero(shape);
        }
        else if(scalar == 1){
            return this;
        }
        else {
            double[][] tmp = new double[shape.rows][shape.columns];
            for(int i = 0; i < shape.rows; i++){
                for(int j = 0; j < shape.columns; j++){
                    tmp[i][j] = get(i,j) * scalar;
                }
            }
            return new Full(tmp);
        }
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        if(scalar == 0){
            return this;
        }
        else{
            double[][] tmp = new double[shape.rows][shape.columns];
            for(int i = 0; i < shape.rows; i++){
                for(int j = 0; j < shape.columns; j++){
                    tmp[i][j] = this.get(i,j) + scalar;
                }
            }
            return new Full(tmp);
        }
    }

    @Override
    public IDoubleMatrix minus(double scalar){
        return this.plus(-scalar);
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assertPlusMinus(other);
        double[][] tmp = new double[shape.rows][shape.columns];
        for(int i = 0; i < shape.rows; i++){
            for(int j = 0; j < shape.columns; j++){
                tmp[i][j] = this.get(i,j) + other.get(i,j);
            }
        }
        return new Full(tmp);
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        assertPlusMinus(other);
        double[][] tmp = new double[shape.rows][shape.columns];
        for(int i = 0; i < shape.rows; i++){
            for(int j = 0; j < shape.columns; j++){
                tmp[i][j] = this.get(i,j) - other.get(i,j);
            }
        }
        return new Full(tmp);
    }

    @Override
    public double[][] data(){
        double[][] tmp = new double[shape.rows][shape.columns];
        for(int i = 0; i < shape.rows; i++){
            for(int j = 0; j < shape.columns; j++){
                tmp[i][j] = get(i,j);
            }
        }
        return tmp;
    }


    @Override
    public double normOne(){
        double max = 0;
        double column = 0;
        for(int i = 0; i < shape.columns; i++) {
            for(int j = 0; j < shape.rows; j++){
                column += Math.abs(get(j,i));
            }
            if(column > max) {
                max = column;
            }
            column = 0;
        }
        return max;
    }

    @Override
    public double normInfinity(){
        double max = 0;
        double row = 0;
        for(int i = 0; i < shape.rows; i++) {
            for(int j = 0; j < shape.columns; j++){
                row += Math.abs(get(i,j));
            }
            if(row > max) {
                max = row;
            }
            row = 0;
        }
        return max;
    }

    @Override
    public double frobeniusNorm() {
        double sumOfSquares = 0;
        for(int i = 0; i < shape.rows; i++) {
            for(int j = 0; j < shape.columns; j++){
                sumOfSquares += get(i,j) * get(i,j);
            }
        }
        return Math.abs(sumOfSquares);
    }

    protected void assertTimes(IDoubleMatrix other){
        assert this.shape.columns == other.shape().rows;
    }

    protected void assertPlusMinus(IDoubleMatrix other){
       shape.equals(other.shape());
    }

    @Override
    public Shape shape(){
        return shape;
    }

    @Override
    public String toString() {
        return "Rows: " +  shape.rows + " Columns: " + shape.columns + '\n';
    }
}
