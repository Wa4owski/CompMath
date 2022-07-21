interface FunctionWithTwoParameters{
    Double calcFunction(Double x1, Double x2);
}
public class SystemOfTwoEquations{
    FunctionWithTwoParameters f1, f2;
    FunctionWithTwoParameters phi1, phi2;
    FunctionWithTwoParameters phi1Dx1, phi1Dx2, phi2Dx1, phi2Dx2;
    FunctionWithTwoParameters absDphi1, absDphi2;
    String phi1String,phi2String;

    public SystemOfTwoEquations(FunctionWithTwoParameters f1, FunctionWithTwoParameters f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public FunctionWithTwoParameters getF1() {
        return f1;
    }

    public void setF1(FunctionWithTwoParameters f1) {
        this.f1 = f1;
    }

    public FunctionWithTwoParameters getF2() {
        return f2;
    }

    public void setF2(FunctionWithTwoParameters f2) {
        this.f2 = f2;
    }

    public FunctionWithTwoParameters getPhi1() {
        return phi1;
    }

    public void setPhi1(FunctionWithTwoParameters phi1) {
        this.phi1 = phi1;
    }

    public FunctionWithTwoParameters getPhi2() {
        return phi2;
    }

    public void setPhi2(FunctionWithTwoParameters phi2) {
        this.phi2 = phi2;
    }

    public FunctionWithTwoParameters getPhi1Dx1() {
        return phi1Dx1;
    }

    public void setPhi1Dx1(FunctionWithTwoParameters phi1Dx1) {
        this.phi1Dx1 = phi1Dx1;
    }

    public FunctionWithTwoParameters getPhi1Dx2() {
        return phi1Dx2;
    }

    public void setPhi1Dx2(FunctionWithTwoParameters phi1Dx2) {
        this.phi1Dx2 = phi1Dx2;
    }

    public FunctionWithTwoParameters getPhi2Dx1() {
        return phi2Dx1;
    }

    public void setPhi2Dx1(FunctionWithTwoParameters phi2Dx1) {
        this.phi2Dx1 = phi2Dx1;
    }

    public FunctionWithTwoParameters getPhi2Dx2() {
        return phi2Dx2;
    }

    public void setPhi2Dx2(FunctionWithTwoParameters phi2Dx2) {
        this.phi2Dx2 = phi2Dx2;
    }

    public FunctionWithTwoParameters getAbsDphi1() {
        return absDphi1;
    }

    public void setAbsDphi1() {
        this.absDphi1 = (x1, x2) -> Math.abs(phi1Dx1.calcFunction(x1, x2)) + Math.abs(phi1Dx2.calcFunction(x1, x2));
    }

    public FunctionWithTwoParameters getAbsDphi2() {
        return absDphi2;
    }

    public void setAbsDphi2() {
        this.absDphi2 = (x1, x2) -> Math.abs(phi2Dx1.calcFunction(x1, x2)) + Math.abs(phi2Dx2.calcFunction(x1, x2));
    }

    public String getPhi1String() {
        return phi1String;
    }

    public void setPhi1String(String phi1String) {
        this.phi1String = phi1String;
    }

    public String getPhi2String() {
        return phi2String;
    }

    public void setPhi2String(String phi2String) {
        this.phi2String = phi2String;
    }
}