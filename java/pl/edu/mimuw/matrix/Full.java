package pl.edu.mimuw.matrix;

public class Full extends AbstractMatrix {
    private final double[][] values;


    public Full(double[][] matrixValues){
        super(matrixValues.length, matrixValues[0].length);
        int length =  matrixValues[0].length;
        for(double[] l: matrixValues){
            assert l.length == length;
        }
        values = new double[matrixValues.length][matrixValues[0].length];
        for(int i = 0 ;i < values.length; i++){
            System.arraycopy(matrixValues[i], 0, values[i], 0, values[0].length);
        }
    }

    @Override
    public double get(int row, int column){
        shape.assertInShape(row, column);
        return values[row][column];
    }

    @Override
    public double normOne(){
        double max = 0;
        double column = 0;
        for(int i = 0; i < shape.columns; i++) {
            for(int j = 0; j < shape.rows; j++){
                column += Math.abs(values[j][i]);
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
                row += Math.abs(values[i][j]);
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
               sumOfSquares += values[i][j] * values[i][j];
           }
       }
       return Math.sqrt(sumOfSquares);
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        for(int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns - 1; j++) {
                result.append(values[i][j]).append(" ");
            }
            result.append(values[i][shape.columns - 1]).append('\n');
        }
        return result.toString();
    }

}
