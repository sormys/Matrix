package pl.edu.mimuw.matrix;

public class Diagonal extends AbstractDiagonalWithValues {


    public Diagonal(double... diagonalValues) {
        super(diagonalValues);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        if (row == column) {
            return values[row];
        } else {
            return 0;
        }
    }
}
