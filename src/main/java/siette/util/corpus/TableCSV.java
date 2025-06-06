package siette.util.corpus;

import siette.util.Random;
import siette.util.RandomException;
import siette.util.regex.ExpresionRegular;
import siette.util.text.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


// import org.apache.log4j.Logger;

public class TableCSV implements Cloneable {


	public final static int ALL = ExpresionRegular.ALL;
	public final static int ANY = ExpresionRegular.ANY;
	
	public final static int LEFT = ExpresionRegular.LEFT;
	public final static int NONE = ExpresionRegular.NONE;
	public final static int RIGHT = ExpresionRegular.RIGHT;
	
	public final static String DEFAULT_SEPARATOR = " \t,";
	public final static String OPTIONS_SEPARATOR = "\\+|\\|";
	public final static String DEFAULT_CHARSET = "UTF-8"; // "x-MacRoman"

   // static Logger logger = Logger.getLogger(Table.class);
   protected Row firstRow ; // Esto es un ArrayList de String
   protected ArrayList<Row> rows ; // Esto es un ArrayList de ArrayList de String
   protected boolean exclusive;
   protected boolean filterHTML;
   protected String charSet;
   protected int matchMode;
   protected boolean ignoreCase;
   protected boolean ignoreAccent;
   
   protected static Random RANDOM = null;
   static {
		   RANDOM = new Random(); 
   }
   
   protected Random random = null; // Si no se inicializa correctamente generara un RandomException al ser null

   
   /* Constructores */

   public TableCSV(Random random, String sURL, boolean hasTitleRow, String separator, String charSet, boolean filterHTML) {
	   this(random);
	   this.filterHTML = filterHTML;
	   this.charSet = charSet;
	   setTable(sURL, hasTitleRow, separator);
   }

   public TableCSV(Random random, String sURL, boolean hasTitleRow, String separator) {
	   this(random, sURL, hasTitleRow, separator, DEFAULT_CHARSET, true);
   }

   public TableCSV(Random random, String sURL, String separator, String charSet) {
	   this(random, sURL, true, separator, charSet, true);
   }

   public TableCSV(Random random, String sURL, String separator) {
	   this(random, sURL, true, separator, DEFAULT_CHARSET, true);
   }

   public TableCSV(Random random, String sURL, boolean hasTitleRow) {
	   this(random, sURL, hasTitleRow, DEFAULT_SEPARATOR);
   }

   public TableCSV(Random random, String sURL) {
	   this(random, sURL, true, DEFAULT_SEPARATOR);
   }
   
   
   public TableCSV(String sURL, boolean hasTitleRow, String separator, String charSet, boolean filterHTML) {
	   this(RANDOM, sURL, hasTitleRow, separator, charSet, filterHTML);
   }

   public TableCSV(String sURL, boolean hasTitleRow, String separator) {
	   this(sURL, hasTitleRow, separator, DEFAULT_CHARSET, true);
   }

   public TableCSV(String sURL, String separator, String charSet) {
	   this(sURL, true, separator, charSet, true);
   }

   public TableCSV(String sURL, String separator) {
	   this(sURL, true, separator, DEFAULT_CHARSET, true);
   }

   public TableCSV(String sURL, boolean hasTitleRow) {
	   this(sURL, hasTitleRow, DEFAULT_SEPARATOR);
   }

   public TableCSV(String sURL) {
	   this(sURL, true, DEFAULT_SEPARATOR);
   }

   public TableCSV(Random r) {
	   random = r;
	   firstRow = new Row();
	   rows = new ArrayList<Row>();
	   setExclusive();
	   setFilterHTML();
	   setCharSet(DEFAULT_CHARSET);
	   setMatchMode(Row.PARTIAL_MATCH);
	   ignoreCase = true;
	   ignoreAccent = true;
   }

   public TableCSV() {
	   this(RANDOM);
   }

   public TableCSV(TableCSV t) {
	   this();
	   random = t.random;
	   firstRow = t.firstRow;
	   exclusive = t.exclusive;
	   filterHTML = t.filterHTML;
	   charSet = t.charSet;
	   matchMode = t.matchMode;
	   ignoreCase = t.ignoreCase;
	   ignoreAccent = t.ignoreAccent;
	   rows = new ArrayList<Row>(); {
		   for(Row row: t.rows) {
			   rows.add(row);
		   }
	   }
   }

 
protected TableCSV setTable(ArrayList<Integer> indexes) {
	   TableCSV newTable = new TableCSV(this.random);
	   if (indexes!=null) {
		   for(int i=0; i<indexes.size(); i++) {
			   int k = indexes.get(i).intValue();
			   Row row = rows.get(k);
			   if (row!=null) {
				   newTable.rows.add(row);
			   }
		   }
	   }
	   newTable.firstRow = firstRow;
	   newTable.exclusive = exclusive;
	   newTable.filterHTML = filterHTML;
	   newTable.charSet = charSet;
	   newTable.matchMode = matchMode;
	   newTable.ignoreCase = ignoreCase;
	   newTable.ignoreAccent = ignoreAccent;
	   return newTable;
   }
   
