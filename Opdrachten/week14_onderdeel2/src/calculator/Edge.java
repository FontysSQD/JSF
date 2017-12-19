package calculator;

import javafx.scene.paint.Color;

import java.io.*;

public class Edge implements Externalizable{
    public double X1, Y1, X2, Y2;
    public Color color;

    public Edge(){
    }

    public Edge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.color = color;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(X1);
        out.writeDouble(Y1);
        out.writeDouble(X2);
        out.writeDouble(Y2);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.X1 = in.readDouble();
        this.Y1 = in.readDouble();
        this.X2 = in.readDouble();
        this.Y2 = in.readDouble();
        this.color = Color.RED;
    }
}
