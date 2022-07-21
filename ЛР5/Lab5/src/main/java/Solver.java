import javafx.util.Pair;

import java.util.Scanner;

public  class Solver {
    final static String inputPath = "C:\\Users\\user\\Desktop\\Учеба\\ВычМат\\ЛР5\\Lab5\\input.txt";
    final static String outputPath = "C:\\Users\\user\\Desktop\\Учеба\\ВычМат\\ЛР5\\Lab5\\output.txt";

    public static int binChoose(Scanner scanner, String question, String var1, String var2) {
        while (true) {
            int ans;
            System.out.println(question);
            System.out.println(var1 + " - нажмите 1");
            System.out.println(var2 + " - нажмите 2");
            try {
                ans = Integer.parseInt(scanner.next());
                if (ans != 1 && ans != 2) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                System.out.println("Непредвиденный ввод");
                continue;
            }
            return ans;
        }
    }

    public static Pair<Double, Double> setInterval(Scanner scanner, String comment) {
        double a, b;
        while (true) {
            System.out.println(comment);
            try {
                a = Double.parseDouble(scanner.next());
                b = Double.parseDouble(scanner.next());
                if (a > b) {
                    double temp = a;
                    a = b;
                    b = temp;
                }
            } catch (Exception e) {
                System.out.println("Непредвиденный ввод");
                continue;
            }
            return new Pair<Double, Double> (a, b);
        }
    }

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
