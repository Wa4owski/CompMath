import javafx.util.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;


public class SystemOfEquationsSolver {

    public static void main(String[] args) {
        SystemOfTwoEquations system1 = new SystemOfTwoEquations(
                (x1, x2) -> (0.1 * x1 * x1 + x1 + 0.2 * x2 * x2 - 0.3), (x1, x2) -> 0.2 * x1 * x1 + x2 - 0.1 * x1 * x2 - 0.7);
        system1.setPhi1(((x1, x2) -> 0.3 - 0.1 * x1 * x1 - 0.2 * x2 * x2));
        system1.setPhi2(((x1, x2) -> 0.7 - 0.2 * x1 * x1 - 0.1 * x1 * x2));
        system1.setPhi1Dx1((x1, x2) -> -0.2 * x1);
        system1.setPhi1Dx2((x1, x2) -> -0.4 * x2);
        system1.setPhi2Dx1((x1, x2) -> -0.4 * x1 + 0.1 * x2);
        system1.setPhi2Dx2(((x1, x2) -> 0.1 * x1));
        system1.setAbsDphi1();
        system1.setAbsDphi2();
        system1.setPhi1String("x1=0.3-0.1x1^2-0.2x2^2");
        system1.setPhi2String("x2=0.7-0.2x1^2-0.1x1x2");

        Scanner scanner = new Scanner(System.in);
        double a, b;
        while (true) {
            //System.out.println("Введите a и b: a <= x1, x2, <= b, которые зададут область построения графиков:");
            Pair<Double, Double> interval = Solver.setInterval(scanner, "Введите a и b: a <= x1, x2, <= b, которые зададут область построения графиков:");
            a = interval.getKey();
            b = interval.getValue();

            try {
                //Graph funcGraph = new Graph();
                String request = Graph.buildRequestToWolfram(system1.getPhi1String(), system1.getPhi2String(), a, b);
                Graph.openWolframAndDownloadGraphImage(new URL(request));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int repeat = Solver.binChoose(scanner,
                    "Хотитите поменять область построения и повторить вывод графика?",
                    "Да", "Нет");
            if (repeat == 2)
                break;
        }

        int readWay = Solver.binChoose(scanner, "Каким способом ввести входные данные?",
                "С клавиатуры", "Из файла");


        if (readWay == 2) {
            scanner.close();
            try {
                scanner = new Scanner(Paths.get(Solver.inputPath));
            } catch (IOException e) {
                System.out.println("Файл с входными данными не найден.");
                return;
            }
        }

        double a1, b1, a2, b2;
        while (true) {
            //System.out.println("Введите a1 и b1: a1 < x1 < b, которые зададут область изоляции x1:)");
            Pair<Double, Double> interval = Solver.setInterval(scanner, "Введите a1 и b1: a1 < x1 < b, которые зададут область изоляции x1:");
            a1 = interval.getKey();
            b1 = interval.getValue();
            //System.out.println("Введите a2 и b2: a2 < x1 < b, которые зададут область изоляции x2:)");
            interval = Solver.setInterval(scanner, "Введите a2 и b2: a2 < x1 < b, которые зададут область изоляции x2:");
            a2 = interval.getKey();
            b2 = interval.getValue();

            double maxAbsDPhi = 0;
            for (double i = a1; i <= b1; i += 0.05) {
                for (double j = a2; j <= b2; j += 0.05) {
                    maxAbsDPhi = Double.max(maxAbsDPhi, system1.getAbsDphi1().calcFunction(i, j));
                    maxAbsDPhi = Double.max(maxAbsDPhi, system1.getAbsDphi2().calcFunction(i, j));
                }
            }

            if (maxAbsDPhi >= 1) {
                System.out.printf("Не выполняется достаточное условие сходимости. max[|dphi(x)|] = %.3f Поменяйте область G\n", maxAbsDPhi);
                if (readWay == 2) {
                    System.out.println("Измените содержимое файла входных данных.");
                    return;
                }
                int changeFlag = Solver.binChoose(scanner, "Выберите:", "Поменять область G", "Продолжить решение");
                        if(changeFlag == 2)
                            break;
                continue;
            }
            System.out.println(maxAbsDPhi);
            break;
        }

        double eps;
        while (true) {
            try {
                System.out.println("Введите eps:");
                eps = Double.parseDouble(scanner.next());
            } catch (Exception e) {
                System.out.println("Непредвиденный ввод");
                if (readWay == 2) {
                    System.out.println("Измените содержимое файла входных данных.");
                    return;
                }
                continue;
            }
            break;
        }

        SimpleIterationForSystem simpleIterationForSystem = new SimpleIterationForSystem(system1.f1, system1.f2, system1.phi1, system1.phi2, eps);
        simpleIterationForSystem.setFirstXs((a1+b1)/2, (a2+b2)/2);
        simpleIterationForSystem.solution();
        if (readWay == 1)
            System.out.println(simpleIterationForSystem.getResult());
        else {
            try (FileWriter fileWriter = new FileWriter(Solver.outputPath)) {
                fileWriter.write(simpleIterationForSystem.getResult());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
