package com.hanspistor.jpmorgan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Main {
    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Node {
        Node prevNode;
        Point point;
        int distance;

        public Node(Point point, int distance, Node prevNode) {
            this.point = point;
            this.distance = distance;
            this.prevNode = prevNode;
        }
    }

    static boolean isValid(int x, int y, int rows, int cols) {
        return (x >= 0) && (x < rows) && (y >= 0) && (y < cols);
    }

    static int[] rowNeighbors = {-1, 0, 0, 1};
    static int[] colNeighbors = {0, -1, 1, 0};

    static int BFS(ArrayList<ArrayList<Character>> matrix, int maxRows, int maxCols, Point src, Point dst) {
        boolean[][] visited = new boolean[maxRows][maxCols];
        visited[src.x][src.y] = true;

        Queue<Node> queue = new LinkedList<>();

        Node sourceNode = new Node(src, 0, null);
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.peek();
            Point currentPoint = currentNode.point;

            if (currentPoint.x == dst.x && currentPoint.y == dst.y) {
                printExitGraph(matrix, currentNode);
                return currentNode.distance;
            }

            queue.remove();

            for (int i = 0; i < rowNeighbors.length; i++) {
                int row = currentPoint.x + rowNeighbors[i];
                int col = currentPoint.y + colNeighbors[i];

                if (isValid(row, col, maxRows, maxCols)
                        && (matrix.get(row).get(col) == '.'
                        || matrix.get(row).get(col) == 'E')
                        && !visited[row][col]) {
                    visited[row][col] = true;
                    Node adjacent = new Node(new Point(row, col), currentNode.distance + 1, currentNode);
                    queue.add(adjacent);
                }
            }
        }

        return -1;
    }

    public static void printExitGraph(ArrayList<ArrayList<Character>> matrix, Node exitNode) {
        Node currentNode = exitNode;
        while (currentNode.prevNode != null && currentNode.prevNode.prevNode != null) {
            currentNode = currentNode.prevNode;
            int row = currentNode.point.x;
            int col = currentNode.point.y;
            matrix.get(row).set(col, '*');
        }
        for (ArrayList<Character> row : matrix) {
            String rowStr = "";
            for (Character c : row) {
                rowStr = rowStr + c;
            }
            System.out.println(rowStr);
        }
    }

    public static void printMatrix(ArrayList<ArrayList<Character>> matrix, int playerX, int playerY) {
        int currentRow = 0;
        for (ArrayList<Character> row : matrix) {
            String rowStr = "";
            for (Character c : row) {
                rowStr = rowStr + c;
            }
            if (playerX == currentRow) {
                rowStr = rowStr.substring(0, playerY) + "P" + rowStr.substring(playerY + 1);
            }
            System.out.println(rowStr);
            currentRow += 1;
        }
    }


    public static void main(String[] args) throws IOException {
//        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
//        BufferedReader in = new BufferedReader(reader);
        BufferedReader in = new BufferedReader(new FileReader("resources/jpmorgan/prison-1.txt"));
        ArrayList<ArrayList<Character>> matrix = new ArrayList();
        String line;
        Point start = null;
        Point exit = null;
        int currentRow = 0;
        while ((line = in.readLine()) != null) {
            ArrayList<Character> row = line
                    .chars()
                    .mapToObj((c) -> (char) c)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (row.contains('E')) {
                exit = new Point(currentRow, row.indexOf('E'));
            }

            if (row.contains('S')) {
                start = new Point(currentRow, row.indexOf('S'));
            }

            matrix.add(row);
            currentRow += 1;
        }

        int maxRows = currentRow;
        int maxCols = matrix.get(0).size();
        if (BFS(matrix, maxRows, maxCols, start, exit) == -1) {
            System.out.println("Trapped");
        }
    }

}
