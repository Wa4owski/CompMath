import javafx.util.Pair;
import java.util.List;

public class Interpolation {
    List<Double> X, Y;
    double x;

    public Interpolation(double x, List<Double> X, List<Double> Y) {
        this.X = X;
        this.Y = Y;
        this.x = x;
    }

    public Pair<Double, Double> lagrange(){
        double y = 0;
        for(int i = 0; i < X.size(); i++){
            double c = Y.get(i);
            double l = 1;
            for(int j = 0; j < X.size(); j++){
                if(i == j)
                    continue;
                c /= (X.get(i)-X.get(j));
                l *= (x-X.get(j));
            }
            y += c*l;
        }
        return new Pair<>(x, y);
    }


    private double calcFk(int k, int i) {
        if (k == 0) {
            return Y.get(i);
        }
        return (calcFk(k - 1, i + 1) - calcFk(k - 1, i))
                / (X.get(i + k) - X.get(i));
    }


    public Pair<Double, Double> newton(){
        double y = 0;
        double c = 1;
        for (int i = 0; i < X.size(); i++) {
            y += calcFk(i, 0) * c;
            c *= (x - X.get(i));
        }
        return new Pair<>(x, y);
    }

}
