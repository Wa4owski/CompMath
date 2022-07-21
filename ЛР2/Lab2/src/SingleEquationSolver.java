import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SingleEquationSolver {

    public static void main(String[] args)  {




        Equation cubeEq = new Equation(x->x*x*x-1.89*x*x-2*x+1.76);
        cubeEq.setDf(x->3*x*x-2*1.89*x-2);
        cubeEq.setDdf(x->6*x-2*1.89);
        Equation sinEq = new Equation(x->Math.sin(x*x+4*x));
        sinEq.setDf(x->Math.cos(x*x+4*x)*(2*x+4));
        sinEq.setDdf(x->2*Math.cos(x*x-4*x) - 4*(x+2)*(x+2)*Math.sin(x*x-4*x));

        Equation lnEq = new Equation(x->Math.log(x*x+5)-2);
        lnEq.setDf(x->2*x/(x*x+5));
        lnEq.setDdf(x->-2*(x*x-5)/((x*x+5)*(x*x+5)));

        Map<String, Equation> equationsMap= new HashMap<String, Equation>();
        equationsMap.put("x^3-1.89x^2-2x+1.76", cubeEq);
        equationsMap.put("sin(x^2+4x)", sinEq);
        equationsMap.put("ln(x^2+5)-2", lnEq);
        List<String> equationsList = new ArrayList<String>(equationsMap.keySet());




        int equationID;
        Equation equation;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Это программа для решения нелинейных уравнений.");
        while (true){
            System.out.println("Выберите, какое уравнение вы хотите решить:");
            for(int i = 0; i < equationsList.size(); i++){
                System.out.println(i+1 + ")\t" + equationsList.get(i));
            }
            try{
                equationID = Integer.parseInt(scanner.next());
                if (!(0 < equationID &&  equationID <= equationsList.size())){
                    throw new IllegalArgumentException();
                }
            } catch (Exception e){
                System.out.println("Нет уравнения под таким номером.");
                continue;
            }

            equation = equationsMap.get(equationsList.get(equationID-1));
            break;
        }

        int solveWay = Solver.binChoose(scanner, "Каким способом хотите решить уравнение?",
                "Методом хорд", "Методом простой итерации");

        int readWay = Solver.binChoose(scanner, "Каким способом ввести входные данные?",
                "С клавиатуры", "Из файла");

            double a = Double.NaN, b = Double.NaN, eps;
            if(readWay == 2) {
                scanner.close();
                try {
                    scanner = new Scanner(Paths.get(Solver.inputPath));
                } catch (IOException e) {
                    System.out.println("Файл с входными данными не найден.");
                    return;
                }
            }
                while (true) {
                    try {
                        if(readWay == 1) {
                            System.out.println("Введите границы интервала и погрешность:");
                            System.out.println("Введите a:");
                        }
                        a = Double.parseDouble(scanner.next());
                        if(readWay == 1)
                            System.out.println("Введите b:");
                        b = Double.parseDouble(scanner.next());
                        if(readWay == 1)
                            System.out.println("Введите eps:");
                        eps = Double.parseDouble(scanner.next());

                        if(a > b) {
                            double temp = a;
                            a = b;
                            b = temp;
                        }
                    } catch (Exception e) {
                        System.out.println("Непредвиденный ввод");
                        if(readWay == 2){
                            System.out.println("Измените содержимое файла входных данных.");
                            return;
                        }
                        continue;
                    }
                    System.out.println("a = " + a);
                    System.out.println("b = " + b);
                    System.out.println("eps = " + eps);
                    if(solveWay == 1) {
                        ChordMethod chordMethod = new ChordMethod(equation.getF(), equation.getDf(), equation.getDdf(), a, b, eps);
                        int intervalQ = chordMethod.checkInterval();
                        if(intervalQ == -1){
                            System.out.println("Выберите другой интервал.");
                            if(readWay == 2){
                                System.out.println("Измените содержимое файла входных данных.");
                                return;
                            }
                            continue;
                        }
                        if(intervalQ == 0){
                            System.out.println("Рекомендуется выбрать другой интервал.");
                            if(readWay == 1) {
                                int changeInterval;
                                while (true) {
                                    System.out.println("Сменить интервал - нажмите 1");
                                    System.out.println("Продолжить решение уравнения - нажмите 2");
                                    try{
                                        changeInterval = Integer.parseInt(scanner.next());
                                        if (changeInterval != 1 && changeInterval != 2) {
                                            throw new IllegalArgumentException();
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Непредвиденный ввод");
                                        continue;
                                    }
                                    break;
                                }
                                if (changeInterval == 1) {
                                    continue;
                                }
                            }
                        }
                        chordMethod.solution() ;

                        if (readWay == 1)
                            System.out.println(chordMethod.getResult());
                        else {
                            try (FileWriter fileWriter = new FileWriter(Solver.outputPath)) {
                                fileWriter.write(chordMethod.getResult());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                    if(solveWay == 2){
                        SimpleIterationMethod simpleIterationMethod = new SimpleIterationMethod(equation.getF(), equation.getDf(), a, b, eps);
                        if(Double.isNaN(simpleIterationMethod.solution())){
                            System.out.println("Выберите другой интервал.");
                            if(readWay == 2){
                                System.out.println("Измените содержимое файла входных данных.");
                                return;
                            }
                            continue;
                        }
                        else{
                            if(readWay == 1)
                                System.out.println(simpleIterationMethod.getResult());
                            else{
                                try (FileWriter fileWriter = new FileWriter(Solver.outputPath)){
                                    fileWriter.write(simpleIterationMethod.getResult());
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    break;
                }

                try {
                if(Double.isNaN(a) || Double.isNaN(b))
                    throw new IllegalArgumentException();
                String request = Graph.buildRequestToWolfram(equationsList.get(equationID-1), a - 1, b + 1);
                //System.out.println(request);
              //  Graph.openWolframAndDownloadGraphImage(new URL(request));
                Graph.openWolframAndDownloadGraphImage(new URL(request));
            } catch (Exception e) {
                e.printStackTrace();
            }



    }
}
