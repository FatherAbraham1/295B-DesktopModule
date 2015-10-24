import scala.Tuple2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.Function;

import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SparkLib {
	private static final Pattern SPACE = Pattern.compile(" ");
	public static void JavaWordCount(String filepath) {
		  
			if (filepath==null) {
		      System.err.println("Usage: JavaWordCount <file>");
		      System.exit(1);
		    }

		    SparkConf sparkConf = new SparkConf().setAppName("JavaWordCount");
		    JavaSparkContext ctx = new JavaSparkContext(sparkConf);
		    JavaRDD<String> lines = ctx.textFile(filepath, 1);

		    JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
		      @Override
		      public Iterable<String> call(String s) {
		        return Arrays.asList(SPACE.split(s));
		      }
		    });

		    JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
		      @Override
		      public Tuple2<String, Integer> call(String s) {
		        return new Tuple2<String, Integer>(s, 1);
		      }
		    });

		    JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
		      @Override
		      public Integer call(Integer i1, Integer i2) {
		        return i1 + i2;
		      }
		    });

		    List<Tuple2<String, Integer>> output = counts.collect();
		    for (Tuple2<?,?> tuple : output) {
		      System.out.println(tuple._1() + ": " + tuple._2());
		    }
		    ctx.stop();
		}
 /* **************     kmeans    ******************** */
private static class ParsePoint implements Function<String, Vector> {
	    private static final Pattern SPACE = Pattern.compile(" ");

	    @Override
	    public Vector call(String line) {
	      String[] tok = SPACE.split(line);
	      double[] point = new double[tok.length];
	      for (int i = 0; i < tok.length; ++i) {
	        point[i] = Double.parseDouble(tok[i]);
	      }
	      return Vectors.dense(point);
	    }
}
public static void JavaKMeans(String filepath, int k, int iterations, int runs) {

		  
		    if (filepath==null) {
		      System.err.println(
		        "Usage: JavaKMeans <input_file> <k> <max_iterations> [<runs>]");
		      System.exit(1);
		    }
		    String inputFile = filepath;
		    //int k = Integer.parseInt(args[1]);
		    //int iterations = Integer.parseInt(args[2]);
		    //int runs = 1;
		    
		    // will need to make runs as optional parameter. Think later.
		    //runs=1;
		    //if (args.length >= 4) {
		    //  runs = Integer.parseInt(args[3]);
		    //}
		    SparkConf sparkConf = new SparkConf().setAppName("JavaKMeans").setMaster("local[2]").set("spark.executor.memory","1g");
		    JavaSparkContext sc = new JavaSparkContext(sparkConf);
		    JavaRDD<String> lines = sc.textFile(inputFile);

		    JavaRDD<Vector> points = lines.map(new ParsePoint());

		    KMeansModel model = KMeans.train(points.rdd(), k, iterations, runs, KMeans.K_MEANS_PARALLEL());

		    System.out.println("Cluster centers:");
		    for (Vector center : model.clusterCenters()) {
		      System.out.println(" " + center);
		    }
		    double cost = model.computeCost(points.rdd());
		    System.out.println("Cost: " + cost);

		    sc.stop();
}
	
	
	
/* K means end */	
	
	
}
