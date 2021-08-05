import java.lang.Math;

public class exprv {

    public static double exponential(double mean)
    {
    return -mean*Math.log( Math.random() );
    }

    public static void bin(int arraySize)
    {
        double incrSize = 0.5;
        double sample;
        int numBins = 11;
        int[] bins = new int[numBins];
        
        double[] normalizedBins = new double[numBins];

        for(int b = 0; b < numBins; b++) {
           bins[b] = 0;
        }

        for(int i = 0; i< arraySize; i++) {
        
            sample = exponential(1);

            for(int j = 0; j < numBins; j++) {
                if(sample >= (incrSize*(j+1) - incrSize) && sample < (incrSize*(j+1))){
                    //Put in corresponding bucket
                    bins[j]++;
                    //System.out.println(sample + " is going in bucket " + (j+1) + " Range:[" + (incrSize*(j+1) - incrSize) + "," + (incrSize*(j+1)) + ")");
                }
                else if(j == 10 && sample >= (incrSize*(j+1) - incrSize))
                {
                    bins[j]++;
                    //System.out.println(sample + " is going in bucket " + (j+1) + " Range:[" + (incrSize*(j+1) - incrSize) + "," + (incrSize*(j+1)) + ")");
                }   
            }
        }
        
        System.out.println("Approximate Density From Experiment:");
        for(int b = 0; b < numBins; b++) {
            //System.out.println("Bin " + (b+1) + ": " + bins[b]);
            normalizedBins[b] = (double)bins[b]/1000;
            System.out.println("[" + (incrSize*(b+1) - incrSize) + "," + (incrSize*(b+1)) + ") Count: " + bins[b] + "/1000" + " = " + normalizedBins[b]);
            //System.out.println(normalizedBins[b]);
         }
        
        double[] actualDensities = new double[numBins];
        System.out.println("Calculated Theoretical(Actual) Density:");
        for(int b = 0; b < numBins; b++) {
            double x = incrSize*(b+1);
            double result = Math.exp((-1) * x) - Math.exp((-1) * (x - incrSize));
            result = result * (-1);
            actualDensities[b] = result;
            System.out.println("e^(-" + x + ")" + " - " + "e^(-" + (x-incrSize)+ ") * (-1) " + " = " + result);
            
            //System.out.println(result);  
         }

         System.out.println("Building CDFs (Experimental Approximation vs Actual):");
         double sumApprox = normalizedBins[0];
         double sumActual = actualDensities[0];
         System.out.println("Interval --| Exp. Approx vs Actual"); 
         for(int b = 0; b < numBins; b++) {
            if(b>0){
                sumApprox += normalizedBins[b];
                sumActual += actualDensities[b];
            }
            System.out.println( "[" + (incrSize*(b+1) - incrSize) + "," + (incrSize*(b+1)) + ") --| " + sumApprox + " vs " + sumActual); 
         }

    }

    public static void main(String[] args) {
    
        //System.out.println(exponential(1));
        bin(1000);
        System.out.println("Program exiting...");
    }

}