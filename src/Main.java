import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String filetype = InputProcessor.fileType("data/SECOM.csv");
		System.out.println(filetype);
		Map<Integer,String> metadata = InputProcessor.metadata("data/SECOM.csv");
		
        Set<Integer> keys = metadata.keySet();
        for(int key: keys){
            System.out.println("K: "+key+" V: "+metadata.get(key));
        }
        
        ArrayList<Integer> exclude=new ArrayList();
        exclude.add(1);
        exclude.add(3);
        exclude.add(5);
        exclude.add(7);
        exclude.add(588);
        exclude.add(585);
        exclude.add(580);
        
        String excludeColumns= InputProcessor.excludeColumns(exclude, metadata);
        
        System.out.println(excludeColumns);
        
        Set<Integer> new_keys = metadata.keySet();
        for(int key: new_keys){
            System.out.println("K: "+key+" V: "+metadata.get(key));
        }
        
        
		// 1st argument is the algorithm name, 2nd one is file path & rest are parameters
if(args.length!=0){
		String algorithmType = "";
		String[] params = new String[args.length-1];
		int check=1;
		int i=0;
		String filepath= "";
		for (String s: args){
			if(check==1){
				algorithmType=s;
				check++;
			} else if (check==2){
				filepath=s;
				check=0;
			}
			else{
				params[i]=s;
				i++;
			}
		}
		
		if(algorithmType.equalsIgnoreCase("PearsonCorrelationSimilarity")){
			try{
				MahoutLib.PearsonCorrelationSimilarity(filepath); //"data/ratings.csv"
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(algorithmType.equalsIgnoreCase("JavaWordCount")){
				SparkLib.JavaWordCount(filepath);
		}
		else if(algorithmType.equalsIgnoreCase("JavaKMeans")){
				SparkLib.JavaKMeans(filepath, 2, 2, 2);
		}
		else {
			System.out.println("Error: "+algorithmType+ ". No such Algorithm in the library!");
		}
		
		
		
	}
	} // if args ends
}