   public Object clone(){
	   TableCSV obj=null;
       try{
           obj= (TableCSV) super.clone();
    	   obj.firstRow = new Row(firstRow, filterHTML);
    	   obj.rows = new ArrayList<Row>();
    	   obj.exclusive = exclusive;
    	   obj.filterHTML = filterHTML;
    	   obj.charSet = charSet;
    	   obj.matchMode = matchMode;
    	   obj.ignoreCase = ignoreCase;
    	   obj.ignoreAccent = ignoreAccent;
    	   obj.random = random;
       }catch(CloneNotSupportedException ex){
       }
       return obj;
   }
  
   public void setRandom(Random r) {
	   random = r;
   }
   

   
   /** Lee los terminos de la tabla desde un fichero web
    *
    * @param url
    * @param hasTitleRw si la primera fila contiene titulos de las columnas
    */
   public void setTable(String sURL, boolean hasTitleRow) {
	   if (sURL!=null) {
		   setTable(sURL, hasTitleRow, DEFAULT_SEPARATOR);
	   }
   }
   
   public void setTable(String sURL, boolean hasTitleRow, String separator) {
	   if (sURL!=null && !sURL.equals("")) {

		    // Abrir la conexion de datos
            try {
            	BufferedReader bufread = null;
    			// busca en el propio jar
    			InputStream in = getClass().getResourceAsStream(sURL);
    			bufread = new BufferedReader(new InputStreamReader(in));
        		// Abrir el fichero desde la URL
                if (bufread!= null) {
		            String inputLine;
		            // Leer el fichero por lineas
		            boolean isFirstRow = true;
		            while ((inputLine = bufread.readLine()) != null) {
		            	inputLine = inputLine.trim();
		            	if (!inputLine.startsWith("#") && !inputLine.equals("")) {
			            	Row row = new Row(inputLine, separator, filterHTML);
			            	if (!row.isEmpty()) {
			            		if (hasTitleRow && isFirstRow) {
			            			firstRow = row;
			            			isFirstRow = false;
			            		} else {
			            			rows.add(row);
			            		}
			            	}
		            	}
		            }
		            bufread.close();
                }
            } catch (IOException ioe) {
            		// logger.fatal("IOException= " + ioe);
            } catch (Exception e) {
            		// logger.fatal("Exception= " + e);
            } 
	   }
   }



/**
 * Esta es una funcion auxiliar que intenta convertir un posible double en un int 
 * El problema es que la funcion numericCellValue solo lee numero en formato double
 * 
 * @param numericCellValue
 * @return
 */
@SuppressWarnings("unused")
private String formatInt(double numericCellValue) {
	int entero = (int) numericCellValue;
	if (entero == numericCellValue) {
		return ""+entero;
	} else {
		return ""+numericCellValue;
	}
}

   
   

   /**
    * Si esta opcion se activa hace que cada vez que se selecciona un
    * objeto, se elimine de la lista de oibles objetos a seleccionar
    * de manera que la proxima seleccion devuelva objetos diferentes
    *
    */
   public void setExclusive() {
	   exclusive = true;
   }

   public void setNotExclusive() {
	   exclusive = false;
   }

   public void setFilterHTML() {
	   filterHTML = true;
   }

   public void setNotFilterHTML() {
	   filterHTML = false;
   }

   public void setCharSet(String cs) {
	   charSet = cs;
   }

   public void setMatchMode(int mode) {
	   matchMode = mode;
	   for(Row row : rows) {
		   row.setMatchMode(mode);
	   }
   }
   
   public void setExactMatch() {
	   setMatchMode(Row.EXACT_MATCH);
   }

   public void setPartialMatch() {
	   setMatchMode(Row.PARTIAL_MATCH);
   }

   public void setIgnoreCase() {
	   ignoreCase = true;
   }

   public void setCaseSensitive() {
	   ignoreCase = false;
   }

   public void setIgnoreAccent() {
	   ignoreAccent = true;
   }

   public void setNotIgnoreAccent() {
	   ignoreAccent = false;
   }
   
   
   /**
    * @return Number of rows in the table
    */
   public int size() {
	   int size = 0;
	   if (rows!=null) {
		   size = rows.size();
	   }
	   return size;
   }
   
   /**
    * @return Number of rows in the table
    */
   public int nRows() {
	   return size();
   }
   
   /**
    * @return Number of columns in the table
    */
   public int nCols() {
	   int size = 0;
	   if (firstRow!=null && !firstRow.isEmpty()) {
		   size = firstRow.size();
	   } else if (rows!=null && !rows.isEmpty()) {
		   Row row = rows.get(0);
		   size = row.size();
	   } 
	   return size;
   }
   
   /**
    * 
    */
   @SuppressWarnings("unchecked")
   public TableCSV sort(int pos, boolean invertedSort) {
	   if (rows!=null) {
		   for(int i=0; i<rows.size(); i++) {
			   rows.get(i).setSortKey(pos, invertedSort);
		   }
		   Collections.sort(rows);
	   }
	   return this;
   }

   public TableCSV sort(int pos) {
	   return sort(pos, false);
   }

