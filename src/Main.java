import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;



public class Main {

	private static final RealMatrix COVARIANCE = null;

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
        exclude.add(591);
        exclude.add(592);
        
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
		else if (algorithmType.equalsIgnoreCase("PCA"))
		{	
			System.out.println("pca selected");
			
			/**
		     * Performs principal component analysis on the data in the specified matrix.  Each 
		     * row of the matrix is considered to be a complete data sample or record.  The number of columns
		     * (row length) of the data is the number of measurements in each sample.
		     * 
		     * @param data - a matrix containing the data.
		     * @param covType - determines whether the eigenvalue decomposition is performed on the
		     *   covariance matrix or the correlation matrix.  Allowed values are <tt>CovarianceType.COVARIANCE</tt>
		     *   or <tt>CovarianceType.CORRELATION</tt>.
		     * @param level - a threshold in the interval (0 - 1] used to determine how much to reduce the
		     *   dimensionality of the data matrix.  The higher the threshold, the more the dimensionality is
		     *   reduced.  
		     */

		    int mPCAIndices = -1;
		    double mLevel=0;
			
			InputProcessor.convertTo2dArray("data/SECOM.csv");
		
		    
			//PcaAlgorithm(RealMatrix data, CovarianceType covType, int indices)
			//PcaAlgorithm  pca = new PcaAlgorithm(matrix);
			
			//PcaAlgorithm(RealMatrix data, CovarianceType covType, double level)
			
			
		}
		else {
			System.out.println("Error: "+algorithmType+ ". No such Algorithm in the library!");
		}
		
		
		
	}
	} // if args ends
}
