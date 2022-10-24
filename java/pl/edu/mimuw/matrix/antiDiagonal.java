package pl.edu.mimuw.matrix;

public class antiDiagonal extends AbstractDiagonalWithValues {

    public antiDiagonal(double... diagonalValues) {
        super(diagonalValues);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        if (row == shape.columns - column - 1) {
            return values[row];
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder wynik = new StringBuilder();
        wynik.append("Columns: ").append(shape.columns).append(" Rows: ").append(shape.rows).append('\n');
        for (int i = 0; i < shape.rows; i++) {
            if (shape.columns - i > 3) {
                wynik.append("0 ... 0 ");
            } else {
                wynik.append("0 ".repeat(shape.columns - i - 1 ));
            }
            wynik.append(values[i]);
            if (i >= 3) {
                wynik.append(" 0 ... 0");
            } else {
                wynik.append(" 0".repeat(i));
            }
            wynik.append('\n');
        }
        return wynik.toString();
    }
}