   /**
    * 
    */
   public TableCSV sort(String name) {
	   int pos = find(name);
	   return sort(pos);
   }
   
   /**
    * Añadir una fila a la tabla
    * @param row
    */
   public void addRow(Row row) {
	   rows.add(row);
   }
   
   
   /**
    * Añade a esta tabla todas las filas de la otra tabla
    * @param ...
    */
   public void addTable(TableCSV table) {
	   for(Row row: table.rows) {
		   rows.add(row);
	   }
   }

   /**
    * Quitar todas las filas de la tabla que cumplan una determinada condicion
    * @param ...condiciones que deben cumplir de las filas a eliminar
    */
   public void delRow(int columnPos, String[] attPos, int columnNeg, String[] attNeg) {
	   List<Integer> indexes = selectIndexes(columnPos, attPos, ANY, columnNeg, attNeg , ANY);
	   if (indexes!=null && !indexes.isEmpty()) {
		   removeFromIndexes(indexes);
	   }
   }

   public void delRow(int columnPos)  {
	   delRow(columnPos, new String[] {}, ANY, new String[] {} );
   }

   public void delRow(int columnPos, String[] att)  {
	   delRow(columnPos, att, ANY, new String[] {} );
   }

   public void delRow(int columnPos, String att)  {
	   delRow(columnPos, new String[] {att}, ANY, new String[] {} );
   }

   // Mismas funciones pero usando nombres de las columnas
   public void delRow(String namePos, String[] attPos, String nameNeg, String[] attNeg)  {
	   int columnPos = find(namePos);
	   int columnNeg = find(nameNeg);
	   delRow(columnPos, attPos, columnNeg, attNeg);
   }

   public void delRow(String namePos, String[] att)  {
	   delRow(namePos, att, null, new String[] {} );
   }

   public void delRow(String namePos, String att)  {
	   delRow(namePos, new String[] {att}, null, new String[] {} );
   }

   // Busqueda en todas selectAll casilas
   public void delRow(String[] att)  {
	   delRow(ANY, att,  ANY, new String[] {} );
   }

   public void delRow(String att)  {
	   delRow(ANY, new String[] {att},  ANY, new String[] {} );
   }
   
   
   /**
    * Selecciona todas las palabras del diccionario que ontenga un conjunto de atributos positivos attPos
    * y ninguno del conjunto de atributos negativos attNeg
    * @param att Lista de atributos
    * @return Los indices en la lista de palabras
    */
   protected ArrayList<Integer> selectIndexes(int columnPos, String[] attPos, int modePos, int columnNeg, String[] attNeg, int modeNeg ) {
	   ArrayList<Integer> list = new ArrayList<Integer>();
	   if (attPos!=null && attNeg!=null && (attPos.length>0 || attNeg.length>0) ) {
			ArrayList<String> attPosList = new ArrayList<String>(attPos.length);
			for(int i=0; i<attPos.length; i++) {
				attPosList.add(attPos[i]);
			}
			ArrayList<String> attNegList = new ArrayList<String>(attNeg.length);
			for(int i=0; i<attNeg.length; i++) {
				attNegList.add(attNeg[i]);
			}
			for(int k=0; k<rows.size(); k++) {
	             Row row = rows.get(k);
	             if (      (modePos==ALL ? row.containsAll(columnPos, attPosList) : row.containsAny(columnPos, attPosList) )
	                  && ! (modeNeg==ANY ? row.containsAny(columnNeg, attNegList) : row.containsAll(columnNeg, attNegList) )
	                ) {
	            	 list.add(k);
	             }
			}
	    } else {
			for(int k=0; k<rows.size(); k++) {
              	 list.add(k);
			}
	    }
		return list;
   }

   protected ArrayList<Integer> selectIndexes(String namePos, String[] attPos, int modePos, String nameNeg, String[] attNeg, int modeNeg ) {
	   int columnPos = find(namePos);
	   int columnNeg = find(nameNeg);
	   return selectIndexes(columnPos, attPos, modePos, columnNeg, attNeg, modeNeg);
   }

   /**
    * A partir de un conjunto de indices, devuelve los objetos correspondientes
    * @param indexes
    * @return a set of Rows
    */
   protected Set<Row> getFromIndexes (List<Integer> indexes) {
	   HashSet<Row> hs = new HashSet<Row>();
	   if (indexes!=null) {
		   for(int i=0; i<indexes.size(); i++) {
			   int k = indexes.get(i).intValue();
			   Row row = rows.get(k);
			   if (row!=null) {
				   hs.add(row);
			   }
		   }
	   }
	   return hs;
   }

   /**
    * A partir de un conjunto de indices, elimina los elementos de la lista de palabras
    * Los indices deben estar ordenados de menor a mayor.
    * @param indexes
    * @return
    */
   protected void removeFromIndexes (List<Integer> indexes) {
	   if (indexes!=null) {
		   for(int i=indexes.size()-1; i>=0; i--) {
			   int k = indexes.get(i).intValue();
			      rows.remove(k);
		   }
	   }
   }


