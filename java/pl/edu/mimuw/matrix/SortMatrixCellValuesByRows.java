package pl.edu.mimuw.matrix;

import java.util.*;

public class SortMatrixCellValuesByRows implements Comparator<MatrixCellValue>{
    public int compare(MatrixCellValue a, MatrixCellValue b){
        if(a.row > b.row){
            return 1;
        }
        else if(a.row < b.row){
            return -1;
        }
        else {
            return Integer.compare(a.column, b.column);
        }
    }
}
