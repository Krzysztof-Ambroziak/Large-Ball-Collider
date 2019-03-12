package pl.cba.reallygrid.util;

public class Pair<F, S> {
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
    
    public F getFirst() {
        return first;
    }
    
    public S getSecond() {
        return second;
    }
    
    @Override
    public String toString() {
        return "Pair<" + first.getClass().getSimpleName() + ", " +
                second.getClass().getSimpleName() + "> {" +
                first + ", " + second + '}';
    }
    
    private final F first;
    
    private final S second;
}
