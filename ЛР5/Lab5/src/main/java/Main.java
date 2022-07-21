import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, PythonExecutionException {
        List<Double> X = new ArrayList<>();
        List<Double> Y = new ArrayList<>();
        Function function;


        Function cubeEq = x->x*x*x-1.89*x*x-2*x+1.76;
        Function sinEq = x->Math.sin(x*x+4*x);
        Function lnEq = x->Math.log(x*x+5)-2;

        double x, l = Double.MAX_VALUE, r = Double.MIN_VALUE;


        Map<String, Function> functionsMap= new HashMap<String, Function>();
        functionsMap.put("x^3-1.89x^2-2x+1.76", cubeEq);
        functionsMap.put("sin(x^2+4x)", sinEq);
        functionsMap.put("ln(x^2+5)-2", lnEq);
        List<String> functionsList = new ArrayList<String>(functionsMap.keySet());



        Scanner scanner = new Scanner(System.in);
        if(Solver.binChoose(scanner, "Выборите способ задания функции:",
                "Таблицей из файла", "Выбор функции") == 1) {
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

            scanner = new Scanner(System.in);
            System.out.println("Введите х:");
            x = Double.parseDouble(scanner.nextLine());
        }
        else{
            int functionID;
            while (true){
                System.out.println("Выберите функцию:");
                for(int i = 0; i < functionsList.size(); i++){
                    System.out.println(i+1 + ")\t" + functionsList.get(i));
                }
                try{
                    functionID = Integer.parseInt(scanner.next());
                    if (!(0 < functionID &&  functionID <= functionsList.size())){
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e){
                    System.out.println("Нет функции под таким номером.");
                    continue;
                }

                function = functionsMap.get(functionsList.get(functionID-1));
                break;
            }

           // while(x) {
                System.out.println("Введите х:");
                x = Double.parseDouble(scanner.next());
            //}

            l = (int)x-5;
            r = (int)x+5;
            for(double i = l; i <= r+0.001; i++){
                X.add(i);
                Y.add(function.calcFunction(i));
            }
        }


        Interpolation interpolation = new Interpolation(x, X, Y);
        Pair<Double, Double> res;
        Graphic graphic = new Graphic();

        if(Solver.binChoose(scanner, "Выберите метод: ",
                "Многочлен Лагранжа", "Многочлен Ньютона") == 1) {
            res = interpolation.lagrange();
            System.out.printf("Вычисленное значение в x = %.3f равно: %.3f\n", res.getKey(), res.getValue());
            graphic.drawPoints(X, Y);
            graphic.show();
        }
        else {
            res = interpolation.newton();
            System.out.printf("Вычисленное значение в x = %.3f равно: %.3f\n", res.getKey(), res.getValue());
            graphic.drawPoints(X, Y);
            //graphic.show();
            List<Double> Xn = new ArrayList<>();
            List<Double> Yn = new ArrayList<>();
            final double n = 1000;
                double step = (r - l) / n;
                int iter = 0;
                for (double i = l; i <= r; i += step, iter++) {
                    Xn.add(i);
                    interpolation.x = i;
                    Yn.add(interpolation.newton().getValue());
                }
                graphic.drawPlot( Xn, Yn);
            graphic.show();
            }

    }
}
