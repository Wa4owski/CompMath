import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Function function = x -> 5 * x / (x * x * x * x + 1);
        double l = -2, r = 0, h = 0.2;
        List<Double> X = new ArrayList<>();
        List<Double> Y = new ArrayList<>();
//        for (double i = l; i <= r+0.001; i += h) {
//            X.add(i);
//            System.out.printf("%.3f\n", function.calcFunction(i));
//            Y.add(function.calcFunction(i));
//        }
        Scanner scanner;
        try {
            scanner = new Scanner(Paths.get(Solver.inputPath));
        } catch (IOException e) {
            System.out.println("Файл с входными данными не найден.");
            return;
        }
        while (scanner.hasNext()){
            Pair<Double, Double> XY = Solver.setXY(scanner);
            l = Math.min(l, XY.getKey());
            r = Math.max(r, XY.getKey());
            X.add(XY.getKey());
            Y.add(XY.getValue());
        }
        List<Approximation> appList = new ArrayList<>();
        Graph graph = new Graph();
        for(FunctionType type : FunctionType.values()){
//            if(type.equals(FunctionType.Exp))
//                break;
            System.out.println(type.toString());
            Approximation app = new Approximation(type, X, Y);
            app.formLinearSystem();
            app.solveLinearSystem();
            app.setAppFunc();
            System.out.println(app.wolfRequest);
            try {
                String request = Graph.buildRequestToWolfram(app.wolfRequest, l, r);
                //System.out.println(request);
                graph.openWolframAndDownloadGraphImage(new URL(request), type.toString());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            app.setStandartDeviation();
            System.out.println("Standard deviation: " + app.sd + "\n");
//            for(int i = 0; i < Y.size(); i++){
//                System.out.printf("%.3f\n",  app.appFunction.calcFunction(X.get(i)));
//            }//Math.abs(app.appFunction.calcFunction(X.get(i))-Y.get(i))
            //System.out.println();
            appList.add(app);
        }
        System.out.println("Functions checked: " + appList.size());
        System.out.println("Best approximation is: " + Approximation.bestApp.type);

    }
}
