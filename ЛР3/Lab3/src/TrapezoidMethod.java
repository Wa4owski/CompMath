import java.util.ArrayList;

public class TrapezoidMethod extends SolveMethod{
    ArrayList<Double> Y;

    public TrapezoidMethod(Function function, double a, double b, double eps){
        super(function, a, b, eps);
        Y = new ArrayList<>();
        updateY();
    }

    private void updateY(){
        Y.clear();
        X.forEach(x->Y.add(function.calcFunction(x)));
    }

    private double calcI(){
        return h/2 * (2*Y.stream().mapToDouble(x->x).sum() - (Y.get(0) + Y.get(Y.size()-1)));
    }

    public double solution(){
        double prevI = calcI(), curI = Double.NaN;

        while(n < Integer.MAX_VALUE / 10000){
            n *= 2;
            updateH();
            updateX();
            updateY();
            curI = calcI();
            if(k*Math.abs(curI-prevI) < eps)
                break;
            prevI = curI;
        }
        System.out.printf("Определенный интеграл вычислен при разбиении на n = %d равных частей\n", n);
        System.out.println("Вычисленное значение: " + curI);
        return curI;
    }
}
