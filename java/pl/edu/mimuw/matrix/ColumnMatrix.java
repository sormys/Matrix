package pl.edu.mimuw.matrix;

public class ColumnMatrix extends AbstractLineMatrix{

    public ColumnMatrix(Shape shape,double... columnValues){
        super(shape, columnValues);
    }

    public double get(int row, int column){
        shape.assertInShape(row, column);
        return values[column];
    }

    @Override
    public double normOne() {
        return maxAbsValue() * shape.rows;
    }

    @Override
    public double normInfinity() {
        return sumOfAbsValues();
    }

    @Override
    public double frobeniusNorm() {
        double sum = valuesSquared();
        sum *= shape.rows;
        return Math.sqrt(sum);
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        for(int i = 0 ; i < shape.rows; i++){
            for(int j = 0; j < shape.columns; j++) {
                result.append(values[j]);
                if(j < shape.columns - 1){
                    result.append(" ");
                }
            }
            result.append('\n');
        }
        return result.toString();
    }
}
