package siette.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** This class imulates an extension  of the class java.util.Random, but allow to redefine
 * the constructors so that all objects created from this class share the same random seed
 * This allow to reproduce the pseudo-random instance generated just saving and reusing the seed
 * When the seed is explicitly changed the instance generated might change,
 */
public class Random  {

   private  java.util.Random random = new java.util.Random();
   private long seed;

   // Este constructor esta anulado
   /**
    * @see java.util.Random
    */
   public Random() { 
	   this.seed = System.currentTimeMillis();
	   random = new java.util.Random(seed);
   }
   /**
    * @see java.util.Random
    */
   public Random(long seed)  { 
	   this.seed = seed;
	   random = new java.util.Random(seed);
   }
   /**
    * @see java.util.Random
    */
   public Random(Random r) { 
	   this.seed = r.seed;
	   random = r.random;
   }

   
   // Metodos de la clase Random
   /**
    * @see java.util.Random
    */
   public  boolean 	nextBoolean() {
	   return random.nextBoolean();
   }
   /**
    * @see java.util.Random
    */
   public void 	nextBytes(byte[] bytes) {
   }
   /**
    * @see java.util.Random
    */
   public  double 	nextDouble() {
	   return random.nextDouble();
   }
   /**
    * @see java.util.Random
    */
   public  float 	nextFloat() {
	   return random.nextFloat();
   }
   /**
    * @see java.util.Random
    */
   public  double 	nextGaussian() {
	   return random.nextGaussian();
   }
   /**
    * @see java.util.Random
    */
   public  int 	nextInt() {
	   return random.nextInt();
   }
   /**
    * @see java.util.Random
    */
   public  int 	nextPositiveInt() {
	   int x = random.nextInt();
	   return x>0?x:-x;
   }
   /**
    * @see java.util.Random
    */
   public  int 	nextInt(int n) {
	   return random.nextInt(n);
   }
   /**
    * @see java.util.Random
    */
   public  long 	nextLong() {
	   return random.nextLong();
   }
   /**
    * @see java.util.Random
    */
   public void 	setSeed(long seed) {
	   this.seed = seed;
	   random.setSeed(seed);
   }

   public long getSeed() {
	   return seed;
   }

   // Metodos estaticos

