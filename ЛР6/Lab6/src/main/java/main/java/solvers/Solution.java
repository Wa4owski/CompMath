package main.java.solvers;

import java.util.Formatter;

public class Solution{
    int i;
    double x_i, y_i, f, yExact, epsExact, epsRunge;

    public Solution(int i, double x_i, double y_i, double f, double yExact, double epsExact) {
        this.i = i;
        this.x_i = x_i;
        this.y_i = y_i;
        this.f = f;
        this.yExact = yExact;
        this.epsExact = epsExact;
    }

    @Override
    public java.lang.String toString() {
        return new Formatter().format("%d\t%.3f\t%.3f\t\t%.3f\t %.3f\t%.3f", i, x_i, y_i, f, yExact, epsExact).toString();
    }
}