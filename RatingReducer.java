package edu.tamu.isys.ratings;

import java.io.IOException;
import java.util.HashMap;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RatingReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		try {
			String max_key = "";
			double max_value = 0;
			String rating = "";
			String maxVal = "";

			/*
			 * Initialize HashMap movieList to store key as movie name and value
			 * as a string of all its ratings. Initialize HashMap finalList to
			 * store key as the Genre and the value as a string of movie name
			 * and average ratings
			 */
			HashMap<String, String> movieList = new HashMap<String, String>();
			HashMap<String, Double> finalList = new HashMap<String, Double>();

			/*
			 * For each value in the set of values passed to us by the mapper:
			 */
			for (Text val : values) {
				/*
				 * Convert the value, which is received as Text object, to a
				 * String object. Split each line by the delimiter "_"
				 */
				String line = val.toString();
				String[] line_values = line.split("_");

				/*
				 * Check if the movieList contains the movie name
				 */
				if (movieList.containsKey(line_values[0])) {
					rating = movieList.get(line_values[0]);
					rating = rating + "," + line_values[1];
					movieList.remove(line_values[0]);
					movieList.put(line_values[0], rating);
				} else {
					movieList.put(line_values[0], line_values[1]);
				}
			}

			for (String hashkey : movieList.keySet()) {
				String ratings = movieList.get(hashkey);

				double sum = (double) 0;

				String[] rating_values = ratings.split(",");

				for (int i = 0; i < rating_values.length; i++) {
					sum += Double.parseDouble(rating_values[i]);
				}
				double result = (sum / rating_values.length);

				finalList.put(hashkey, result);
			}

			for (String finalKey : finalList.keySet()) {
				if (finalList.get(finalKey) > max_value) {
					max_value = finalList.get(finalKey);
					max_key = finalKey;
					/*
					 * To get the ratings precision up to 2 decimal places.
					 */
					maxVal = Double.toString(max_value) + "0000";
					maxVal = maxVal.substring(0, 4);
				}
			}
			context.write(new Text(key + " " + max_key), new Text("("+maxVal+")"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}