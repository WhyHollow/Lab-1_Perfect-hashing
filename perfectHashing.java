import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;	
import java.util.Iterator;
import java.util.Arrays;

public class perfectHashing{

	public static void main(String[] args) throws Exception {

		File file = new File("randomKeys.txt");
		FileWriter wr = new FileWriter(file);

		int n = 1000;
		for(int i=0; i < n; i++) {
			long val = (long)(Math.random() * 10000000000L);
			wr.write(String.valueOf(val)+ " ");	
		}

		wr.close();

		int b = 0;
		int m = 2 * n;
		long p = 10000000019L;
		
		int a = (int)((Math.random() * 1000) % p) ;

		PriHashTable[] primary = new PriHashTable[m];
		for(int i =0; i< m; i++) {
			primary[i] = new PriHashTable();
		}

		int n_i = 0;
		
		do {
			b = (int)((Math.random() * 1000)% p);
			Scanner sc = new Scanner(file);

			for(int i =0; i < n; i++) {
				long val = sc.nextLong();
				
				int hashVal = (int)(((a*val + b)% p)% m);
				primary[hashVal].sec.add(val);
			}

			for(int i = 0; i < m; i++) {
				
				int temp = primary[i].sec.size();
				primary[i].n_i = temp;
				n_i += (int)(Math.pow(temp, (double)2));
			}
		} while(n_i > m);

		for(int i = 0; i< m; i++) {
			primary[i].p = 71784986521L;
			primary[i].m = (int)(Math.pow(primary[i].n_i, (double)2));
			primary[i].second = new long[primary[i].m];

			int collision;

			do {
				collision  = 0;

				Arrays.fill(primary[i].second, -1);

				primary[i].b = (int)((Math.random() * 100000) % primary[i].p);

				primary[i].a = (int)((Math.random() * 100000) % primary[i].p) ; 

				for(long val : primary[i].sec)
				{
					int hashVal = (int)((((primary[i].a * val) + primary[i].b) % primary[i].p) % primary[i].m);

					if(primary[i].second[hashVal] == -1) {
						
						primary[i].second[hashVal] = val ;
						
					} else {
						if(primary[i].second[hashVal] != val) {
							collision = 1;
							
						}
						
						break;
					}
				}


			} while(collision != 0);
		}

		File fileres = new File("result.txt");
		FileWriter wrr = new FileWriter(fileres);

		for(int i =0; i < m; i++)
		{
			wrr.write("i: " + i);
			
			for(int j = 0; j < primary[i].m; i++)
			{
				wrr.write(" " + primary[i].second[j] + " ");	
			}
			
			wrr.write("\n\r");
		}

		wrr.close();	
	}
}

class PriHashTable{ 
	int a, b, n_i, m;
	long p;
	ArrayList<Long> sec = new ArrayList<Long>();
	long[] second;
}