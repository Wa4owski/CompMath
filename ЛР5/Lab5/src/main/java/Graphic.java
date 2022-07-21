import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonConfig;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Graphic {

        private final Plot plt;


        public Graphic() {
            plt = Plot.create(PythonConfig.pythonBinPathConfig("C:\\Users\\user\\Desktop\\Учеба\\ТеорВер\\Практическая№5\\venv\\Scripts\\python.exe"));
        }

        public void drawPlot(List<Double> X, List<Double> Y) throws PythonExecutionException, IOException {
                plt.plot().add(X, Y);
        }

        public void drawPoints( List<Double> X, List<Double> Y) throws PythonExecutionException, IOException {
            double xLeft = X.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            double xRight = X.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
            double yLeft = Y.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            double yRight = Y.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
            plt.xlim(xLeft-1, xRight+1);
            plt.ylim(yLeft-1, yRight+1);

            for(int i = 1; i < X.size(); i++) {
                List<Double> xt = new ArrayList<>();
                xt.add(X.get(i - 1));
                xt.add(X.get(i));
                List<Double> yt = new ArrayList<>();
                yt.add(Y.get(i - 1));
                yt.add(Y.get(i));
                plt.plot().add(xt, yt);
            }
    }

        public void show() throws PythonExecutionException, IOException{
            plt.show();
        }

}
