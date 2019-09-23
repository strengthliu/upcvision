package com.surpass.vision.utils;

import java.util.Scanner;


public class Newton_interpolation {
	
    /*拷贝向量*/
    private static void copy_vector(double from[],double to[]){
        int k=from.length;
        int k2=to.length;
        if(k!=k2){
            System.out.println("the two vector's length is not equal!");
            System.exit(0);
        }
        for(int i=0;i<k;i++){
            to[i]=from[i];
        }
    }
    
    /*牛顿插值法*/
    public static double[] Newton_inter_method(double[] X,double[] Y,double X0[]){
        int m=X.length;
        int n=X0.length;
        double[] Y0=new double[n];
        double[] cp_Y=new double[m];
        for(int i1=0;i1<n;i1++){//遍历X0
            double t=0;
            int j=0;
            copy_vector(Y, cp_Y);
            int kk=j;
            /*求各级均差*/
            while(kk<m-1){
                kk=kk+1;
                for(int i2=kk;i2<m;i2++){
                    cp_Y[i2]=(cp_Y[i2]-cp_Y[kk-1])/(X[i2]-X[kk-1]);
                }
            }
            /*求插值结果*/
            double temp=cp_Y[0];
            for(int i=1;i<=m-1;i++){
                double u=1;
                int jj=0;
                while(jj<i){
                    u*=(X0[i1]-X[jj]);
                    jj++;
                }
                temp+=cp_Y[i]*u;
            }
            Y0[i1]=temp;
            System.out.println(Y0[i1]);
        }
        return Y0;
    }
    public static Double[] castLongArrayToDoubleArray(Long[] la) {
    	Double[] ret = new Double[la.length];
    	for(int i=0;i<la.length;i++) {
    		ret[i] = Double.valueOf(la[i]);
    	}
    	return ret;
    }
	public static Double[] Newton_inter_method(Double[] _x, Double[] _y, Double[] _x0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        /*输入插值点横纵坐标*/
        System.out.println("Input number of interpolation point:");
        Scanner scan=new Scanner(System.in);
        int m=scan.nextInt();
        System.out.println("Input number of test point:");
        int n=scan.nextInt();
        double X[]=new double[m];
        double Y[]=new double[m];
        double X0[]=new double[n];
        System.out.println("Input the elements of X:");//已知插值点
        for(int i=0;i<m;i++){
            X[i]=scan.nextDouble();
        }
        
        System.out.println("Input the elements of Y:");//已知插值点的函数值
        for(int i=0;i<m;i++){
            Y[i]=scan.nextDouble();
        }
 
        System.out.println("Input the elements of X0:");//需要求的插值点的横坐标标值
        for(int i=0;i<n;i++){
            X0[i]=scan.nextDouble();
        }
        Newton_inter_method(X,Y,X0);
	}

}