   /**
    * Selecciona todas las palabras de la columna i-esima de la tabla que contengan
    * un conjunto de aributos y no contengan otro conjunto de atributos en la columna j-esima
    * @param columnPos orden de la columna donde buscar
    * @param attPos Lista de atributos que debe contener
    * @param modePos modo en el qe buscar ALL/ANY
    * @return a set of String
    */
   public TableCSV selectTable(int columnPos, String[] attPos, int modePos, int columnNeg, String[] attNeg, int modeNeg ) {
	   ArrayList<Integer> indexes = selectIndexes(columnPos, attPos, ANY, columnNeg, attNeg , ANY);
	   return setTable(indexes);
   }

   public TableCSV selectTable(int columnPos, String[] attPos, int columnNeg, String[] attNeg) {
	   return selectTable(columnPos, attPos, ANY, columnNeg, attNeg , ANY);
   }

   public TableCSV selectTable(int columnPos, String[] att, int mode) {
	   return selectTable(columnPos, att, mode, ANY, new String[] {} , ANY );
   }

   public TableCSV selectTable(int columnPos, String[] att) {
	   return selectTable(columnPos, att, ANY, ANY, new String[] {}, ANY );
   }

   public TableCSV selectTable(int columnPos, String att) {
	   return selectTable(columnPos, new String[] {att}, ANY, ANY, new String[] {}, ANY );
   }

   // Mismas funciones pero usando nombres de las columnas
   public TableCSV selectTable(String namePos, String[] attPos, int modePos, String nameNeg, String[] attNeg, int modeNeg ) {
	   int columnPos = find(namePos);
	   int columnNeg = find(nameNeg);
	   return selectTable(columnPos, attPos, modePos, columnNeg, attNeg, modeNeg);
   }

   public TableCSV selectTable(String namePos, String[] attPos,  String nameNeg, String[] attNeg) {
	   return selectTable(namePos, attPos, ANY, nameNeg, attNeg, ANY);
   }

   public TableCSV selectTable(String namePos, String[] att, int mode) {
	   return selectTable(namePos, att, mode, null, new String[] {} , ANY );
   }

   public TableCSV selectTable(String namePos, String[] att) {
	   return selectTable(namePos, att, ANY, null, new String[] {}, ANY );
   }

   public TableCSV selectTable(String namePos, String att) {
	   return selectTable(namePos, new String[] {att}, ANY, null, new String[] {}, ANY );
   }

   // Busqueda en todas las casilas
   public TableCSV selectTable(String[] att) {
	   return selectTable(ANY, att, ANY, ANY, new String[] {}, ANY );
   }

   public TableCSV selectTable(String att) {
	   return selectTable(ANY, new String[] {att}, ANY, ANY, new String[] {}, ANY );
   }

   public TableCSV selectTable() {
	   return this;
   }

   /**
    * Selecciona una subtabla con n filas elegidos al azar
    * @throws CloneNotSupportedException 
    */
   public TableCSV selectTable(int n)  {
	   TableCSV table = (TableCSV) this.clone();
	   for(int i=0; i<n; i++) {
		   Row row = selectRow();
		   if (row!=null) {
			   table.rows.add(row);
		   }
	   }
	   return table;
   }


   
   
   
   /**
    * Selecciona todas las palabras de la columna i-esima de la tabla que contengan
    * un conjunto de aributos y no contengan otro conjunto de atributos en la columna j-esima
    * @param columnPos orden de la columna donde buscar
    * @param attPos Lista de atributos que debe contener
    * @param modePos modo en el qe buscar ALL/ANY
    * @return a set of String
    */
   public Set<Row> selectSet(int columnPos, String[] attPos, int modePos, int columnNeg, String[] attNeg, int modeNeg ) {
	   List<Integer> indexes = selectIndexes(columnPos, attPos, ANY, columnNeg, attNeg , ANY);
	   Set<Row> set = getFromIndexes(indexes);
	   if (exclusive) {
		   removeFromIndexes(indexes);
	   }
	   return set;
   }

   public Set<Row> selectSet(int columnPos, String[] attPos, int columnNeg, String[] attNeg) {
	   return selectSet(columnPos, attPos, ANY, columnNeg, attNeg , ANY);
   }

   public Set<Row> selectSet(int columnPos, String[] att, int mode) {
	   return selectSet(columnPos, att, mode, ANY, new String[] {} , ANY );
   }

   public Set<Row> selectSet(int columnPos, String[] att) {
	   return selectSet(columnPos, att, ANY, ANY, new String[] {}, ANY );
   }

   public Set<Row> selectSet(int columnPos, String att) {
	   return selectSet(columnPos, new String[] {att}, ANY, ANY, new String[] {}, ANY );
   }

   // Mismas funciones pero usando nombres de las columnas
   public Set<Row> selectSet(String namePos, String[] attPos, int modePos, String nameNeg, String[] attNeg, int modeNeg ) {
	   int columnPos = find(namePos);
	   int columnNeg = find(nameNeg);
	   return selectSet(columnPos, attPos, modePos, columnNeg, attNeg, modeNeg);
   }

   public Set<Row> selectSet(String namePos, String[] attPos,  String nameNeg, String[] attNeg) {
	   return selectSet(namePos, attPos, ANY, nameNeg, attNeg, ANY);
   }

