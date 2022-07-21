package main.java;

import main.java.interfaces.BinParameterFunction;
import main.java.interfaces.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiffusionEquation {
    private String equationStr;
    private BinParameterFunction equationFunction;


    private double c1;
    private BinParameterFunction C1Funtion;
    private Function exactFunction;

    public DiffusionEquation(String equationStr, BinParameterFunction equationFunction) {
        this.equationStr = equationStr;
        this.equationFunction = equationFunction;
    }

    public String getEquationStr() {
        return equationStr;
    }

    public void setEquationStr(String equationStr) {
        this.equationStr = equationStr;
    }

    public BinParameterFunction getEquationFunction() {
        return equationFunction;
    }

    public void setEquationFunction(BinParameterFunction equationFunction) {
        this.equationFunction = equationFunction;
    }

    public Function getExactFunction() {
        return exactFunction;
    }

    public void setExactFunction(Function exactFunction) {
        this.exactFunction = exactFunction;
    }

    public void setCFunction(BinParameterFunction C1Funtion){
        this.C1Funtion = C1Funtion;
    }

    public void calcC1(double x0, double y0){
        setC1(C1Funtion.calcFunction(x0, y0));
    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public BinParameterFunction getC1Funtion() {
        return C1Funtion;
    }

    public void setC1Funtion(BinParameterFunction c1Funtion) {
        C1Funtion = c1Funtion;
    }

    public static List<DiffusionEquation> equationList = new ArrayList<>();

    public static void printEquationList(){
        int i = 0;
        for(DiffusionEquation eq : DiffusionEquation.equationList){
            System.out.println(i+1 + ")\t" + eq.toString());
            i++;
        }
    }

    public static DiffusionEquation choseEquation(Scanner scanner){
        int equationID;
        while (true){
            System.out.println("Выберите функцию:");
            printEquationList();
            try{
                equationID = Integer.parseInt(scanner.next());
                if (!(0 < equationID &&  equationID <= DiffusionEquation.equationList.size())){
                    throw new IllegalArgumentException();
                }
            } catch (Exception e){
                System.out.println("Нет функции под таким номером.");
                continue;
            }

            return DiffusionEquation.equationList.get(equationID-1);
        }
    }

    @Override
    public String toString() {
        return "y' = " + equationStr;
    }
}

