public class RectangleMethod extends SolveMethod{
    public RectangleMethod(Function function, double a, double b, double eps){
        super(function, a, b, eps);
    }

    private double calcI(RectangleMethodType type){
        switch (type){
            case LEFT:
                double lastFx = function.calcFunction(X.get(X.size()-1));
                return h*(X.stream().mapToDouble(x->function.calcFunction(x)).sum() - lastFx);
            case MID:
                double sum = 0d;
                for(int i = 1; i < X.size(); i++){
                    sum += function.calcFunction((X.get(i) + X.get(i-1))/2);
                }
                return h*sum;
            case RIGHT:
                double firstFx = function.calcFunction(X.get(0));
                return h*(X.stream().mapToDouble(x->function.calcFunction(x)).sum() - firstFx);
            default:
                return Double.NaN;
        }

    }
    public double solution(RectangleMethodType type) {
        double prevI = calcI(type), curI = Double.NaN;
        while(n < Integer.MAX_VALUE / 1000){
            n *= 2;
            updateH();
            updateX();
            curI = calcI(type);
            if(k*Math.abs(curI-prevI) < eps)
                break;
            prevI = curI;
        }
        System.out.printf("Определенный интеграл вычислен при разбиении на n = %d равных частей\n", n);
        System.out.println("Вычисленное значение: " + curI);
        return curI;
    }
}
