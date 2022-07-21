import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.math3.linear.*;

public class Approximation {
    FunctionType type;
    public double sd = 1000;
    public static Approximation bestApp;
    int power;
    Function appFunction;
    public List<Double> X, Y;
    private double coefficients[][], constants[], solution[];
    String wolfRequest;
    public Approximation(FunctionType type, List<Double> X, List<Double> Y){
        this.type = type;

        switch (type){
            case Square:
                this.power = 2;
                break;
            case Cube:
                this.power = 3;
                break;
            default:
                this.power = 1;
        }

        this.X = new ArrayList<>(X);
        this.Y = new ArrayList<>(Y);

        this.coefficients = new double[power+1][power+1];
        this.constants = new double[power+1];
    }



    public void formLinearSystem(){
        List<Double> Xt = new ArrayList<>(X);
        List<Double> Yt = new ArrayList<>(Y);

                switch (type){
            case Exp:
                Yt = Yt.stream().mapToDouble(Math::log).boxed().collect(Collectors.toList());
                break;
            case Log:
                Xt = Xt.stream().mapToDouble(Math::log).boxed().collect(Collectors.toList());
                break;
            case Power:
                Xt = Xt.stream().mapToDouble(Math::log).boxed().collect(Collectors.toList());
                Yt = Yt.stream().mapToDouble(Math::log).boxed().collect(Collectors.toList());
        }
//        for(Double x : Xt){
//            System.out.println(x);
//        }
//        for(Double y : Yt){
//            System.out.println(y);
//        }
        for(int i = 0; i <= power; i++){
            int p = power*2-i;
            for(int j = 0; j <= power; j++){
                int finalP = p - j;
                coefficients[i][j] = Xt.stream().mapToDouble(x->Math.pow(x, finalP)).sum();
             //   System.out.printf("%.3f ", coefficients[i][j]);
            }
            for(int k = 0; k < Xt.size(); k++){
                constants[i] += Math.pow(Xt.get(k), power-i)*Yt.get(k);
            }
           // System.out.printf("\t %.3f\n", constants[i]);
        }
      //  System.out.println();
    }

    public void solveLinearSystem() {
        DecompositionSolver solver = new LUDecomposition(new Array2DRowRealMatrix(coefficients)).getSolver();
        solution = solver.solve(new ArrayRealVector(constants)).toArray();
    }

    public void setAppFunc(){
        switch (type){
            case Exp:
                appFunction = x -> Math.exp(solution[1])*Math.exp(solution[0]*x);
                System.out.printf("%.5f %.5f\n", Math.exp(solution[1]), solution[0]);
                wolfRequest = String.format("%.5f", Math.exp(solution[1])) + "e^(+" + String.format("%.5f", solution[0]) + "x)";
                break;
            case Log:
                appFunction = x -> solution[0]*Math.log(x)+solution[1];
                System.out.printf("%.5f %.5f\n", solution[0], solution[1]);
                wolfRequest = String.format("%.5f", solution[0]) + "ln(x)+" + String.format("%.5f", solution[1]);
                break;
            case Power:
                appFunction = x -> Math.exp(solution[0])*Math.pow(x, solution[1]);
                System.out.printf("%.5f %.5f\n", solution[0], solution[1]);
                wolfRequest = String.format("%.5f", Math.exp(solution[0])) + "x^(" + String.format("%.5f", solution[1]) + ")";
                break;
            case Linear:
                appFunction = x -> solution[0]*x+solution[1];
                System.out.printf("%.5f %.5f\n", solution[0], solution[1]);
                wolfRequest = String.format("%.5f", solution[0]) + "x+" + String.format("%.5f", solution[1]);
                break;
            case Square:
                appFunction = x -> solution[0]*x*x+solution[1]*x+solution[2];
                System.out.printf("%.5f %.5f %.5f\n", solution[0], solution[1], solution[2]);
                wolfRequest = String.format("%.5f", solution[0])+"x^2+" + String.format("%.5f", solution[1])+"x+"+String.format("%.5f", solution[2]);
                break;
            case Cube:
                appFunction = x -> solution[0]*x*x*x+solution[1]*x*x+solution[2]*x+solution[3];
                System.out.printf("%.5f %.5f %.5f %.5f\n", solution[0], solution[1], solution[2], solution[3]);
                wolfRequest = String.format("%.5f", solution[0]) +"x^3+" + String.format("%.5f", solution[1]) +"x^2+" + String.format("%.5f", solution[2]) +"x+" +String.format("%.5f", solution[3]);
        }
    }
    public void setStandartDeviation(){
        double s2 = 0;
        for(int i = 0; i < X.size(); i++){
            s2 += Math.pow(appFunction.calcFunction(X.get(i))-Y.get(i), 2);
        }
        sd = Math.sqrt(s2/X.size());
        if(bestApp == null ||  sd < bestApp.sd){
            bestApp = this;
        }
    }

}
