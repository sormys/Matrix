package pl.edu.mimuw;
import pl.edu.mimuw.matrix.*;


public class Main {
    public static void main(String[] args) {
        Shape shape = Shape.matrix(10,10);
        IDoubleMatrix[] matrices = new IDoubleMatrix[10];
        int i = 0;
        String[] names = new String[10];
        names[i] = "SPARSE";
        matrices[i++] = DoubleMatrixFactory.sparse(shape,
                MatrixCellValue.cell(0, 0, 1),
                MatrixCellValue.cell(0, 1, 2),
                MatrixCellValue.cell(1, 0, 3),
                MatrixCellValue.cell(1, 1, 4),
                MatrixCellValue.cell(2, 0, 5),
                MatrixCellValue.cell(2, 1, 6)
        );
        double[][] fullArray= {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}
        };
        names[i] = "VECTOR";
        matrices[i++] = DoubleMatrixFactory.vector(1,2,3,4,5,6,7,8,9,10);
        names[i] = "FULL";
        matrices[i++] = DoubleMatrixFactory.full(fullArray);
        names[i] = "IDENTITY";
        matrices[i++] =  DoubleMatrixFactory.identity(10);
        names[i] = "DIAGONAL";
        matrices[i++] = DoubleMatrixFactory.diagonal(1,2,3,4,5,6,7,8,9,10);
        names[i] = "ANTI DIAGONAL";
        matrices[i++] = DoubleMatrixFactory.antiDiagonal(1,2,3,4,5,6,7,8,9,10);
        names[i] = "ZERO";
        matrices[i++] = DoubleMatrixFactory.zero(shape);
        names[i] = "ROW MATRIX";
        matrices[i++] = DoubleMatrixFactory.rowMatrix(shape, 1,2,3,4,5,6,7,8,9,10);
        names[i] = "COLUMN MATRIX";
        matrices[i++] = DoubleMatrixFactory.columnMatrix(shape, 1,2,3,4,5,6,7,8,9,10);
        names[i] = "CONSTANT";
        matrices[i]= DoubleMatrixFactory.constant(shape, 3);
        i = 0;
        for(IDoubleMatrix matrix: matrices){
            System.out.println("============" + names[i++] + "============");
            System.out.println("\n++++toString()++++");
            System.out.println(matrix);
            System.out.println("\n++++times(this)++++");
            System.out.println(matrix.times(matrix));
            System.out.println("\n++++times(5)++++");
            System.out.println(matrix.times(5));
            System.out.println("\n++++plus(this)++++");
            System.out.println(matrix.plus(matrix));
            System.out.println("\n++++plus(2)++++");
            System.out.println(matrix.plus(2));
            System.out.println("\n++++minus(this)++++");
            System.out.println(matrix.minus(matrix));
            System.out.println("\n++++minus(0)++++");
            System.out.println(matrix.minus(0));
            System.out.println("\n++++get(5,5)++++");
            System.out.println(matrix.get(5,5));
            System.out.println("\n++++normOne()++++");
            System.out.println(matrix.normOne());
            System.out.println("\n++++normInfinity()++++");
            System.out.println(matrix.normInfinity());
            System.out.println("\n++++frobeniusNorm()++++");
            System.out.println(matrix.frobeniusNorm());
            System.out.println("\n++++shape()++++");
            System.out.println("Shape: [" + matrix.shape().rows + " , " +matrix.shape().columns + "]\n");
        }
    }

}
