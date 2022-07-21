interface Function{
    Double calcFunction(Double x);
}
public class Equation {
    private Function f;
    private Function df;
    private Function ddf;
    private Function phi;
    private Function dphi;

    public Function getF() {
        return f;
    }

    public Function getDf() {
        return df;
    }

    public void setDf(Function df) {
        this.df = df;
    }


    public Function getPhi() {
        return phi;
    }

    public void setPhi(Function phi) {
        this.phi = phi;
    }

    public Function getDphi() {
        return dphi;
    }

    public void setDphi(Function dphi) {
        this.dphi = dphi;
    }

    public Function getDdf() {
        return ddf;
    }

    public void setDdf(Function ddf) {
        this.ddf = ddf;
    }

    public Equation(Function f){
        this.f = f;
    }
}