   // double
  /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code double} value between {@code min} and {@code max}
    * acording to a pecific {@code format}
    * This method returns only those values that can be
    * obtained adding an integer multiples of {@code step}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @param step Increment value
    * @param format The format to display this number
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextDouble
   */
  public  double nextDouble(double min, double max, double step, String format) {
	   double valor = nextDouble(min,max,step);
	   if (format!=null && !format.equals("")) {
	          String st = format(valor, format);
	          st = st.replace(',','.');
	          valor = new Double(st).doubleValue();
	   }
	   return valor;
  }

   /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code double} value between {@code min} and {@code max}
     * acording to a pecific {@code format}
     *
     * @param min Minimal value
     * @param max Maximal value
     * @param format The format to display this number
     * @return the pseudorandom number
     *
     * @see java.util.Random#nextDouble
    */
   public  double nextDouble(double min, double max, String format) {
	   double valor = nextDouble(min,max);
	   if (format!=null && !format.equals("")) {
	          String st = format(valor, format);
	          st = st.replace(',','.');
	          valor = new Double(st).doubleValue();
	   }
	   return valor;
   }

   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code double} value between {@code min} and {@code max}
    * This method returns only those values that can be
    * obtained adding an integer multiples of {@code step}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @param step Increment value
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextDouble()
   */
   public  double nextDouble(double min, double max, double step) {
	 	  if (max<min) {
	 		  double temp = min;
	    	  min = max;
	    	  max = temp;
		  }
	      return min + step * nextLong(0,(long)((max-min)/step)) ;
   }

   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code double} value between {@code min} and {@code max}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextDouble()
   */
   public  double nextDouble(double min, double max) {
	 	  if (max<min) {
	 		  double temp = min;
	    	  min = max;
	    	  max = temp;
		  }
	      return min + random.nextDouble()*(max-min) ;
   }

   /**
    * Returns the next pseudorandom, ("pseudonormally") distributed
    * {@code double} value between {@code min} and {@code max}
    * with {@code mean = (max-min)/2} and
    * {@code standard deviation = (max-min)/(2*1,96)}
    * That is, the result of {@code java.util.nextGaussian} is transformed
    * from interval {@code (-1.96, 1.96)} to interval {@code (max-min)}
    * This will capture the 95% of the normal distribution.
    * The result is formated acording to a pecific {@code format}
    * This method guarantees that the returns value is included in the given
    * interval, that is the distribution is pruned at both ends
     *
    * @param min Minimal value
    * @param max Maximal value
    * @param format The format to display this number
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextGaussian()
   */
   public  double nextGausian(double min, double max, String format) {
	   double valor = nextGaussian(min,max);
	   if (format!=null && !format.equals("")) {
	          String st = format(valor, format);
	          st = st.replace(',','.');
	          valor = new Double(st).doubleValue();
	   }
	   return valor;
   }

   /**
    * Returns the next pseudorandom, ("pseudonormally") distributed
    * {@code double} value between {@code min} and {@code max}
    * with {@code mean = (max-min)/2} and
    * {@code standard deviation = (max-min)/(2*1,96)}
    * That is, the result of {@code java.util.nextGaussian} is transformed
    * from interval {@code (-1.96, 1.96)} to interval {@code (max-min)}
    * This will capture the 95% of the normal distribution.
    * The result is formated acording to a pecific {@code format}
    * This method guarantees that the returns value is included in the given
    * interval, that is the distribution is pruned at both ends
     *
    * @param min Minimal value
    * @param max Maximal value
     * @return the pseudorandom number
    *
    * @see java.util.Random#nextGaussian()
   */
   public  double nextGaussian(double min, double max) {
	      double P = 1.96;
	 	  if (max<min) {
	 		  double temp = min;
	    	  min = max;
	    	  max = temp;
		  }
	 	  double z;
	 	  do {
	 		 z = random.nextGaussian();
	 	  } while (z<-P || z>P) ;
	 	  double x = ((z+P)/(P+P)) * (max-min) + min;
	 	  return x;
   }

   // float
   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code float} value between {@code min} and {@code max}
    * acording to a pecific {@code format}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @param format The format to display this number
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextFloat
   */
   public  float 	nextFloat(float min, float max, String format) {
	   return (float) nextDouble((double)min, (double)max, format);
   }

   public  float 	nextFloat(float min, float max, float step, String format) {
	   return (float) nextDouble((double)min, (double)max, (double)step, format);
   }

   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code float} value between {@code min} and {@code max}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextFloat
   */
   public  float 	nextFloat(float min, float max) {
	   return (float) nextDouble((double)min, (double)max);
   }

   public  float 	nextFloat(float min, float max, float step) {
	   return (float) nextDouble((double)min, (double)max, (double)step);
   }

   // long
   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code long} value between {@code min} and {@code max}
    * acording to a pecific {@code format}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @param step Any number should be min + a multiple of step
    * @param format The format to display this number
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextLong
   */
   public  long 	nextLong(long min, long max, long step, String format) {
	   return nextLong(min,max,step);
   }

   public  long 	nextLong(long min, long max, String format) {
	   return nextLong(min,max);
   }


   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code long} value between {@code min} and {@code max}
    * This method returns only those values that can be
    * obtained adding an integer multiples of {@code step}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @param step Increment value
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextLong
   */
   public  long nextLong(long min, long max, long step) {
	   if (max<min) {
			long temp = min;
			min = max;
			max = temp;
	   }
	   return min + step * nextLong(0,(max-min)/step);
   }

   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code long} value between {@code min} and {@code max}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @return the pseudorandom number
    *
    * @see siette.util.Random#nextLong
   */
   public  long 	nextLong(long min, long max) {
		  if (max<min) {
			     long temp = min;
			     min = max;
			     max = temp;
		  }
		  // se amplian los extremos min, y max, para que sean equiprobables al redondear
	      return (Math.abs(random.nextLong()) % (max-min+1)) + min;
   }

   // int
   /**
    * Returns the next pseudorandom, uniformly distributed
    * {@code int} value between {@code min} and {@code max}
    * acording to a pecific {@code format}
    *
    * @param min Minimal value
    * @param max Maximal value
    * @param step Any number should be min + a multiple of step
    * @param format The format to display this number
    * @return the pseudorandom number
    *
    * @see java.util.Random#nextInt
   */
   public  int 	nextInt(int min, int max, int step, String format) {
	   return nextInt(min,max,step);
  }

   public  int 	nextInt(int min, int max, String format) {
	   return nextInt(min,max);
   }


  /**
   * Returns the next pseudorandom, uniformly distributed
   * {@code int} value between {@code min} and {@code max}
   *
   * @param min Minimal value
   * @param max Maximal value
   * @return the pseudorandom number
   *
   * @see java.util.Random#nextInt
  */
  public  int 	nextInt(int min, int max) {
	   return (int) nextLong((long)min,(long)max);
   }

  public  int 	nextInt(int min, int max, int step) {
	   return (int) nextLong((long)min,(long)max, (long)step);
  }


  /**
   * Returns the formated String corresponding to a
   * {@code double} value
   *
   * @param value Value to be formatted
   * @param format
   * @return the formated value
   *
   * @see java.text.DecimalFormat#format
  */
   public static String format(double value, String format) {
	   if (format == null || format.equals("")) return ""+value;
	   return (new DecimalFormat(format)).format(value);
   }


   /**
    * Returns the formated String corresponding to the scientific notation in HTML
    * of a {@code double} value, in the form like 1.23 x 10^4
    *
    * @param value Value to be formatted
    * @param format of the mantissa
   * @return the formated value
    *
    * @see java.text.DecimalFormat#format
   */
  public static String format10(double value, String format) {
	    int exponente  = (int) (Math.log(value) / Math.log(10));
	    exponente = (exponente<0) ? exponente-1 : exponente;
	    double mantisa = value / Math.pow(10, exponente);
        String sMantisa =  format(mantisa,format);
        String sExponente = "";
        if (exponente!=0) {
        	sExponente = " x 10<sup>" + exponente + "</sup>";
        }
	    return sMantisa + sExponente ;
   }



   /**
    * Returns the formated String corresponding to a
    * {@code float} value
    *
    * @param value Value to be formatted
    * @param format
    * @return the pseudorandom number
    *
    * @see java.text.DecimalFormat#format
   */
   public static String format(float value, String format) {
	   return format((double)value, format);
   }

   /**
    * Returns the formated String corresponding to a
    * {@code long} value
    *
    * @param value Value to be formatted
    * @param format
    * @return the pseudorandom number
    *
    * @see java.text.DecimalFormat#format
   */
   public static String format(long value, String format) {
	   return format((double)value, format);
   }

   /**
    * Returns the formated String corresponding to a
    * {@code int} value
    *
    * @param value Value to be formatted
    * @param format
    * @return the pseudorandom number
    *
    * @see java.text.DecimalFormat#format
   */
   public static String format(int value, String format) {
	   return format((double)value, format);
   }

   /**
    * Returns an object randomly selected from a list of
    * n objects. If the argument is null or empty it returns null
    *
    * @param list The list of objects
    * @return a randomly selected Object
    *
    * @see java.lang.Object
   */
   public  Object select(List list) {
	   if (list == null || list.size()==0) {
		   return null;
	   }
	   else {
		   return list.get(random.nextInt(list.size()));
	   }
   }

   /**
    * Returns an object randomly selected from a set of
    * n objects. If the argument is null or empty it returns null
    *
    * @param set The Set of objects
    * @return a randomly selected Object
    *
    * @see java.lang.Object
   */
   public  Object select(Set set) {
	   if (set == null || set.size()==0) {
		   return null;
	   }
	   else {
		   Object[] objs = (Object[]) set.toArray(new Object[set.size()]);
		   return select(objs);
	   }
   }

   /**
    * Returns an object randomly selected from an array of
    * n objects. If the argument is null or empty it returns null
    *
    * @param obj The array of objects
    * @return a randomly selected Object
    *
    * @see java.lang.Object
   */
   public  Object select(Object[] obj) {
	   if (obj == null || obj.length==0) {
		   return null;
	   }
	   else {
		   return obj[random.nextInt(obj.length)];
	   }
   }

   /**
    * Returns a String randomly selected from an array of
    * n Strings. If the argument is null or empty it returns null
    *
    * @param obj The array of Strings
    * @return a randomly selected String
    *
    * @see java.lang.String
   */
   public  String select(String[] obj) {
	   if (obj == null || obj.length==0) {
		   return null;
	   }
	   else {
		   return obj[random.nextInt(obj.length)];
	   }
   }

   /**
    * Returns a double number randomly selected from an array of
    * double. If the argument is null Double.NaN is returned
    *
    * @param obj The array of double
    * @return a randomly selected double
    *
   */
   public  double select(double[] obj) {
	   if (obj == null || obj.length==0) {
		   return Double.NaN;
	   }
	   else {
		   return obj[random.nextInt(obj.length)];
	   }
   }

   /**
    * Returns a double number randomly selected from an array of
    * float. If the argument is null Float.NaN is returned
    *
    * @param obj The array of float
    * @return a randomly selected float
    *
   */
   public  float select(float[] obj) {
	   if (obj == null || obj.length==0) {
		   return Float.NaN;
	   }
	   else {
		   return obj[random.nextInt(obj.length)];
	   }
   }

   /**
    * Returns a long number randomly selected from an array of
    * long. If the argument is null Integer.MIN_VALUE is returned
    *
    * @param obj The array of long
    * @return a randomly selected long
    *
   */
   public  long select(long[] obj) {
	   if (obj == null || obj.length==0) {
		   return Long.MIN_VALUE;
	   }
	   else {
		   return obj[random.nextInt(obj.length)];
	   }
   }

   /**
    * Returns a int number randomly selected from an array of
    * int. If the argument is null Integer.MIN_VALUE is returned
    *
    * @param obj The array of int
    * @return a randomly selected int
    *
   */
   public  int select(int[] obj) {
	   if (obj == null || obj.length==0) {
		   return Integer.MIN_VALUE;
	   }
	   else {
		   return obj[random.nextInt(obj.length)];
	   }
   }

   /**
    * Returns an array where the elements in random order
    *
    * @param obj The array of objects
    * @return a randomly ordered array of Objects
    *
    * @see java.lang.Object
   */
   public  Object[] shuffle(Object[] obj) {
	   if (obj == null || obj.length<2) {
		   return obj;
	   }
	   else {
		   for (int i=0; i<obj.length; i++) {
			   int j = i+random.nextInt(obj.length-i);
			   Object temp = obj[i];
			   obj[i] = obj[j];
			   obj[j] = temp;
		   }
		   return obj;
	   }
   }

   /**
    * Returns an array in random order
    *
    * @param obj The array of objects
    * @return a randomly ordered array of String
    *
    * @see java.lang.String
   */
   public  String[] shuffle(String[] obj) {
	   if (obj == null || obj.length<2) {
		   return obj;
	   }
	   else {
		   for (int i=0; i<obj.length; i++) {
			   int j = i+random.nextInt(obj.length-i);
			   String temp = obj[i];
			   obj[i] = obj[j];
			   obj[j] = temp;
		   }
		   return obj;
	   }
   }

   /**
    * Returns a double number array randomly selected from an array of
    * double. If the argument is null then null is returned
    *
    * @param obj The array of double
    * @return a randomly shuffed array of double
    *
   */
   public  double[] shuffle(double[] obj) {
	   if (obj == null || obj.length<2) {
		   return obj;
	   }
	   else {
		   for (int i=0; i<obj.length; i++) {
			   int j = i+random.nextInt(obj.length-i);
			   double temp = obj[i];
			   obj[i] = obj[j];
			   obj[j] = temp;
		   }
		   return obj;
	   }
   }

   /**
    * Returns a float number array randomly selected from an array of
    * double. If the argument is null then null is returned
    *
    * @param obj The array of float
    * @return a randomly shuffed array of float
    *
   */
   public  float[] shuffle(float[] obj) {
	   if (obj == null || obj.length<2) {
		   return obj;
	   }
	   else {
		   for (int i=0; i<obj.length; i++) {
			   int j = i+random.nextInt(obj.length-i);
			   float temp = obj[i];
			   obj[i] = obj[j];
			   obj[j] = temp;
		   }
		   return obj;
	   }
   }

   /**
    * Returns a long number array randomly selected from an array of
    * double. If the argument is null then null is returned
    *
    * @param obj The array of long
    * @return a randomly shuffed array of long
    *
   */
   public  long[] shuffle(long[] obj) {
	   if (obj == null || obj.length<2) {
		   return obj;
	   }
	   else {
		   for (int i=0; i<obj.length; i++) {
			   int j = i+random.nextInt(obj.length-i);
			   long temp = obj[i];
			   obj[i] = obj[j];
			   obj[j] = temp;
		   }
		   return obj;
	   }
   }

   /**
    * Returns a int number array randomly selected from an array of
    * double. If the argument is null then null is returned
    *
    * @param obj The array of int
    * @return a randomly shuffed array of int
    *
   */
   public  int[] shuffle(int[] obj) {
	   if (obj == null || obj.length<2) {
		   return obj;
	   }
	   else {
		   for (int i=0; i<obj.length; i++) {
			   int j = i+random.nextInt(obj.length-i);
			   int temp = obj[i];
			   obj[i] = obj[j];
			   obj[j] = temp;
		   }
		   return obj;
	   }
   }

   public  List shuffle(List list) {
		Object[] objs = toObjectArray(list);
		objs = shuffle(objs);
		return toList(objs);
	}

   
   public  Object[] toObjectArray(List list) {
		Object[] objs = null;
		if (list!=null) {
			objs = new Object[list.size()];
			for(int i=0; i<list.size(); i++) {
				objs[i] = (Object) list.get(i);
			}
		}
		return objs;
   }
   
   public static List toList(Object[] objs) {
	   List list = null;
		if (objs!=null) {
			list = new ArrayList();
			for(int i=0; i<objs.length; i++) {
				list.add(objs[i]); 
			}
		}
		return list;
   }
 
   /**
    * Returns an integer array of integers with a random
    * permutation of numbers from 0 to n-1
    *
    * @param n Tha number of values to shuffle
    * @return a randomly shuffed array of int
    *
   */
   public  int[] permutation(int n) {
	    if (n <= 0) {
	        return null;
	    }
	    int[] permuta = new int[n];
	    for (int i = 0; i < n; i++) {
	        permuta[i] = i;
	    }
	    return shuffle(permuta);
	}
   public  int[] permutacion(int n) {
	   return permutation(n);
   }

   /**
    * Genera un codigo aleatorio de N caracteres
    * @return
    */
   public static String code(int n) {
		String code = "";
		Random random = new Random();
		for(int i=0; i<n; i++) {
			code += (char) ((int) 'A' + random.nextInt(0,25));
		}
		return code;
   }

}
