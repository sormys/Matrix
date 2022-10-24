package pl.edu.mimuw.matrix;

public final class MatrixCellValue {

  public final int row;
  public final int column;
  public final double value;

  public MatrixCellValue(int row, int column, double value) {
    this.column = column;
    this.row = row;
    this.value = value;
  }

  @Override
  public String toString() {
    return "{" + value + " @[" + row + ", " + column + "]}";
  }

  public boolean isBigger(MatrixCellValue other) {
    if(this.row > other.row){
      return true;
    }
    else if(this.row < other.row){
      return false;
    }
    else {
      return Integer.compare(this.column, other.column) >= 0;
    }
  }

  public static MatrixCellValue cell(int row, int column, double value) {
    return new MatrixCellValue(row, column, value);
  }
}