   public Set<Row> selectSet(String namePos, String[] att, int mode) {
	   return selectSet(namePos, att, mode, null, new String[] {} , ANY );
   }

   public Set<Row> selectSet(String namePos, String[] att) {
	   return selectSet(namePos, att, ANY, null, new String[] {}, ANY );
   }

   public Set<Row> selectSet(String namePos, String att) {
	   return selectSet(namePos, new String[] {att}, ANY, null, new String[] {}, ANY );
   }

   // Busqueda en todas las casilas
   public Set<Row> selectSet(String[] att) {
	   return selectSet(ANY, att, ANY, ANY, new String[] {}, ANY );
   }

   public Set<Row> selectSet(String att) {
	   return selectSet(ANY, new String[] {att}, ANY, ANY, new String[] {}, ANY );
   }

   public Set<Row> selectSet() {
	   TreeSet<Row> hs = new TreeSet<Row>();
	   if (rows!=null) {
		   for(int i=0; i<rows.size(); i++) {
			   Row row = rows.get(i);
			   if (row!=null) {
				   hs.add(row);
			   }
		   }
	   }
	   return hs;
   }


   /**
    * Selecciona una subtabla con todos los campos, pero solo los registros
    * que cumplen unas determinadas condiciones, convertidos en una matriz bidimensional de String
    * @param att Lista de atributos
    * @return an array of array of String
    */
   public String[][] selectAll(int columnPos, String[] attPos, int modePos, int columnNeg, String[] attNeg, int modeNeg) {
	   Set<Row> set = selectSet(columnPos, attPos, modePos, columnNeg, attNeg, modeNeg) ;
	   Row[] selectedRows = set.toArray(new Row[set.size()]);
	   String[][] selectedStringString = new String[set.size()][firstRow.size()];
	   for(int i=0; i<set.size(); i++) {
		   selectedStringString[i] = (String[]) selectedRows[i].toArray(new String[selectedRows[i].size()]);
	   }
	   return selectedStringString;
   }

   public String[][] selectAll(int columnPos, String[] attPos, int columnNeg, String[] attNeg) {
	   return selectAll(columnPos, attPos, ANY, columnNeg, attNeg, ANY ) ;
   }

   public String[][] selectAll(int columnPos, String[] att, int mode) {
	   return selectAll(columnPos, att, mode, ANY, new String[] {} , ANY);
   }

   public String[][] selectAll(int columnPos, String[] att) {
	   return selectAll(columnPos, att, ANY, ANY, new String[] {} , ANY );
   }

   public String[][] selectAll(int columnPos, String att) {
	   return selectAll(columnPos, new String[] {att}, ANY, ANY, new String[] {} , ANY );
   }

   // Mismas funciones pero usando nombres de las columnas
   public String[][] selectAll(String namePos, String[] attPos, int modePos, String nameNeg, String[] attNeg, int modeNeg ) {
	   int columnPos = find(namePos);
	   int columnNeg = find(nameNeg);
	   return selectAll(columnPos, attPos, modePos, columnNeg, attNeg, modeNeg);
   }

   public String[][] selectAll(String namePos, String[] attPos,  String nameNeg, String[] attNeg) {
	   return selectAll(namePos, attPos, ANY, nameNeg, attNeg, ANY);
   }

   public String[][] selectAll(String namePos, String[] att, int mode) {
	   return selectAll(namePos, att, mode, null, new String[] {} , ANY );
   }

   public String[][] selectAll(String namePos, String[] att) {
	   return selectAll(namePos, att, ANY, null, new String[] {}, ANY );
   }

   public String[][] selectAll(String namePos, String att) {
	   return selectAll(namePos, new String[] {att}, ANY, null, new String[] {}, ANY );
   }

   // Busqueda en todas selectAll casilas
   public String[][] selectAll(String[] att) {
	   return selectAll(ANY, att, ANY, ANY, new String[] {}, ANY );
   }

   public String[][] selectAll(String att) {
	   return selectAll(ANY, new String[] {att}, ANY, ANY, new String[] {}, ANY );
   }

   public String[][] selectAll() {
	   return getTable();
   }


   /**
    * Selecciona una palabra de la tabla ue contenga un conjunto de atributos
    * Si hay varias, selecciona una al azar.
    * En caso de que no haya ninguna devuelve la cadena vacia
    * @param att
    * @return
    */
   public String[] select(int columnPos, String[] attPos, int columnNeg, String[] attNeg) throws RandomException {
	   String[] selected = null;
	   List<Integer> indexes = selectIndexes(columnPos, attPos, ANY, columnNeg, attNeg , ANY);
	   if (indexes!=null && !indexes.isEmpty()) {
		   if (random!=null) {
			   int k = ((Integer) random.select(indexes)).intValue();
			   indexes = new ArrayList<Integer>();
			   indexes.add(k);
			   Row selectedRow = rows.get(k);
			   selected = (String[]) selectedRow.toArray(new String[selectedRow.size()]);
			   if (exclusive) {
				   removeFromIndexes(indexes);
			   }
		   } else {
			   throw new RandomException();
		   }
	   }
	   return selected;
   }

