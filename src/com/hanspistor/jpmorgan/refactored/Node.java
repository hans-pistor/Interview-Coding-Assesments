package com.hanspistor.jpmorgan.refactored;

public class Node {
    Node previousNode;
    Point point;
    int distance;

    public Node(Point point, Node previousNode) {
        this.point = point;
        this.previousNode = previousNode;
    }
}
