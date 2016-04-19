package edu.tamu.isys.ratings;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
 The following is the code for the driver class 
 */
public class Program {

	public static void main(String[] args) throws Exception {
		/*
		 * Validate that two arguments were passed from the command line.
		 */
		try {
			if (args.length != 2) {
				System.out
				.printf("Usage: movierating <input dir> <output dir>\n");
				System.exit(-1);
			}
			/*
			 * Instantiate a Job object for the job's configuration.
			 */
			Configuration conf = new Configuration();
			Job job = Job.getInstance(conf, "Movie Ratings");

			/*
			 * Specify the jar file that contains driver, mapper, and reducer.
			 * Hadoop will transfer this jar file to nodes in the cluster
			 * running mapper and reducer tasks.
			 */
			job.setJarByClass(Program.class);
			job.setMapperClass(MovieMapper.class);
			job.setReducerClass(RatingReducer.class);

			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(Text.class);

			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(DoubleWritable.class);

			/*
			 * Specify the paths to the input and output data based on the
			 * command-line arguments.
			 */
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			boolean success = job.waitForCompletion(true);
			System.exit(success ? 0 : 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
