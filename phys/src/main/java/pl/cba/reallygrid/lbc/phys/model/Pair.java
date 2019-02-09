package pl.cba.reallygrid.lbc.phys.model;

public class Pair<S, T> {
    Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }
    
    public S getFirst() {
        return first;
    }
    
    public T getSecond() {
        return second;
    }
    
    private final S first;
    
    private final T second;
}
