package main.java.solvers;

import main.java.DiffusionEquation;

import java.util.ArrayList;

public class ImprovedEulerMethod extends SolveMethod{

    public ImprovedEulerMethod(DiffusionEquation equation, double a, double b, double h) {
        super(equation, a, b, h);
    }

    private double eulerFormul(double x_prev, double y_prev, double h){
        return y_prev+h*equation.getEquationFunction()
                .calcFunction(x_prev, y_prev);
    }

    public ArrayList<Solution> calc(boolean hHalf){
        double h;
        ArrayList<Solution> solution;
        if(hHalf){
            h = this.h/2;
            solution = hHalfSolutions;
        }
        else{
            h = this.h;
            solution = hSolution;
        }

        double yExact = equation.getExactFunction().calcFuntion(a);
        double yMethod = yExact;
        double eqFunction = equation.getEquationFunction().calcFunction(a, yMethod);
        solution.add(new Solution(0, a, yMethod, eqFunction, yExact, Math.abs(yExact - yMethod)));
        for(int i = 1; i <= (b-a)/h; i++){
            double x = a+(double)i*h;
            yExact = equation.getExactFunction().calcFuntion(x);
            yMethod = yMethod+h/2* (eqFunction +
                    equation.getEquationFunction().calcFunction(x, eulerFormul(x-h, yMethod, h)));
            eqFunction = equation.getEquationFunction().calcFunction(x, yMethod);
            solution.add(new Solution(i, x, yMethod, eqFunction, yExact, Math.abs(yExact - yMethod)));
        }
        return solution;
    }

}
