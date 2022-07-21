public class SimpleIterationForSystem {
    FunctionWithTwoParameters f1, f2, phi1, phi2;
    double prevX1, prevX2;
    double x1, x2;
    double eps;
    public SimpleIterationForSystem(FunctionWithTwoParameters f1, FunctionWithTwoParameters f2,
                                    FunctionWithTwoParameters phi1, FunctionWithTwoParameters phi2,
                                    double eps) {
        this.f1 = f1;
        this.f2 = f2;
        this.phi1 = phi1;
        this.phi2 = phi2;
        this.eps = eps;
    }

    public void setFirstXs(double prevX1, double prevX2){
        this.prevX1 = prevX1;
        this.prevX2 = prevX2;
    }

    String result;

    public String getResult() {
        return result;
    }

    public void solution(){
        int count = 0;
        while(true){
            count++;
            x1 = phi1.calcFunction(prevX1, prevX2);
            x2 = phi2.calcFunction(prevX1, prevX2);
            double delta1 = Math.abs(prevX1-x1), delta2 = Math.abs(prevX2-x2);
            if(Double.min(delta1, delta2) < eps){
                result = String.format("Найдено решение {x1* = %.5f, x2* = %.5f} за %d итераций\n", x1, x2, count) +
                        String.format("Погрешности: {|x1_k+1 - x1_k| = %.5f, |x2_k+1 - x2_k| = %.5f}\n", delta1, delta2);
                return;
            }
            prevX1 = x1;
            prevX2 = x2;
        }
    }
}
