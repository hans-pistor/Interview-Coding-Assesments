package com.hanspistor.jpmorgan.refactored;

import java.util.ArrayList;
import java.util.Arrays;

public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Point> getAdjacentPoints() {
        return new ArrayList(Arrays.asList(
                new Point(this.x - 1, this.y),
                new Point(this.x + 1, this.y),
                new Point(this.x, this.y - 1),
                new Point(this.x, this.y + 1)
        ));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;

        Point other = (Point) obj;
        return (this.x == other.x) && (this.y == other.y);
    }
}
