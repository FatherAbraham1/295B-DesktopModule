

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class InputProcessor {

	public static String fileType(String filepath) throws IOException {
		//get hdfs filepath. read filename. find out file format
		//return "txt";
		String[] fileSplit= filepath.split("\\.");
		return fileSplit[fileSplit.length-1];
	}
	
	public static Map<Integer,String> metadata(String filepath) throws IOException{
		// Call fileType and find out type of file
		// read the file accordingly and separate first row from others
		//if needed make a class for this output. i.e: return metadata separately and data separately
		BufferedReader br;
	    if (filepath.startsWith("hdfs://")) {
	    	Configuration configuration = new Configuration();
		    FileSystem fs = null;
	    	fs = FileSystem.get(configuration);
	    	Path path = new Path(filepath);
	    	br=new BufferedReader(new InputStreamReader(fs.open(path)));
	    } else {
	    	br = new BufferedReader(new FileReader(filepath));
	    }
		
        String line;
        line=br.readLine();
        String[] meta = line.split(",");
        Map<Integer,String> meta_out = new HashMap();
        int i=1;
        for(int j=0;j<meta.length;j++){
        	meta_out.put(i, (String)meta[j]);
        	i++;
        }
        br.close();
        return meta_out;
	}
	
	public static String excludeColumns(ArrayList<Integer> list,Map<Integer,String> hashmap){
		Iterator<Integer> itr = list.iterator();
        while(itr.hasNext()){
            hashmap.remove(itr.next());
        }
        return "Selected columns excluded.";
	}
	public static String includeColumns(ArrayList<Integer> list,Map<Integer,String> hashmap){
		Iterator<Integer> itr = list.iterator();
        while(itr.hasNext()){
        	// move the key,value pairs in another hashmap and then clone it to original
        	// will think about it later.
        }
        return "Only selected columns will be included.";
	}
	
	public static void convertToCSV(String filepath){
		//convert text or other formats to csv. some Algos might need data accordingly.
		//Less priority
	}
	
	public static void convertToText(String filepath){
		//convert csv or other formats to text. some Algos might need data accordingly.
		//Less priority
		//Also you might need to send to hdfs in this format. Not sure though.
	}
	
	public static boolean missingValueScrubber(String filepath,String attribute,String missingValue, float value) throws Exception{
	//replace with 0,mean,median,mode or remove entire row, custom value substitute
		
		BufferedReader br;
	    if (filepath.startsWith("hdfs://")) {
	    	Configuration configuration = new Configuration();
		    FileSystem fs = null;
	    	fs = FileSystem.get(configuration);
	    	Path path = new Path(filepath);
	    	br=new BufferedReader(new InputStreamReader(fs.open(path)));
	    } else {
	    	br = new BufferedReader(new FileReader(filepath));
	    }
		
        String line;
        line=br.readLine(); // Ignore assuming first one is metadata
        
		if(attribute=="MEAN"){ //average value
			while ((line = br.readLine()) != null) {
	        	String[] row = line.split(",");
	        	int mean=0;
	        	int count=0;
	        	for(int i=0;i<row.length;i++){
	        		if(!row[i].equalsIgnoreCase(missingValue)){
	        			count++;
	        			mean+= Integer.parseInt(row[i]);
	        		}
	        	}
	        	mean = mean/count;
	        	// NOW REPLACE BLANK VALUE WITH MEAN
	        	for(int i=0;i<row.length;i++){
	        		if(row[i].equalsIgnoreCase(missingValue)){
	        			row[i]=mean+"";
	        		}
	        	}
	        	return true;
			}
		} else if(attribute=="MEDIAN"){ // middle value
			while ((line = br.readLine()) != null) {
	        	String[] row = line.split(",");
	        	for(int i=0;i<row.length;i++){
	        		
	        	}
			}
		} else if(attribute=="MODE"){ // most frequently occuring value
			while ((line = br.readLine()) != null) {
	        	String[] row = line.split(",");
	        	for(int i=0;i<row.length;i++){
	        		
	        	}
			}
		} else if(attribute=="REMOVE_ROW"){
			while ((line = br.readLine()) != null) {
	        	String[] row = line.split(",");
	        	for(int i=0;i<row.length;i++){
	        		
	        	}
			}
		} else if(attribute=="CUSTOM_VALUE"){
			while ((line = br.readLine()) != null) {
	        	String[] row = line.split(",");
	        	for(int i=0;i<row.length;i++){
	        		
	        	}
			}
		}
		
        
        br.close();
        return true;
		
	}
	
}
