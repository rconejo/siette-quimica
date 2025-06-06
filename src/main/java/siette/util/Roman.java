package siette.util;

public class Roman {

	private int n;
	
	public Roman(int n) {
		this.n = n;
	}
	
	public Roman(String st) {
		n = roman_to_integer(st);
	}
	
	public int toInteger() {
		return n;
	}
	
	public String toString() {
		return n>0 ? integer_to_roman(n) : null;
	}


	private String integer_to_roman(int number) {
	// declare arrays for Roman numbers
		String[] thousands = { "", "M", "MM", "MMM" };
		String[] hundreds = { "", "C", "CC", "CCC", "CD", "D", 
	                          "DC", "DCC", "DCCC", "CM" };
		String[] tens = { "", "X", "XX", "XXX", "XL", "L", 
	                          "LX", "LXX", "LXXX", "XC" };
		String[] units = { "", "I", "II", "III", "IV", "V", "VI",
	                          "VII", "VIII", "IX", "X" };
		// get thousands in the decimal number
		int numberOfThousands = number / 1000;
		// get hundreds in the decimal number
		int numberOfHundreds = (number / 100) % 10;
		// get tens in the decimal number
		int numberOfTens = (number / 10) % 10;
		// get units in the decimal number
		int numberOfUnits = number % 10;
		// get the corresponding Roman digits and merge them
		String romanNumber = thousands[numberOfThousands] + hundreds[numberOfHundreds]
	                             + tens[numberOfTens] + units[numberOfUnits];
		return romanNumber;
	}

    private int roman_to_integer(String str1) {
	    if(str1 == null) return -1;
	    int num = char_to_int(str1.charAt(0));

		for(int i = 1; i < str1.length(); i++){
			int curr = char_to_int(str1.charAt(i));
			int pre = char_to_int(str1.charAt(i-1));
			if(curr <= pre){
				num += curr;
			} else {
				num = num - pre*2 + curr;
			}
		}

		if (integer_to_roman(num) == str1) {
			return num;
		} else {
			return -1;
		}
}

	private int char_to_int(char c){
		switch (c) {
			case 'I': return 1;
			case 'V': return 5;
			case 'X': return 10;
			case 'L': return 50;
			case 'C': return 100;
			case 'D': return 500;
			case 'M': return 1000;
			default: return -1;
		}
	}

	public static void main(String[] args) {
		Roman n = new Roman(1234);
		System.out.println(n.toInteger());
		System.out.println(n);
		n = new Roman("DXLIV");
		System.out.println(n.toInteger());
		System.out.println(n);
	}
	
}
