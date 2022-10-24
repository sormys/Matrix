package pl.edu.mimuw.matrix;

import java.util.*;

public class SortMatrixCellValuesByColumns implements Comparator<MatrixCellValue>{
    public int compare(MatrixCellValue a, MatrixCellValue b){
        if(a.column > b.column){
            return 1;
        }
        else if(a.column < b.column){
            return -1;
        }
        else {
            return Integer.compare(a.row, b.row);
        }
    }
}