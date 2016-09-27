package JavaSpaceImplementation.JavaSpaceMult;

import JavaSpaceImplementation.ResultEntry;
public class MultResult extends ResultEntry{
    public Integer a;
    public Integer b;
    public Integer answer;
    
    public MultResult() {}

    public MultResult(Integer a, Integer b, Integer answer) {
        this.a = a;
        this.b = b;
        this.answer = answer;
    }
    
    public String toString() {
        return a + " times " + b + " = " + answer;
    }
}