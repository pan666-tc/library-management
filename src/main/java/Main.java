import java.util.*;
public class Main {
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        int[] num1=new int[n];
        for(int i=0;i<n;i++){
            num1[i]=in.nextInt();
        }
        int m=in.nextInt();
        int[] num2=new int[n];
        for(int i=0;i<m;i++){
            num2[i]=in.nextInt();
        }
        int a=m+n;
        int n1=0,m1=0;
        int[] sum=new int[a];
        for(int i=0;i<a;i++){
            if(num1[n1]>num2[m1]&&n1<n){
                sum[i]=num1[n1];
                n1++;
            }else if(m1<m){
                sum[i]=num2[m1];
                m1++;
            }
        }
        System.out.print(a+" ");
        for(int i=0;i<a;i++){
            System.out.print(sum[i]+" ");
        }
        System.out.println();
    }
}