   public String[] select(int columnPos) throws RandomException {
	   return select(columnPos, new String[] {}, ANY, new String[] {} );
   }

   public String[] select(int columnPos, String[] att) throws RandomException {
	   return select(columnPos, att, ANY, new String[] {} );
   }

   public String[] select(int columnPos, String att) throws RandomException {
	   return select(columnPos, new String[] {att}, ANY, new String[] {} );
   }

   // Mismas funciones pero usando nombres de las columnas
   public String[] select(String namePos, String[] attPos, String nameNeg, String[] attNeg) throws RandomException {
	   int columnPos = find(namePos);
	   int columnNeg = find(nameNeg);
	   return select(columnPos, attPos, columnNeg, attNeg);
   }

   public String[] select(String namePos, String[] att) throws RandomException {
	   return select(namePos, att, null, new String[] {} );
   }

   public String[] select(String namePos, String att) throws RandomException {
	   return select(namePos, new String[] {att}, null, new String[] {} );
   }

   // Busqueda en todas selectAll casilas
   public String[] select(String[] att) throws RandomException {
	   return select(ANY, att,  ANY, new String[] {} );
   }

   public String[] select(String att) throws RandomException {
	   return select(ANY, new String[] {att},  ANY, new String[] {} );
   }

   public String[] select() {
	   String[] aStr = null;
	   Row row = selectRow();
	   if (row!=null) {
		   aStr = (String[]) row.toArray(new String[row.size()]);
	   }
	   return aStr;
   }

   public Row selectRow(int columnPos, String[] attPos, int columnNeg, String[] attNeg) throws RandomException {
	   Row selectedRow = null;
	   List<Integer> indexes = selectIndexes(columnPos, attPos, ANY, columnNeg, attNeg , ANY);
	   if (indexes!=null && !indexes.isEmpty()) {
		   if (random!=null) {
			   int k = ((Integer) random.select(indexes)).intValue();
			   indexes = new ArrayList<Integer>();
			   indexes.add(k);
			   selectedRow = rows.get(k);
			   if (exclusive) {
				   removeFromIndexes(indexes);
			   }
		   } else {
			   throw new RandomException();
		   }
	   }
	   return selectedRow;
   }

   public Row selectRow(int columnPos) throws RandomException {
	   return selectRow(columnPos, new String[] {}, ANY, new String[] {} );
   }

   public Row selectRow(int columnPos, String[] att) throws RandomException {
	   return selectRow(columnPos, att, ANY, new String[] {} );
   }

   public Row selectRow(int columnPos, String att) throws RandomException {
	   return selectRow(columnPos, new String[] {att}, ANY, new String[] {} );
   }

   // Mismas funciones pero usando nombres de las columnas
   public Row selectRow(String namePos, String[] attPos, String nameNeg, String[] attNeg) throws RandomException {
	   int columnPos = find(namePos);
	   int columnNeg = find(nameNeg);
	   return selectRow(columnPos, attPos, columnNeg, attNeg);
   }

   public Row selectRow(String namePos, String[] att) throws RandomException {
	   return selectRow(namePos, att, null, new String[] {} );
   }

   public Row selectRow(String namePos, String att) throws RandomException {
	   return selectRow(namePos, new String[] {att}, null, new String[] {} );
   }

   // Busqueda en todas selectAll casilas
   public Row selectRow(String[] att) throws RandomException {
	   return selectRow(ANY, att,  ANY, new String[] {} );
   }

   public Row selectRow(String att) throws RandomException {
	   return selectRow(ANY, new String[] {att},  ANY, new String[] {} );
   }

   public Row selectRow() {
	   Row row= null;
	   if (rows!=null && !rows.isEmpty()) {
		   int x = random.nextInt(rows.size());
		   row = getRow(x) ;
		   if (exclusive) {
			   List<Integer> indexes = new ArrayList<Integer>();
			   indexes.add(x);
			   removeFromIndexes(indexes);
		   }
	   }
	   return row;
   }
   
   ///////////////////////////////////////////////////////////////////////////
   // Get ////////////////////////////////////////////////////////////////////
   ///////////////////////////////////////////////////////////////////////////
   public Row getRow(int i) {
	   Row row = null;
	   if (rows!=null && !rows.isEmpty() && 0<=i && i<rows.size()) {
		   row = rows.get(i);
	   }
	   return row;
   }

   public String[] get(int i) {
	   String[] aStr = null;
	   Row row = getRow(i);
	   if (row!=null) {
		   aStr = (String[]) row.toArray(new String[row.size()]);
	   }
	   return aStr;
   }

   public String get(int i, int j) {
	   String val = "";
	   String[] aStr = get(i);
	   if (aStr!=null && j<aStr.length) {
		   val = aStr[j];
	   }
	   return val;
   }

   public String get(String[] rowString, String name) {
	   String str = "";
	   int pos = find(name);
	   if (rowString!=null && 0<=pos && pos<rowString.length && rowString[pos]!=null) {
		   str = rowString[pos];
	   }
	   return str;
   }

