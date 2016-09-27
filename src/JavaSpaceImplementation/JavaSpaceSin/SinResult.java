package JavaSpaceImplementation.JavaSpaceSin;

import JavaSpaceImplementation.ResultEntry;

public class SinResult extends ResultEntry{
    public Double angle;
    public Double result;

    public SinResult() {
    }

    public SinResult(Double angle, Double result) {
        this.angle = angle;
        this.result = result;
    }

    public String toString() {
        return "Sin of angle " + angle.doubleValue() + " is " + result;
    }
}
