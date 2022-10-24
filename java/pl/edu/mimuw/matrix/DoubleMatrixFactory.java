package pl.edu.mimuw.matrix;

public class DoubleMatrixFactory {

  private DoubleMatrixFactory() {
  }

  public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values){
    if(values == null){
      return new Zero(shape);
    }
    return new Sparse(shape, values);
  }

  public static IDoubleMatrix full(double[][] values) {
    assert values != null;
    assert values.length > 0;
    return new Full(values);
  }

  public static IDoubleMatrix identity(int size) {
    assert size > 0;
    return new Identity(size);
  }

  public static IDoubleMatrix diagonal(double... diagonalValues) {
    assert diagonalValues != null;
    return new Diagonal(diagonalValues);
  }

  public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
    assert antiDiagonalValues != null;
    return new antiDiagonal(antiDiagonalValues);
  }

  public static IDoubleMatrix vector(double... values){
    assert values != null;
    return new Vector(values);
  }

  public static IDoubleMatrix zero(Shape shape) {
    assert shape != null;
    return new Zero(shape);
  }
  public static IDoubleMatrix rowMatrix(Shape shape, double... rowValues) {
    assert shape != null;
    assert rowValues != null;
    assert shape.rows == rowValues.length;
    return new RowMatrix(shape, rowValues);
  }

  public static IDoubleMatrix columnMatrix(Shape shape, double... rowValues) {
    assert shape != null;
    assert rowValues != null;
    assert shape.columns == rowValues.length;
    return new ColumnMatrix(shape, rowValues);
  }

  public static IDoubleMatrix constant(Shape shape, double matrixValue) {
    assert shape != null;
    return new Constant(shape, matrixValue);
  }
}


