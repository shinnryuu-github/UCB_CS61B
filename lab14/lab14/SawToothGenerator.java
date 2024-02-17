package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period, state;
    public SawToothGenerator(int period){
        state = 0;
        this.period = period;
    }
    @Override
    public double next(){
        state = (state + 1) % period;
        return -1 + 2 * (double)state / (period - 1);
    }
}
