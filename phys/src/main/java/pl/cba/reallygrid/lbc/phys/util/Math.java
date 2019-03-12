package pl.cba.reallygrid.lbc.phys.util;

public final class Math {
    public static double normalize(double a, int size) {
        return a < 0 ? normalize(a + size, size) : a % size;
    }
    
    private Math() {
    }
}
