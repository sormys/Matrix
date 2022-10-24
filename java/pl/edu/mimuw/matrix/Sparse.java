package pl.edu.mimuw.matrix;

import java.util.ArrayList;
import java.util.Arrays;

public class Sparse extends AbstractMatrix{

    // It will store MatrixCellValues sorted by indexes and grouped by rows.
    private final ArrayList<ArrayList<MatrixCellValue>> values;

    public Sparse(Shape shape, MatrixCellValue... matrixValues){
        super(shape.rows, shape.columns);
        // Creating copy of values and sorting by indexes.
        MatrixCellValue[] matrixValuesCopy = new MatrixCellValue[matrixValues.length];
        System.arraycopy(matrixValues, 0, matrixValuesCopy, 0, matrixValues.length);
        for(MatrixCellValue cell: matrixValuesCopy){
            assert cell != null;
        }
        Arrays.sort(matrixValuesCopy, new SortMatrixCellValuesByRows());
        assertValues(matrixValuesCopy);
        values = groupByRowOrColumns(true, matrixValuesCopy);
    }

    private IDoubleMatrix newSparse(Shape shape, ArrayList<MatrixCellValue> matrixValues){
        if(matrixValues.size() == 0){
            return new Zero(shape);
        }
        MatrixCellValue[] tmp = new MatrixCellValue[matrixValues.size()];
        int i = 0;
        for(MatrixCellValue value: matrixValues){
            tmp[i] = value;
            i++;
        }
        return new Sparse(shape, tmp);
    }

    // Array must be sorted!
    private void assertValues(MatrixCellValue... matrixValues){
        shape.assertInShape(matrixValues[0].row, matrixValues[0].column);
        for(int i = 1 ; i < matrixValues.length; i++){
            shape.assertInShape(matrixValues[i].row, matrixValues[i].column);
            assert matrixValues[i].row != matrixValues[i-1].row
                    || matrixValues[i].column != matrixValues[i-1].column;
        }
    }

    // Array must be sorted accordingly!
    private ArrayList<ArrayList<MatrixCellValue>> groupByRowOrColumns(boolean byRows, MatrixCellValue[] array){
        ArrayList<ArrayList<MatrixCellValue>> tmp = new ArrayList<>();
        tmp.add(new ArrayList<>());
        int currentIndex = 0;
        for(MatrixCellValue cell: array){
            if(tmp.get(currentIndex).size() == 0
                    || groupOperator(byRows,tmp.get(currentIndex).get(0)) == groupOperator(byRows, cell)){
                tmp.get(currentIndex).add(cell);
            }
            else {
                currentIndex++;
                tmp.add(new ArrayList<>());
                tmp.get(currentIndex).add(cell);
            }
        }
        return tmp;
    }

    private static int groupOperator(boolean byRows, MatrixCellValue cell){
        return byRows ? cell.row : cell.column;
    }

    public ArrayList<ArrayList<MatrixCellValue>> getColumns(){
        MatrixCellValue[] array = new MatrixCellValue[notZeroValuesCount()];
        int i = 0;
        for(ArrayList<MatrixCellValue> list: values){
            for(MatrixCellValue cell: list){
                array[i] = cell;
                i++;
            }
        }
        Arrays.sort(array, new SortMatrixCellValuesByColumns());
        return groupByRowOrColumns(false, array);
    }

    public int notZeroValuesCount(){
        int notZeroCells = 0;
        for(ArrayList<MatrixCellValue> list: values){
            notZeroCells += list.size();
        }
        return notZeroCells;
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assertTimes(other);
        if(other.getClass() == Sparse.class){
            return times((Sparse) other);
        }
        return super.times(other);
    }

    private IDoubleMatrix times(Sparse other){
        double valueSum = 0;
        int columnIndex = 0;
        ArrayList<ArrayList<MatrixCellValue>> columns = other.getColumns();
        ArrayList<MatrixCellValue> result = new ArrayList<>();
        // Standard algorithm of multiplying matrices but skipping 0.
        for(ArrayList<MatrixCellValue> rowList: values){
            for (ArrayList<MatrixCellValue> column : columns) {
                for (MatrixCellValue cellValue : rowList) {
                    // Searching if we can multiply by not 0.
                    while (columnIndex < column.size() && column.get(columnIndex).row < cellValue.column) {
                        columnIndex++;
                    }
                    if (columnIndex < column.size() && column.get(columnIndex).row == cellValue.column) {
                        valueSum += cellValue.value * column.get(columnIndex).value;
                    }
                }
                if (valueSum != 0) {
                    result.add(new MatrixCellValue(rowList.get(0).row, column.get(0).column, valueSum));
                    valueSum = 0;
                }
                columnIndex = 0;
            }
        }
        Shape newShape = Shape.matrix(this.shape.rows, other.shape.columns);
        return newSparse(newShape, result);
    }

    private void addSubtractValue(boolean isPlus, ArrayList<MatrixCellValue> list, int maxIndex, MatrixCellValue cell){
        int left = 0, right = maxIndex;
        while (left < right)
        {
            int mid = (right + left)/ 2;
            if (!list.get(mid).isBigger(cell))
                left = mid + 1;
            else
                right = mid;
        }
        if(list.get(left).row == cell.row && list.get(left).column == cell.column){
            list.set(left, new MatrixCellValue(
                            cell.row,
                            cell.column,
                            plusMinusOperator(isPlus, list.get(left).value, cell.value)
                        ));
        }
        else{
            list.add(cell);
        }
    }