   public String get(int i, String name) {
	   String[] aStr = get(i);
	   return get(aStr, name);
   }

   ///////////////////////////////////////////////////////////////////////////
   // GetAlternatives ////////////////////////////////////////////////////////
   ///////////////////////////////////////////////////////////////////////////
   public Set<String> getAlternativesSet(int j) {
	   HashSet<String> hs = new HashSet<String>();
	   if (rows!=null && !rows.isEmpty()) {
			for(int i=0; i<rows.size(); i++) {
				Row row = rows.get(i);
				if (row!=null && !row.isEmpty() && 0<=j && j<row.size()) {
					String value = (String) row.get(j);
					String[] split = value.split(OPTIONS_SEPARATOR);
					for(int k=0; k<split.length; k++) {
						if (!split[k].isEmpty()) {
							hs.add(split[k]);
						}
					}
				}
	   		}
	   }
	   return hs;
   }

   public Set<String> getAlternativesSet(String name) {
	   int j = find(name);
	   return getAlternativesSet(j);
   }

   public String[] getAlternatives(int j) {
	   HashSet<String> hs = (HashSet<String>) getAlternativesSet(j);
	   String[] aStr = hs.toArray(new String[hs.size()]);;
	   return aStr;
   }

   public String[] getAlternatives(String name) {
	   int j = find(name);
	   return getAlternatives(j);
   }
   
   /**
    * @return Number of columns in the table
    */
   public String[] getFirstRow() {
	   String[] stFirstRow = null;
	   if (firstRow!=null) {
		   stFirstRow = (String[]) firstRow.toArray(new String[firstRow.size()]);;
	   }
	   return stFirstRow;
   }
   

   /**
    * @return Find the position of a column in the table
    */
   public String find(int pos) {
	   String name = null;
	   if (pos!=ANY) {
		   if (firstRow==null || firstRow.isEmpty()) {
			   getFirstRow();
		   } 
		   if (firstRow!=null && pos<firstRow.size()) {
			   name = (String) firstRow.get(pos);
		   }
	   }
	   return name;
   }

   /**
    * Busca la posicion de la columna por el nombre de su titulo
    */
   public int find(String title) {
	   int pos = ANY;
	   if (title!=null && !title.equals("") && firstRow!=null) {
		   for (int i=0; i<firstRow.size(); i++) {
			   String field = (String)firstRow.get(i);
			   if (ignoreCase) {
				   title = title.toLowerCase();
				   field = field.toLowerCase();
			   } 
			   if (title.equals(field)) {
				   return i;
			   }
		   }
		   if (pos==ANY) { // No se ha encontrado, busca concidencia parcial
			   for (int i=0; i<firstRow.size(); i++) {
				   String field = (String)firstRow.get(i);
				   if (ignoreCase) {
					   title = Strings.filterHTMLtoTXT(title);
					   title = Strings.eliminarAcentos(title);
					   title = title.toLowerCase();
					   field = Strings.filterHTMLtoTXT(field);
					   field = Strings.eliminarAcentos(field);
					   field = field.toLowerCase();
				   } 
				   if (field.contains(title)) {
					   return i;
				   }
			   }
		   }
	   }
	   return pos;
   }

   /**
    * Devuelve todos los posibles valores de una columna por el nombre de su titulo
    * contenidos en una lista de filas. A partir de un ArrayList<Row>
    */
   public String[] allValues(ArrayList<Row> list, int pos) {
	   HashSet<String> hs = new HashSet<String>();
	   if (list!=null && pos!= ANY) {
		   for(int i=0; i<list.size(); i++) {
			   Row row = list.get(i);
			   String valor = (String) row.get(pos);
			   hs.add(valor);
		   }
	   }
	   String[] strings = hs.toArray(new String[hs.size()]);
	   return strings;
   }


   public String[] allValues(ArrayList<Row> list, String title) {
	   int pos = find(title);
	   return allValues(rows, pos);
   }

   /** 
    * Lo mismo que la anterior utilizando toda la tabla.
    * @param title
    * @return
    */
   public String[] allValues(String title) {
	   return allValues(rows, title);
   }
   
   
   /**
    * Convertir en una matriz de String
    */
   public static String[][] getTable(Set set) {
	   Row[] selectedRows = (Row[]) set.toArray(new Row[set.size()]);
	   String[][] selectedStringString = new String[set.size()][];
	   for(int i=0; i<set.size(); i++) {
		   selectedStringString[i] = (String[]) selectedRows[i].toArray(new String[selectedRows[i].size()]);
	   }
	   return selectedStringString;
   }

   /**
    * Convertir en una matriz de String
    */
   public static String[][] getTable(ArrayList<Row> list) {
	   Row[] selectedRows = list.toArray(new Row[list .size()]);
	   String[][] selectedStringString = new String[list.size()][];
	   for(int i=0; i<list.size(); i++) {
		   selectedStringString[i] = (String[]) selectedRows[i].toArray(new String[selectedRows[i].size()]);
	   }
	   return selectedStringString;
   }

