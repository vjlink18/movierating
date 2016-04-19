package edu.tamu.isys.ratings;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MovieMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		/*
		 * Convert the line, which is received as Text object, to a String
		 * object. Split each line by the delimiter "::" and split each genre by
		 * the delimiter ","
		 */
		try {
			String line = value.toString();
			String[] words = line.split("::");
			String[] genre = words[2].split(",");

			/*
			 * Check if movie name or genre or rating are empty. Emit genre as
			 * the key value and a string with the combination of
			 * moviename_rating as the value.
			 */
			if (!(words[1].isEmpty() || words[2].isEmpty() || words[6]
					.isEmpty())) {
				for (int i = 0; i < genre.length; i++) {
					String movieRating = "";
					movieRating = words[1] + "_" + words[6];
					context.write(new Text(genre[i]), new Text(movieRating));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}