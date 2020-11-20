package com.hanspistor.jpmorgan.refactored;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Matrix extends ArrayList<ArrayList<Character>> {
    public static final String MOVEABLE_STRINGS = "SE.";

    Point startPoint;
    Point endPoint;
    int totalRows;
    int totalCols;

    public void setAtPoint(Point point, Character value) {
        this.get(point.x).set(point.y, value);
    }

    public Matrix findExit() {
        boolean[][] visited = new boolean[this.totalRows][this.totalCols];

        visited[this.startPoint.x][this.startPoint.y] = true;

        Queue<Node> queue = new LinkedList<>();

        Node startNode = new Node(this.startPoint, null);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            Point currentPoint = currentNode.point;

            if (currentPoint.equals(this.endPoint)) {
                return this.getSolutionMatrix(currentNode);
            }

            for (Point point : this.getValidAdjacentPoints(currentPoint)) {
                if (!visited[point.x][point.y]) {
                    visited[point.x][point.y] = true;
                    Node adjacent = new Node(point, currentNode);
                    queue.add(adjacent);
                }
            }
        }

        // no solution exists
        return null;
    }

    private ArrayList<Point> getValidAdjacentPoints(Point point) {
        return point
                .getAdjacentPoints()
                .stream()
                .filter(this::isPointWithinBounds)
                .filter(this::canMoveToPoint)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isPointWithinBounds(Point point) {
        return (point.x >= 0) && (point.x < this.totalRows) && (point.y >= 0) && (point.y < this.totalCols);
    }

    private boolean canMoveToPoint(Point point) {
        return MOVEABLE_STRINGS.contains(this.get(point.x).get(point.y).toString());
    }

    private Matrix getSolutionMatrix(Node finalNode) {
        Matrix solution = this.clone();
        Node currentNode = finalNode.previousNode;

        while (currentNode.previousNode != null && currentNode.previousNode.previousNode != null) {
            solution.setAtPoint(currentNode.point, '*');
            currentNode = currentNode.previousNode;
        }
        return solution;
    }

    @Override
    public Matrix clone() {
        Matrix newMatrix = new Matrix();
        newMatrix.startPoint = this.startPoint;
        newMatrix.endPoint = this.endPoint;
        newMatrix.totalRows = this.totalRows;
        newMatrix.totalCols = this.totalCols;

        for (ArrayList<Character> row : this) {
            newMatrix.add(new ArrayList<>(row));
        }

        return newMatrix;
    }


    @Override
    public String toString() {
        StringBuilder graphStr = new StringBuilder();
        for (ArrayList<Character> row : this) {

            StringBuilder rowStr = new StringBuilder();
            for (Character c : row) {
                rowStr.append(c);
            }

            graphStr.append(rowStr).append("\n");
        }
        return graphStr.toString();
    }

    public static Matrix createMatrixFromFile(String filename) {
        BufferedReader in = null;
        File file = null;
        try {
            file = new File(filename);
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("The file at " + file.getAbsolutePath() + " does not exist. Aborting.");
            System.exit(-1);
        }

        String line;
        int currentRow = 0;

        Matrix matrix = new Matrix();

        while ((line = Util.getLineFromBufferedReader(in)) != null) {
            ArrayList<Character> row = line
                    .chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (row.contains('S')) {
                matrix.startPoint = new Point(currentRow, row.indexOf('S'));
            }
            if (row.contains('E')) {
                matrix.endPoint = new Point(currentRow, row.indexOf('E'));
            }
            matrix.add(row);
            currentRow += 1;
        }

        matrix.totalRows = currentRow;
        matrix.totalCols = matrix.get(0).size();

        return matrix;
    }
}
