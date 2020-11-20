package com.hanspistor.jpmorgan.refactored;

public class Main {
    public static void main(String[] args) {
        Matrix matrix = Matrix.createMatrixFromFile("resources/jpmorgan/prison-2.txt");

        Matrix solvedMatrix = matrix.findExit();
        if (solvedMatrix == null) {
            System.out.println("Trapped");
        } else {
            System.out.println(solvedMatrix);
        }
    }
}
