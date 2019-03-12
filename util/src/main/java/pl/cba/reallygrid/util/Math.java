package pl.cba.reallygrid.util;

final class Math {
    static int ceilInt(double a) {
        return (int)java.lang.Math.ceil(a);
    }
    
    static int divInc(int divident, int divisor) {
        return divident / divisor + 1;
    }
    
    private Math() {
    }
}