    private double plusMinusOperator(boolean isPlus, double a, double b){
        return isPlus ? a + b : a - b;
    }

    @Override
    public double[][] data() {
        double[][] tmp = new double[shape.rows][shape.columns];
        for(ArrayList<MatrixCellValue> list: values){
            for(MatrixCellValue cell: list){
                tmp[cell.row][cell.column] = cell.value;
            }
        }
        return tmp;
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assertPlusMinus(other);
        if(other.getClass() == Sparse.class){
            return plusMinus(true,(Sparse) other);
        }
        return super.plus(other);
    }

    private IDoubleMatrix plusMinus(boolean isPlus, Sparse other){
        ArrayList<MatrixCellValue> tmp = new ArrayList<>();
        for(ArrayList<MatrixCellValue> list: values){
            tmp.addAll(list);
        }
        int originalValuesCount = tmp.size() - 1;
        for(ArrayList<MatrixCellValue> list: other.values){
            for(MatrixCellValue cell: list){
                addSubtractValue(isPlus, tmp, originalValuesCount, cell);
            }
        }
        return newSparse(shape, tmp);
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        assertPlusMinus(other);
        if(other.getClass() == Sparse.class){
            return plusMinus(false, (Sparse) other);
        }
        return super.minus(other);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if(scalar == 1){
            return this;
        }
        else if(scalar == 0){
            return new Zero(shape);
        }
        else {
            int notZeroCells = notZeroValuesCount();
            MatrixCellValue[] tmp = new MatrixCellValue[notZeroCells];
            int i = 0;
            for(ArrayList<MatrixCellValue> list: values){
                for(MatrixCellValue cell: list){
                    tmp[i] = new MatrixCellValue(cell.row, cell.column, cell.value * scalar);
                    i++;
                }
            }
            return new Sparse(shape, tmp);
        }
    }

    @Override
    public double normOne() {
        double max = 0;
        double column = 0;
        for(int i = 0; i < shape.columns; i++){
            for(ArrayList<MatrixCellValue> list: values) {
                for(MatrixCellValue value: list){
                   if(value.column == i){
                       column += Math.abs(value.value);
                   }
                   else if(value.column > i){
                       break;
                   }
                }
            }
            if(column > max){
                max = column;
            }
            column = 0;
        }
        return max;
    }

    @Override
    public double normInfinity() {
        double max = 0;
        double current= 0;
        for(ArrayList<MatrixCellValue> list: values) {
            for(MatrixCellValue value: list){
                current += Math.abs(value.value);
            }
            if(current > max){
                max = current;
            }
            current = 0;
        }
        return max;
    }

    @Override
    public double frobeniusNorm() {
        double sum = 0;
        for(ArrayList<MatrixCellValue> list: values) {
            for(MatrixCellValue value: list){
                sum += value.value * value.value;
            }
        }
        return Math.sqrt(sum);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        int left = 0, right = values.size() - 1;
        while (left < right)
        {
            int mid = (right + left)/ 2;
            if (values.get(mid).get(0).row < row)
                left = mid + 1;
            else
                right = mid;
        }
        int index = left;
        if(values.get(left).get(0).row == row){
            left = 0;
            right = values.get(index).size() - 1;
            while (left < right)
            {
                int mid = (right + left)/ 2;
                if (values.get(index).get(mid).column < column)
                    left = mid + 1;
                else
                    right = mid;
            }
            if(values.get(index).get(left).column == column){
                return values.get(index).get(left).value;
            }
        }
        return 0;
    }

    private static String zeroToString(int i){
        if(i == 0){
            return "";
        }
        StringBuilder result = new StringBuilder();
        if(i > 2){
            result.append("0 ... 0");
        }
        else {
            result.append("0 ".repeat(i - 1)).append("0");
        }
        return result.toString();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        int listsIndex = 0;
        int previousColumn = -1;
        for(int i = 0; i < shape.rows; i++){
            if(listsIndex >= values.size() ||  values.get(listsIndex).get(0).row > i){
                result.append(zeroToString(shape.columns)).append('\n');
            }
            else if(values.get(listsIndex).get(0).row == i){
                for(MatrixCellValue cell: values.get(listsIndex)){
                    // Filling spaces between values with 0.
                    result.append(zeroToString(Math.max(0,cell.column - previousColumn - 1)));
                    result.append(" ".repeat(cell.column - previousColumn > 1 ? 1: 0)).append(cell.value);
                    if(cell.column < shape.columns){
                        result.append(" ");
                    }
                    previousColumn = cell.column;
                }
                result.append(zeroToString(shape.columns - previousColumn - 1)).append('\n');
                previousColumn = -1;
                listsIndex++;
            }
            else {
                listsIndex++;
                if(listsIndex >= values.size()){
                    while(i < shape.rows){
                        // There are no more values.
                        result.append(zeroToString(shape.columns)).append('\n');
                        i++;
                    }
                }
                else if(values.get(listsIndex).get(0).row == i) {
                    // We want to correctly add this line in next iteration.
                    i--;
                }
            }
        }

        return result.toString();
    }
}