   /**
    * Convertir en una matriz de String
    */
   public String[][] getTable() {
	   return TableCSV.getTable(rows);
   }

   /**
    * Volcado de la tabla (para depuracion)
    */
   public String toString() {
	   String st = "";
	   if (firstRow!=null && firstRow.size()>0) {
		   st += firstRow.get(0);
		   for(int i=1; i<firstRow.size(); i++) {
			   st += DEFAULT_SEPARATOR + firstRow.get(i);
		   }
		   st +="\n";
	   }
	   if (rows!=null) {
		   for(int k=0; k<rows.size(); k++) {
			   Row row = rows.get(k);
			   if (row!=null && row.size()>0) {
				   st += row.get(0);
				   for(int i=1; i<row.size(); i++) {
					   st += DEFAULT_SEPARATOR + row.get(i);
				   }
				   st+= "\n";
			   }
		   }
	   }
	   return st;
   }

   
   ///////////////////////////////////////////////////////////////////////////
   // Funciones estaticas auxiliares /////////////////////////////////////////
   ///////////////////////////////////////////////////////////////////////////


   /**
    * @see Strings
    */
   public static String cap(String st) {
	   return Strings.cap(st);
   }

   public static String low(String st) {
	   return Strings.low(st);
   }

   
   
   
   public static String toPattern(String in) {
	   return ExpresionRegular.toPattern(in);
   }
   public static String toPattern(String in, boolean escape) {
	   return ExpresionRegular.toPattern(in, escape);
   }
   public static String toPattern(String in, boolean escape, int patternAlign) {
	   return ExpresionRegular.toPattern(in, escape, patternAlign);
   }

   public static String toPattern(Set set, int mode) {
	   return ExpresionRegular.toPattern(set, mode);
   }

   public static String toPatternAll(Set set) {
	   return ExpresionRegular.toPatternAll(set);
   }

   public static String toPatternAny(Set set) {
	   return ExpresionRegular.toPatternAny(set);
   }

   public static String toPattern(String[] aString, int mode) {
       return ExpresionRegular.toPattern(aString, mode);
   }

   public static String toPatternAll(String[] aString) {
	   return ExpresionRegular.toPatternAll(aString);
   }

   public static String toPatternAny(String[] aString) {
	   return ExpresionRegular.toPatternAny(aString);
   }

   public static String toPattern(List aList, int mode) {
       return ExpresionRegular.toPattern(aList, mode);
   }

   public static String toPatternAll(List aList) {
	   return ExpresionRegular.toPatternAll(aList);
   }

   public static String toPatternAny(List aList) {
	   return ExpresionRegular.toPatternAny(aList);
   }


   public static String toText(Set set, int mode, String lang) {
	   return ExpresionRegular.toText(set, mode, lang);
   }

   public static String toTextAll(Set set, String lang) {
	   return ExpresionRegular.toTextAll(set, lang);
   }

 
   public static String toTextAny(Set set, String lang) {
	   return ExpresionRegular.toTextAny(set, lang);
   }

   public static String toText(String[] aString, int mode, String lang) {
       return ExpresionRegular.toText(aString, mode, lang);
   }

   public static String toTextAll(String[] aString, String lang) {
	   return ExpresionRegular.toTextAll(aString,lang );
   }

   public static String toTextAny(String[] aString, String lang) {
	   return ExpresionRegular.toTextAny(aString,lang);
   }

   public static String toTextAll(String s, String lang) {
	   String[] aString = s.split(OPTIONS_SEPARATOR);
	   return ExpresionRegular.toText(aString, ALL, lang);
   }
   public static String toTextAny(String s, String lang) {
	   String[] aString = s.split(OPTIONS_SEPARATOR);
	   return ExpresionRegular.toText(aString, ANY, lang);
   }
   
   
   // Hints
   
   public static String toHint(String in, String remove, String replace) {
	   String out = in.replaceAll("(?i)"+remove+"([^a-zA-Z])",replace+"$1");
	   return out;
   }
   
   public static String toHint(String in, Set<String> set0, String replace) {
	   final int MIN_LENGTH = 3;
	   String out = in;
	   if (in!=null && !in.equals("")) {
		   // Crear un conjunto ordenado en orden decreciente de longitud
		   Set<String> set = new TreeSet<String>(new Comparator() {
				public int compare(Object o1, Object o2) {
					String s1 = (String) o1;
					String s2 = (String) o2;
			        if (s1.length() == s2.length()) {
			            return s2.compareTo(s1);
			        } else {
			            return Integer.compare(s2.length(), s1.length());
			        }
			    }
			});
		   // fragmenta en palabras
		   for(String st : (Set<String>) set0) {
			    if (st!=null && st.length()>=MIN_LENGTH) {
			        String stx = toPattern(st);
			        String[] stSplit = stx.split(" ");
			        for(int i=0; i<stSplit.length; i++) {
			            if (stSplit[i]!=null && stSplit[i].length()>=MIN_LENGTH) {
			                set.add(stSplit[i]);
			            }
			        }
			    }
			}
		    for(String st : (Set<String>) set) {
		    		out = toHint(out,st,replace);
		   }
		}
	    return out;
   }
   
}
