package pl.edu.mimuw.matrix;

public class RowMatrix extends AbstractLineMatrix{

    public RowMatrix(Shape shape, double... rowValues) {
        super(shape, rowValues);
    }

    public double get(int row, int column){
        shape.assertInShape(row,column);
        return values[row];
    }

    @Override
    public double normOne() {
        return sumOfAbsValues();
    }

    @Override
    public double normInfinity() {
        return maxAbsValue() * shape.columns;
    }

    @Override
    public double frobeniusNorm() {
        double sum = valuesSquared();
        sum *= shape.columns;
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        for(int i = 0; i < shape.rows; i++){
            if(shape.columns > 3){
                result.append(values[i]).append(" ... ").append(values[i]);
            }
            else{
                for(int j = 0; j < shape.columns; j++){
                    result.append(values[i]);
                    if(j < shape.columns - 1){
                        result.append(" ");
                    }
                }
            }
            result.append('\n');
        }
        return result.toString();
    }
}
