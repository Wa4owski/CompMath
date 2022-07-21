public class SimpleIterationMethod {
    Function f, df ;
    double a, b, eps;
    public SimpleIterationMethod(Function f, Function df, double a, double b, double eps){
        this.f = f;
        this.df = df;
        this.a = a;
        this.b = b;
        this.eps = eps;
    }


    private double calcLambda(){
        return -1d/Double.max(df.calcFunction(a), df.calcFunction(b));
    }

    private String result;

    public double solution(){
        double lambda = calcLambda();
        Function dphi = x->1+lambda*df.calcFunction(x);
        double q = 0d;
        for(double i = a; i <= b; i += 0.05){
            q = Double.max(q, Math.abs(dphi.calcFunction(i)));
        }
        if(q >= 1){
            System.out.printf("q = %.2f Достаточное условие сходимости не выполняется\n", q);
            return Double.NaN;
        }
        Function phi = x->x+lambda*f.calcFunction(x);
        double prevX = (a+b)/2;
        int count = 0;
        while(count < 1000){
            count++;
            double curX = phi.calcFunction(prevX);
            double delta = Math.abs(curX - prevX);
//            System.out.printf("x_k = %.3f f(x_k) = %.3f x_k+1 = %.3f phi(x_k) = %.3f |x_k-x_k+1| = %.3f\n",
//                  //  "%.3f %.3f %.3f %.3f %.3f %.3f %.3f\n",
//                    prevX, f.calcFunction(prevX) ,curX, phi.calcFunction(prevX), Math.abs(prevX - curX));
           // System.out.printf("%.3f\n", delta);
            if (0 < q && q <= 0.5 && delta <= eps ||
                    0.5 < q && q < 1 && delta < (1 - q) / q * eps) {
                result = String.format("Найдено решение x* = %.5f за %d итераций\n", curX, count) +
                        String.format("Значение ф-и в корне f(x*) = %.5f\n", f.calcFunction(curX));
                return curX;
            }
            prevX = curX;
        }
        return Double.NaN;
    }

    public String getResult() {
        return result;
    }
}
