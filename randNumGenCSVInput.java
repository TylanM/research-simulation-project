//Written by github.com/tylanbm

import java.lang.Math;
import java.util.Arrays;
import java.io.*;  
import java.util.Scanner; 

public class randNumGenCSVInput {

  //numSamples MUST be <=10000
  public static void ConductKolSmirnovTest(int numSamples, double[] samples) {

    System.out.println("Conducting Kolmogorov-Smirnov Test with " + numSamples + " JMT-Random Numbers...");
      //Produce n random numbers
      int n = numSamples; 
      double[] randNums = new double[n];
      double[] iDivns = new double[n];
      double[] iDivnsminusR = new double[n];
      double[] Riminusidivn = new double[n];  

      //Step 1
      for(int i = 0; i< n; i++)
      {
        randNums[i] = samples[i]; //Samples from jmt
      }
      //Sort them
      Arrays.sort(randNums);

      //System.out.println("Sorted Random Number Array: ");  
      //Step 1 cont and Step 2
      for(int i = 0; i< n; i++)
      {
        //Step 1 Cont
        iDivns[i] = (i+1)/n;  
        //Step 2
        iDivnsminusR[i] = iDivns[i] - randNums[i]; 
        Riminusidivn[i] =  randNums[i] = (i)/n;
        //System.out.println("R." + i + ":  " + randNums[i]); 
      }

      //Find biggest D+
      Arrays.sort(iDivnsminusR);
      double dpos = iDivnsminusR[n-1];
      //find Biggest D-
      Arrays.sort(Riminusidivn);
      double dneg = Riminusidivn[n-1];
      //D = max(D=,D-)
      double d = Math.max(dpos, dneg);
      //For n = 10 alpha is BELOW in table. 
      double dalph0p01 = 0.490;
      double dalph0p05 = 0.410;

      System.out.println(" -Level of Significance -> 0.01:");
      if(dalph0p01 > d)
       System.out.println("    -h0 is not rejected... " + "d0.01: " + dalph0p01 + ">" + "d:" + d);
        else
        System.out.println("    -h0 is rejected... " + "d0.01: " + dalph0p01 + "!>" + "d:" + d);

      System.out.println(" -Level of Significance -> 0.05:");
      if(dalph0p05 > d)
       System.out.println("    -h0 is not rejected... " + "d0.05: " + dalph0p05 + ">" + "d:" + d);
        else
        System.out.println("    -h0 is rejected... " + "d0.05: " + dalph0p05 + "!>" + "d:" + d);

        System.out.println("----------------------*TEST COMPLETE*-------------------------------");
  }

  //numSamples MUST be <=10000
  public static void ConductChiTest(int numSamples, double[] samples)
  {
    System.out.println("Conducting Chi-square Test with " + numSamples + " JMT-Random Numbers...");
    int n = numSamples;
    
    double incrSize = 0.1;
    double sample;
    int numClasses = 10; 
    int[] classes = new int[numClasses];

    for(int c = 0; c < numClasses; c++) {
       classes[c] = 0;
    }
 
    double[] randNums = new double[n];

    //Generate 100 jmt-random nums
    for(int i = 0; i< n; i++) {
        randNums[i] = samples[i];
    }

    for(int i = 0; i < n; i++) {
      
      sample = randNums[i];

      for(int j = 0; j < numClasses; j++) {
        if(sample >= (incrSize*(j+1) - incrSize) && sample < (incrSize*(j+1))){
          //Put in corresponding bucket
          classes[j]++;
          //System.out.println(sample + " is going in bucket " + (j+1) + " Range:[" + (incrSize*(j+1) - incrSize) + "," + (incrSize*(j+1)) + ")");
        } else if((j == (numClasses-1)) && sample >= (incrSize*(j+1) - incrSize)) {
            classes[j]++;
            //System.out.println(sample + " is going in bucket " + (j+1) + " Range:[" + (incrSize*(j+1) - incrSize) + "," + (incrSize*(j+1)) + ")");
          }       
      }
    }
    
    /* DEBUG
    for(int c = 0; c < numClasses; c++) {
      //System.out.println("Class " + (c+1) + ": " + classes[c]);
      //System.out.println("[" + (incrSize*(b+1) - incrSize) + "," + (incrSize*(b+1)) + ")");
      //System.out.println(bins[b]);
    }
    */
  
    //Ei (Will stay constant)
    double Ei = n/numClasses;

    double result = 0; //(Oi - Ei)^2 / Ei = x0^2

    for(int c = 0; c < numClasses; c++) {
      //System.out.println("(" + classes[c] + " - " + Ei + ")^2" + " / " + Ei);
      result += Math.pow((classes[c]-Ei),2)/Ei;
      //System.out.println(result);
    }

    double dalph0p01 = 21.7;
    double dalph0p05 = 16.9;

    System.out.println(" -Level of Significance -> 0.01:");
      if(dalph0p01 > result)
       System.out.println("    -h0 is not rejected... " + "d0.01: " + dalph0p01 + ">" + "d:" + result);
        else
        System.out.println("    -h0 is rejected... " + "d0.01: " + dalph0p01 + "!>" + "d:" + result);

      System.out.println(" -Level of Significance -> 0.05:");
      if(dalph0p05 > result)
       System.out.println("    -h0 is not rejected... " + "d0.05: " + dalph0p05 + ">" + "d:" + result);
        else
        System.out.println("    -h0 is rejected... " + "d0.05: " + dalph0p05 + "!>" + "d:" + result);

        System.out.println("----------------------*TEST COMPLETE*-------------------------------");
  }

  public static double[] GetCSVRandomNumbers(){
    
    System.out.println("Getting data from CSV file...");
    
    double[] result = new double[10000];
    int size = 0;

    try{
    //parsing a CSV file into Scanner class constructor  
    File file = new File("C://Users//tylan//Dropbox//My PC (LAPTOP-94ABS753)//Desktop//CSC446A3//Logger1RandomData.csv");
    //File file = new File("Logger1RandomData.csv");
    Scanner sc = new Scanner(file); 
      
    
    int i = 0;
    
    sc.useDelimiter(","); 
    
    while (sc.hasNextLine()) {      
      String line = sc.nextLine();
      String[] tokens = line.split(",");
 
      if(i%2==1) {
        result[size] = Double.parseDouble(tokens[2]);
        size++;  
        //System.out.println(tokens[2]);  
      } 
      i++;
    }   
    
    sc.close();  
    } catch(Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
    //System.out.println("Size: " + size);
    return result; 
  }

    public static void main(String[] args) {
      
      double[] jmtSamples = GetCSVRandomNumbers();

      ConductKolSmirnovTest(10,jmtSamples);
      ConductChiTest(100,jmtSamples);

      ConductKolSmirnovTest(10000,jmtSamples);
      ConductChiTest(10000,jmtSamples);
      System.out.println("Program exiting...");
    }
  }
