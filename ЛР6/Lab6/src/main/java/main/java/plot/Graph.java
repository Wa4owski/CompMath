package main.java.plot;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonConfig;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.List;


public class Graph{

    private final Plot plt;


    public Graph() {
        plt = Plot.create(PythonConfig.pythonBinPathConfig("C:\\Users\\user\\Desktop\\Учеба\\ТеорВер\\Практическая№5\\venv\\Scripts\\python.exe"));
    }

    public void drawPlot(List<Double> X, List<Double> Y) throws PythonExecutionException, IOException {
        plt.plot().add(X, Y);
    }

    public void show() throws PythonExecutionException, IOException{
        plt.show();
    }

}
