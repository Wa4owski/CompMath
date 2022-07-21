public class ChordMethod {
    Function f, df, ddf;
    double a, b, eps;
    public ChordMethod(Function f, Function df, Function ddf, double a, double b, double eps){
        this.f = f;
        this.df = df;
        this.ddf = ddf;
        this.a = a;
        this.b = b;
        this.eps = eps;
    }
    double calcX(){
        return a - (b-a)/(f.calcFunction(b)-f.calcFunction(a)) * f.calcFunction(a);
    }

    private String result;

    public String getResult() {
        return result;
    }

    public int checkInterval(){
        if (f.calcFunction(a) * f.calcFunction(b) >= 0) {
            System.out.println("На выбранном интервале нет корня, либо корень не единственный.");
            return -1;
        }
        double prevSignDf = Math.signum(df.calcFunction(a));
        double prevSignDdf = Math.signum(ddf.calcFunction(a));
        for(double i = a; i <= b; i+=0.05){
            if(prevSignDf != Math.signum(df.calcFunction(i)) || prevSignDdf != Math.signum(ddf.calcFunction(i))){
                System.out.println("Не выполняется условие константности знака первой или второй производной ф-и.");
               // System.out.println(i);
                return 0;
            }
            prevSignDf = Math.signum(df.calcFunction(i));
            prevSignDdf = Math.signum(ddf.calcFunction(i));
        }
        return 1;
    }

    public double solution() {
        double prevX = calcX();
        if(f.calcFunction(a)*ddf.calcFunction(a) > 0)
            prevX = a;
        if(f.calcFunction(b)*ddf.calcFunction(b) > 0)
            prevX = b;
        int count = 0;
        while (Math.abs(prevX) > eps) {
            count++;
            if (f.calcFunction(a) * f.calcFunction(prevX) < 0)
                b = prevX;
            else
                a = prevX;
            if (Math.abs(calcX() - prevX) <= eps) {
                prevX = calcX();
//                System.out.printf("a = %.3f b = %.3f x = %.3f f(a) = %.3f f(b) = %.3f f(x) = %.3f |a-b| = %.3f\n",
//                        a, b, prevX, f.calcFunction(a) ,f.calcFunction(b), f.calcFunction(prevX), Math.abs(a - b));
                break;
            }
            prevX = calcX();
        }
        result = String.format("Найдено решение x* = %.5f за %d итераций\n", prevX, count) +
                String.format("Значение ф-и в корне f(x*) = %.5f\n", f.calcFunction(prevX));
        return prevX;
    }
}
