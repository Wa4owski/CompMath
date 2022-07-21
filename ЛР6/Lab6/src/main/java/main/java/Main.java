package main.java;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import main.java.plot.Graph;
import main.java.solvers.AdamsMethod;
import main.java.solvers.ImprovedEulerMethod;
import main.java.solvers.SolveMethod;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, PythonExecutionException {

        DiffusionEquation diffusionEquation1 = new DiffusionEquation
                ("y/x*ln(y/x)", (x, y)->(y/x*Math.log(y/x)));
        diffusionEquation1.setC1Funtion((x_0, y_0) -> (Math.log(y_0/x_0)-1)/x_0);
        diffusionEquation1.setExactFunction(x -> x*Math.exp(diffusionEquation1.getC1()*x+1));

        DiffusionEquation diffusionEquation2 = new DiffusionEquation
                ("3*x^2*y + x^2*e^(x^3)", (x, y)-> x*x*(3*y+Math.exp(Math.pow(x, 3))));
        diffusionEquation2.setC1Funtion((x_0, y_0) -> y_0/Math.exp(Math.pow(x_0, 3)) - Math.pow(x_0, 3)/3);
        diffusionEquation2.setExactFunction(x->Math.exp(Math.pow(x, 3))*(diffusionEquation2.getC1()+Math.pow(x, 3)/3));

        DiffusionEquation.equationList.add(diffusionEquation1);
        DiffusionEquation.equationList.add(diffusionEquation2);

        Scanner scanner = new Scanner(System.in);

        DiffusionEquation equation = DiffusionEquation.choseEquation(scanner);

        InputHelper.Interval interval = new InputHelper.Interval(scanner, "Введите интервал дифференцирования [a, b]:");
        double a = interval.getA();
        double b = interval.getB();
        double h = InputHelper.setDouble(scanner, "Введите шаг h:");

        double y_0 = InputHelper.setDouble(scanner, "Введите начальное условие y(a) = :");
        equation.calcC1(a, y_0);


        int solveWay = InputHelper.binChoose(scanner, "Каким способом решить дифф. уравнение?", "усовершенствованный метод Эйлера", "метод Адамса");

        ArrayList<Double> X, Y, YExact;
        SolveMethod solveMethod = (solveWay == 1 ? new ImprovedEulerMethod(equation, a, b, h) : new AdamsMethod(equation, a, b, h) );
        solveMethod.solute();
        X = solveMethod.getX();
        Y = solveMethod.getY();
        YExact = solveMethod.getYExact();

//        if(solveWay == 1){
//            ImprovedEulerMethod eulerMethod = new ImprovedEulerMethod(equation, a, b, h);
//            eulerMethod.solute();
//            X = eulerMethod.getX();
//            Y = eulerMethod.getY();
//            YExact = eulerMethod.getYExact();
//        }
//        //System.out.println("--------------------------------------------------------");
//        else {
//            AdamsMethod adamsMethod = new AdamsMethod(equation, a, b, h);
//            adamsMethod.solute();
//            X = adamsMethod.getX();
//            Y = adamsMethod.getY();
//            YExact = adamsMethod.getYExact();
//
//        }

        Graph graph = new Graph();
        graph.drawPlot(X, Y);
        graph.drawPlot(X, YExact);
        graph.show();
        }
}
