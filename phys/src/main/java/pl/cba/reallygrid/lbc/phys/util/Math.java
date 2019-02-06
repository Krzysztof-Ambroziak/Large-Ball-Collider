package pl.cba.reallygrid.lbc.phys.util;

public final class Math {
    public static int divCell(int a, int b) {
        return (a + b - 1) / b;
    }
    
    private Math() {
    }
}
