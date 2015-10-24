

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MahoutLib {

	public static void PearsonCorrelationSimilarity(String filepath) throws IOException, TasteException{
		try {
			DataModel model = new FileDataModel(new File(filepath)); //args[0]
			//ItemSimilarity item = new LogLikelihoodSimilarity(model);
			//TanimotoCoefficientSimilarity item = new TanimotoCoefficientSimilarity(model);
			
			
			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
			
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		for(LongPrimitiveIterator prim = model.getItemIDs(); prim.hasNext();){
			long userid = prim.nextLong();
			List<RecommendedItem>recommendations =  recommender.recommend(userid, 3);
			int x=1;
			for (RecommendedItem recommendation : recommendations) {
				//System.out.println(recommendation);
				System.out.println("USERID: "+userid+" Recommended Movie: #"+x+": "+recommendation.getItemID()+" Predicted User Rating: "+recommendation.getValue());
				x++;
			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("There was an error.");
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			System.out.println("There was a taste error.");
			e.printStackTrace();
		}
	
  }
}
