import java.util.ArrayList;

public class SolveMethod {

    Function function;
    double a, b, eps;

    int n;
    double h;
    final double k = 1d/3d;
    ArrayList<Double> X;

    public SolveMethod(Function function, double a, double b, double eps) {
        this.function = function;
        this.a = a;
        this.b = b;
        this.eps = eps;

        n = 4;
        updateH();
        X = new ArrayList<>();
        updateX();
    }

    public void updateH(){
        h = (b-a)/n;
    }

    public void updateX(){
        X.clear();
        for(int i = 0; i < n; i++){
            X.add(a+i*h);
        }
    }
}
