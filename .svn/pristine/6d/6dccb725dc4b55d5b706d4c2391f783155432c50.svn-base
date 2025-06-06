package siette.util;

import java.util.ArrayList;

public class Combinatoria {

	public static int factorial(int n) {
		int factorial = 1;
		if (n<=1) {
			factorial = 1;
		} else {
			factorial = n * factorial(n-1);
		}
		return factorial;
	}

	public static int[][] permutaciones(int n) {
		int [][] permutaciones = new int[factorial(n)][n];
		if (n<0) {
			return null;
		} else if (n==0) {
			permutaciones = new int[0][0];
		} else if (n==1) {
			permutaciones[0][0] = 0;
		} else {
			int [][] previas = permutaciones(n-1);
			for (int k=0; k<n; k++) {
				for (int i=0; i<previas.length; i++) {
					int ii = k*previas.length+i;
					for(int j=0; j<previas[i].length ; j++) {
						int jj = (j<k) ? j : j+1;
						permutaciones[ii][jj] = previas[i][j];
					}
					permutaciones[ii][k] = (n-1);
				}
			}
		}
		return permutaciones;
	}

	public static Object[][] permutaciones(Object[] obj) {
		if (obj==null) {
			return null;
		} else if (obj.length == 0) {
			return new Object[0][0];
		} else {
			int n = obj.length;
			Object[][] objPermutados = new Object[factorial(n)][n];
			int[][] permutaciones = permutaciones(n);
			for(int i=0; i<permutaciones.length; i++) {
				for(int j=0; j<permutaciones[i].length; j++) {
					objPermutados[i][j] = obj[permutaciones[i][j]];
				}
			}
			return objPermutados;
		}
	}

	/*
	public static ArrayList<ArrayList<String>> permutaciones(ArrayList<String> array) {
		if (array==null) {
			return null;
		} else if (array.size() == 0) {
			return new ArrayList<ArrayList<String>>();
		} else {
			int n = array.size();
			ArrayList<ArrayList<String>> arrayPermutado = new ArrayList<ArrayList<String>>();
			int[][] permutaciones = permutaciones(n);
			for(int i=0; i<permutaciones.length; i++) {
				ArrayList<String> al = new ArrayList<String>();
				for(int j=0; j<permutaciones[i].length; j++) {
					al.add(array.get(permutaciones[i][j]));
				}
				arrayPermutado.add(al);
			}
			return arrayPermutado;
		}
	}
	*/
	
	public static <T> ArrayList<ArrayList<T>> permutaciones(ArrayList<T> array) {
		if (array == null) {
			return null;
		} else if (array.size() == 0) {
			return new ArrayList<ArrayList<T>>();
		} else {
			int n = array.size();
			ArrayList<ArrayList<T>> arrayPermutado = new ArrayList<ArrayList<T>>();
			int[][] permutaciones = permutaciones(n); 
			for (int i = 0; i < permutaciones.length; i++) {
				ArrayList<T> al = new ArrayList<T>();
				for (int j = 0; j < permutaciones[i].length; j++) {
					al.add(array.get(permutaciones[i][j]));
				}
				arrayPermutado.add(al);
			}
			return arrayPermutado;
		}
	}
	
	public static Object[][] variacionesRepeticion(Object[] obj, int n) {
		if (obj==null) {
			return null;
		} else if (obj.length == 0) {
			return new Object[0][0];
		} else {
			Object[][] variaciones = new Object[(int)Math.pow(obj.length,n)][n];
			for(int bigNumber=0; bigNumber<(int)Math.pow(obj.length,n); bigNumber++) {
				int number = bigNumber;
				for(int j=0; j<n; j++) {
					int k = number % obj.length;
					number /= obj.length;
					variaciones[bigNumber][j] = obj[k];
				}
			}
			return variaciones;
		}
	}
	

	public static void main(String argv[]) {
		int [][] x = permutaciones(3);
		for (int i=0; i<x.length; i++) {
			System.out.print("{ ");
			for(int j=0; j<x[i].length ; j++) {
				System.out.print(x[i][j]+" ");
			}
			System.out.println("}");
		}
		System.out.println("-------------");
		String[] st = {"alfa","beta","ganma"};
		Object[][] y = permutaciones(st);
		for (int i=0; i<y.length; i++) {
			System.out.print("{ ");
			for(int j=0; j<y[i].length ; j++) {
				System.out.print((String)y[i][j]+" ");
			}
			System.out.println("}");
		}
		System.out.println("-------------");
		Boolean[] bool = {true, false};
		Object[][] v = variacionesRepeticion(bool,3);
		for (int i=0; i<v.length; i++) {
			System.out.print("{ ");
			for(int j=0; j<v[i].length ; j++) {
				System.out.print(v[i][j]+" ");
			}
			System.out.println("}");
		}
		
	}
}