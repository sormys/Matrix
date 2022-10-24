package pl.edu.mimuw.matrix;

public abstract class AbstractDiagonalWithValues extends AbstractDiagonal{
    protected final double[] values;

    public AbstractDiagonalWithValues(double... diagonalValues){
        super(diagonalValues.length, diagonalValues.length);
        values = new double[diagonalValues.length];
        System.arraycopy(diagonalValues, 0, values, 0, diagonalValues.length);
    }

    @Override
    public double normOne() {
        double max = values[0];
        for (double value : values) {
            if (Math.abs(value) > max) {
                max = Math.abs(value);
            }
        }
        return max;
    }

    @Override
    public double normInfinity() {
        return normOne();
    }

    @Override
    public double frobeniusNorm() {
        double tmp = 0;
        for (double value : values) {
            tmp += value * value;
        }
        return Math.sqrt(tmp);
    }

}
