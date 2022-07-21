import javafx.util.Pair;
import java.util.Scanner;

public  class Solver {
    final static String inputPath = "C:\\Users\\user\\Desktop\\Учеба\\ВычМат\\ЛР4\\Lab4\\input.txt";

    public static Pair<Double, Double> setXY(Scanner scanner) {
        double a, b;
        while (true) {
            try {
                a = Double.parseDouble(scanner.next());
                b = Double.parseDouble(scanner.next());
            } catch (Exception e) {
                System.out.println("Непредвиденный ввод");
                continue;
            }
            return new Pair<Double, Double> (a, b);
        }
    }
}
