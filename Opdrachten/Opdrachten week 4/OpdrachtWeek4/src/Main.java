import calculate.Edge;
import calculate.KochFractal;
import java.util.Observable;
import java.util.Observer;

public class Main {
    public static void main(String[] args) {
        KochFractal kochFractal = new KochFractal();
        FractalObserver ob = new FractalObserver();
        kochFractal.addObserver(ob);
        kochFractal.setLevel(4);
        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
    }
}

class FractalObserver implements Observer{
    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge)arg;
        System.out.println("X1: " + e.X1 + ", X2: " + e.X2 + ", Y1: " + e.Y1 + ", Y2: " + e.Y2);
    }
}