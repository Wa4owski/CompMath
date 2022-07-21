package main.java.solvers;

import main.java.DiffusionEquation;

import java.util.ArrayList;

public class AdamsMethod extends SolveMethod{

    public AdamsMethod(DiffusionEquation equation, double a, double b, double h) {
        super(equation, a, b, h);
    }

    public ArrayList<Solution> calc(boolean hHalf){
        double h;
        ArrayList<Solution> solution;
        if(hHalf){
            h = this.h/2;
            hHalfSolutions = new ImprovedEulerMethod(equation, a, b, h).calc(true);
            solution = hHalfSolutions;
        }
        else{
            h = this.h;
            hSolution = new ImprovedEulerMethod(equation, a, b, h).calc(false);
            solution = hSolution;
        }

        for(int i = 4; i <= (b-a)/h+0.001; i++){
            double df = solution.get(i-1).f-solution.get(i-2).f;
            double ddf = solution.get(i-1).f-2*solution.get(i-2).f+solution.get(i-3).f;
            double dddf = solution.get(i-1).f-3*solution.get(i-2).f+3*solution.get(i-3).f+solution.get(i-4).f;

            double y_i = solution.get(i-1).y_i + h*solution.get(i-1).f
                    + h*h/2*df + 5*h*h*h/12*ddf + 3*h*h*h*h/8*dddf;
            double f_i = equation.getEquationFunction().calcFunction(a+(double)i*h, y_i);

            solution.get(i).y_i = y_i;
            solution.get(i).f = f_i;
            solution.get(i).epsExact = Math.abs(y_i-solution.get(i).yExact);
        }
        return solution;
    }


}
