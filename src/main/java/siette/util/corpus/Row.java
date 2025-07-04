package siette.util.corpus;

import java.util.ArrayList;

import java.util.List;
import java.util.StringTokenizer;


import siette.util.text.Strings;
import siette.util.regex.Correspondencia;
import siette.util.regex.ExpresionRegular;

public class Row extends ArrayList<String>  implements Comparable {

//	static Logger logger = Logger.getLogger(Row.class);

	private static final long serialVersionUID = 1L;
	public final static int ALL = ExpresionRegular.ALL;
	public final static int ANY = ExpresionRegular.ANY;

	public static final int PARTIAL_MATCH = 0;
	public static final int EXACT_MATCH= 1;

	protected int matchMode = PARTIAL_MATCH;
	protected boolean ignoreCase = false;
	protected boolean ignoreAccent = true;
	private int sortKey = -1;
	private boolean invertedSort = false;
	
	
	public Row(String inputLine, String separator, boolean filterHTML) {
    	StringTokenizer st = new StringTokenizer(inputLine,separator);
    	while (st.hasMoreTokens()) {
    		String column = st.nextToken();
    		if (column!=null) {
        		column.trim();
        		if (filterHTML) {
        			column = Strings.filterHTMLAbove(column);
        		}
        		add(column);
    		}
    	}
	}

	// Crea una fila a partir de campos dados
	public Row(List lista, boolean filterHTML) {
		if (lista!=null) {
			for(int j=0; j<lista.size(); j++) {
				String column = (String)lista.get(j);
        		column.trim();
        		if (filterHTML) {
        			column = Strings.filterHTMLAbove(column);
        		}
				add(column);
			}
		}
	}

	public Row(String inputLine, String separator) {
		this(inputLine, separator, true);
	}
	
	
	public Row(Row row) {
		matchMode = row.matchMode;
		ignoreCase = row.ignoreCase;
		ignoreAccent = row.ignoreAccent;
		sortKey = row.sortKey;
		if (row!=null) {
			for(int j=0; j<row.size(); j++) {
				String column = (String)row.get(j);
				add(column);
			}
		}
	}
	
	public Row() {
		super();
	}

	
	public void setMatchMode(int mode) {
		matchMode = mode;
	}
	
	public void setIgnoreCase(boolean mode) {
		ignoreCase = mode;
	}
	
	public void setIgnoreAccent(boolean mode) {
		ignoreAccent = mode;
	}
	
	
	public boolean contains(int pos, String str) {
		boolean bRes = false;
		if (pos == ANY) {
			// Se busca en cualquier columna				
			for(int i=0; i<size(); i++) {
				bRes = bRes || contains(i,str);
			}
		} else if (str!=null && 0<=pos && pos<size()) {
			str = Correspondencia.normalizar(str, ignoreCase, ignoreAccent, false, false);
			// Se busca solo en la columna pos-esima
			String column = (String)get(pos);
			if (column!=null) {
				column = Correspondencia.normalizar(column, ignoreCase, ignoreAccent, false, false);
				if (matchMode == PARTIAL_MATCH && column.indexOf(str)!=-1 ) {
					bRes = true;
				} else if (matchMode == EXACT_MATCH && column.equals(str)) {
					bRes = true;
				}
			}
		}
		return bRes;
	}


	public boolean containsAll(int pos, ArrayList strList) {
        boolean bRes = false;
		if (strList!=null && !strList.isEmpty()) {
			bRes = true;
			for(int i=0; bRes && i<strList.size(); i++) {
				bRes = bRes && contains(pos, (String) strList.get(i));
			}
		}
		return bRes;
	}

	public boolean containsAll(int pos, String[] strs) {
        boolean bRes = false;
		if (strs!=null && strs.length>0) {
			bRes = true;
			for(int i=0; bRes && i<strs.length; i++) {
				bRes = bRes && contains(pos, strs[i]);
			}
		}
		return bRes;
	}

	public boolean containsAny(int pos, ArrayList strList) {
        boolean bRes = false;
		if (strList!=null && !strList.isEmpty()) {
			for(int i=0; !bRes && i<strList.size(); i++) {
				bRes = bRes || contains(pos, (String) strList.get(i));
			}
		}
		return bRes;
	}

	public boolean containsAny(int pos, String[] strs) {
        boolean bRes = false;
		if (strs!=null && strs.length>0) {
			for(int i=0; !bRes && i<strs.length; i++) {
				bRes = bRes || contains(pos, strs[i]);
			}
		}
		return bRes;
	}


    public String toString() {
    	String line = "";
    	if (!isEmpty()) {
    		line += get(0);
        	for(int i=1; i<size(); i++) {
        		line += ";\t\t" + get(i);
        	}
    	}
    	return line;
    }

	public void setSortKey(int key) {
		this.sortKey  = key;
	}
    
	public void setSortKey(int key, boolean invertedSort) {
		this.sortKey  = key;
		this.invertedSort = invertedSort;
	}
    
	public int compareTo(Object o) {
		int compare = -1;
		if (o instanceof Row) {
			Row r = (Row) o;
			if (r!=null) {
				if (r.size()>size()) {
					compare = +1;
				} else if (r.size()==size()) {
					compare = 0;
					if (0 <= sortKey && sortKey< r.size()) {
						try {
							Double val = new Double(get(sortKey));
							Double rval = new Double(r.get(sortKey));
							compare = val.compareTo(rval);
						} catch (Exception e) {
							compare = ((String)r.get(sortKey)).compareTo((String)get(sortKey));
						}
					} else {
						for(int i=0; i<size() && compare==0; i++) {
							String ri = (String) r.get(i);
							compare = ri.compareTo((String)get(i));
						}
					}
				}
			}
		}
		return invertedSort ? -compare : compare;
	}


	public int getInt(int n) {
		String st = get(n);
		int valor = Integer.MAX_VALUE;
		try {
			valor = Integer.parseInt(st);
		} catch(NumberFormatException e1) {
			try {
				double d = Double.parseDouble(st);
				valor = (int) d;
			} catch(NumberFormatException e2) {
			}
		}
		return valor;
	}
	
	public double getDouble(int n) {
		String st = get(n);
		double valor = Double.POSITIVE_INFINITY;
		try {
			double d = Double.parseDouble(st);
			valor = (int) d;
		} catch(NumberFormatException e2) {
		}
		return valor;
	}

}

