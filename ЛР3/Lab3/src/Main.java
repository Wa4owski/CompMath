import javafx.util.Pair;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Function cube = x->-2*x*x*x-4*x*x+8*x-4;
        Function sin = x->Math.sin(x*x+4*x);
        Function ln = x->Math.log(x*x+5)-2;

        Double ans =  2d*(41d/840* cube.calcFunction(-3d)+ 9d/35* cube.calcFunction(-8d / 3)+9d/280* cube.calcFunction(-7d / 3)+34d/105* cube.calcFunction(-2d)
                +9d/280* cube.calcFunction(-5d / 3)+9d/35* cube.calcFunction(-4d / 3)+41d/840* cube.calcFunction(-1d));
        Double rec = 1d/3 *( cube.calcFunction(-3 + 1/6d) +cube.calcFunction(-3 + 3/6d) +cube.calcFunction(-3 + 5/6d) +
                cube.calcFunction(-3 + 7/6d) +cube.calcFunction(-3 + 9/6d) + cube.calcFunction(-3 + 11/6d));
        Double simp = 1d/9*( cube.calcFunction(-3d)+ 4d*cube.calcFunction(-8d / 3)+ 2d*cube.calcFunction(-7d / 3)+4d*cube.calcFunction(-2d)
                +2d* cube.calcFunction(-5d / 3)+4d*cube.calcFunction(-4d / 3)+cube.calcFunction(-1d));
//        System.out.printf("%.10f\n", ans);
//        System.out.printf("%.10f mis = %.10f perc = %.10f\n", rec, Math.abs(rec-ans), Math.abs(rec-ans)/ans * 100d);
//        System.out.printf("%.10f mis = %.10f perc = %.10f\n", simp, Math.abs(simp-ans), Math.abs(simp-ans)/ans * 100d);


        Map<String, Function> functionsMap= new HashMap<String, Function>();
        functionsMap.put("-2x^3-4x^2+8x-4", cube);
        functionsMap.put("sin(x^2+4x)", sin);
        functionsMap.put("ln(x^2+5)-2", ln);
        List<String> functionsList = new ArrayList<String>(functionsMap.keySet());

        int functionID;
        Function function;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Это программа для вычисления определенных интегралов численным методом.");
        while (true){
            System.out.println("Выберите подинтегральную функцию:");
            for(int i = 0; i < functionsList.size(); i++){
                System.out.println(i+1 + ")\t" + functionsList.get(i));
            }
            try{
                functionID = Integer.parseInt(scanner.next());
                if (!(0 < functionID &&  functionID <= functionsList.size())){
                    throw new IllegalArgumentException();
                }
            } catch (Exception e){
                System.out.println("Нет подинтегральной функции под таким номером.");
                continue;
            }

            function = functionsMap.get(functionsList.get(functionID-1));
            break;
        }

        Pair<Double, Double> interval = Solver.setInterval(scanner, "Введите границы интервала интегрирования a, b:");
        double a = interval.getKey(), b = interval.getValue();

        double eps = Solver.setDouble(scanner, "Введите погрешность eps:");


        int solveWay = Solver.binChoose(scanner, "Каким способом хотите посчитать интеграл?",
                "Методом прямоугольников", "Методом трапеций");

        if(solveWay == 1){
            RectangleMethod rectangleMethod = new RectangleMethod(function, a, b, eps);
            RectangleMethodType type;
            while (true){
                System.out.println("Выберите, по какой формуле вычислять интеграл:");
                System.out.println("LEFT - левых прямоугольников");
                System.out.println("MID - средних прямоугольников");
                System.out.println("RIGHT - правых прямоугольников");
                try {
                    String choose = scanner.next();
                    type = RectangleMethodType.valueOf(choose);
                }
                catch (Exception e){
                    System.out.println("Непредвиденный ввод");
                    continue;
                }
                break;
            }
            rectangleMethod.solution(type);
        }

        if(solveWay == 2){
            TrapezoidMethod trapezoidMethod = new TrapezoidMethod(function, a, b, eps);
            trapezoidMethod.solution();
        }

    }
}
