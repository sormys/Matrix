package pl.edu.mimuw.matrix;

public abstract class AbstractLineMatrix extends AbstractMatrix {
    protected final double[] values;

    public AbstractLineMatrix(Shape shape, double... columnValues){
        super(shape.rows, shape.columns);
        values = new double[columnValues.length];
        System.arraycopy(columnValues, 0, values, 0, columnValues.length);
    }

    private IDoubleMatrix scalarOperator(double scalar, boolean isPlus) {
        double[] tmp = new double[values.length];
        for(int i = 0; i < values.length; i++){
            tmp[i]  =  isPlus ? values[i] + scalar : values[i] * scalar;
        }
        return new ColumnMatrix(shape, tmp);
    }
    @Override
    public IDoubleMatrix times(double scalar) {
        return scalarOperator(scalar, false);
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        return scalarOperator(scalar, true);
    }
    protected double maxAbsValue() {
        double max = 0;
        for(double v: values){
            if(Math.abs(v) > max){
                max = Math.abs(v);
            }
        }
        return max;
    }

    protected double sumOfAbsValues() {
        double sum = 0;
        for(double v: values){
            sum += Math.abs(v);
        }
        return sum;
    }

    protected double valuesSquared() {
        double sum = 0;
        for(double v: values){
            sum += v*v;
        }
        sum *= shape.rows;
        return Math.sqrt(sum);
    }

}
