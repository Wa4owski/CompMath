package main.java.solvers;

import main.java.DiffusionEquation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SolveMethod {
    final DiffusionEquation equation;
    final double a;
    double b;
    double h;

    public SolveMethod(DiffusionEquation equation, double a, double b, double h) {
        this.equation = equation;
        this.a = a;
        this.b = b;
        this.h = h;
    }

    ArrayList<Solution> hSolution = new ArrayList<>();
    ArrayList<Solution> hHalfSolutions = new ArrayList<>();

    public ArrayList<Double> getX(){
        //Stream<Double> stream = ;
        return hSolution.stream().mapToDouble(x->x.x_i).boxed().collect(Collectors
                .toCollection(ArrayList::new));//ArrayList<>((Arrays.asList(hSolution.stream().mapToDouble(x -> x.x_i).boxed().toArray())));
    }

    public ArrayList<Double> getY(){
        //Stream<Double> stream = ;
        return hSolution.stream().mapToDouble(x->x.y_i).boxed().collect(Collectors
                .toCollection(ArrayList::new));//ArrayList<>((Arrays.asList(hSolution.stream().mapToDouble(x -> x.x_i).boxed().toArray())));
    }

    public ArrayList<Double> getYExact(){
        //Stream<Double> stream = ;
        return hSolution.stream().mapToDouble(x->x.yExact).boxed().collect(Collectors
                .toCollection(ArrayList::new));//ArrayList<>((Arrays.asList(hSolution.stream().mapToDouble(x -> x.x_i).boxed().toArray())));
    }

    public

    void printTable(){
        System.out.println("i\tx_i\t\ty_i\t\tf(x_i, y_i)\ty_exact\tepsExact\tepsRunge");
        for(int i = 0; i < hSolution.size(); i++){
            final int p = 3;
            double R = Math.abs(hSolution.get(i).y_i-hHalfSolutions.get(2*i).y_i)/(Math.pow(2, p)-1);
            System.out.printf("%s\t\t%.3f\n", hSolution.get(i).toString(), R);
        }
    }

    abstract ArrayList<Solution> calc(boolean hHalf);

    public void solute(){
        calc(false);
        calc(true);
        printTable();
    }
}
