package pl.edu.mimuw.matrix;

public abstract class AbstractDiagonal extends AbstractMatrix {


    protected AbstractDiagonal(int rows, int doubles) {
        super(rows, doubles);
    }

    @Override
    public String toString() {
        StringBuilder wynik = new StringBuilder();
        wynik.append(super.toString());
        for (int i = 0; i < shape.rows; i++) {
            if (i > 2) {
                wynik.append("0 ... 0 ");
            } else {
                wynik.append("0 ".repeat(i));
            }
            wynik.append(this.get(i, i));
            if (shape.columns - i > 3) {
                wynik.append(" 0 ... 0");
            } else {
                wynik.append(" 0".repeat(Math.max(0, shape.rows - 1 - i)));
            }
            wynik.append('\n');
        }
        return wynik.toString();
    }

}
