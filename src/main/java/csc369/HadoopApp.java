package csc369;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import csc369.part2sort.SecondarySortingGroupingComparator;
import csc369.part2sort.SecondarySortingMapper;
import csc369.part2sort.SecondarySortingPartitioner;
import csc369.part2sort.SecondarySortingReducer;

import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;


public class HadoopApp {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Hadoop example");
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

	if (otherArgs.length < 3) 
	{
	    System.out.println("Expected parameters: <job class> <input dir> <output dir>");
	    System.exit(-1);
	} 
	else if ("WordCount".equalsIgnoreCase(otherArgs[0])) 
	{
	    job.setReducerClass(WordCount.ReducerImpl.class);
	    job.setMapperClass(WordCount.MapperImpl.class);
	    job.setOutputKeyClass(WordCount.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(WordCount.OUTPUT_VALUE_CLASS);
	} 
	else if ("AccessLog".equalsIgnoreCase(otherArgs[0])) 
	{
	    job.setReducerClass(AccessLog.ReducerImpl.class);
	    job.setMapperClass(AccessLog.MapperImpl.class);
	    job.setOutputKeyClass(AccessLog.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(AccessLog.OUTPUT_VALUE_CLASS);

		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
    } 
	else if ("AccessLog2".equalsIgnoreCase(otherArgs[0])) 
	{
	    job.setReducerClass(AccessLog2.ReducerImpl.class);
	    job.setMapperClass(AccessLog2.MapperImpl.class);
	    job.setOutputKeyClass(AccessLog2.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(AccessLog2.OUTPUT_VALUE_CLASS);

		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	} 
	else if ("AccessLog2reverse".equalsIgnoreCase(otherArgs[0])) 
	{
	    job.setReducerClass(AccessLog2.ReducerImpl.class);
	    job.setMapperClass(AccessLog2.MapperImpl.class);
	    job.setOutputKeyClass(AccessLog2.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(AccessLog2.OUTPUT_VALUE_CLASS);
		job.setSortComparatorClass(LongWritable.DecreasingComparator.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	} 
	else if ("part1join".equalsIgnoreCase(otherArgs[0])) 
	{

		MultipleInputs.addInputPath(job, new Path(otherArgs[1]),KeyValueTextInputFormat.class, part1join.AddressMapper.class);
		MultipleInputs.addInputPath(job, new Path(otherArgs[2]),TextInputFormat.class, part1join.CountryMapper.class);

		job.setJarByClass(part1join.class);
	    job.setReducerClass(part1join.ReducerImpl.class);
	    job.setOutputKeyClass(part1join.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(part1join.OUTPUT_VALUE_CLASS);

		FileOutputFormat.setOutputPath(job, new Path(otherArgs[3]));
    }
	else if ("part1sum".equalsIgnoreCase(otherArgs[0])) 
	{
	    job.setReducerClass(part1sum.ReducerImpl.class);
	    job.setMapperClass(part1sum.MapperImpl.class);
	    job.setOutputKeyClass(part1sum.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(part1sum.OUTPUT_VALUE_CLASS);

		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	}
	else if ("part2join".equalsIgnoreCase(otherArgs[0])) 
	{

		MultipleInputs.addInputPath(job, new Path(otherArgs[1]),KeyValueTextInputFormat.class, part2join.AddressMapper.class);
		MultipleInputs.addInputPath(job, new Path(otherArgs[2]),TextInputFormat.class, part2join.CountryMapper.class);

		job.setJarByClass(part2join.class);
	    job.setReducerClass(part2join.ReducerImpl.class);
	    job.setOutputKeyClass(part2join.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(part2join.OUTPUT_VALUE_CLASS);

		FileOutputFormat.setOutputPath(job, new Path(otherArgs[3]));
    }
	else if ("part2sort".equalsIgnoreCase(otherArgs[0])) 
	{
		job.setJarByClass(part2sort.class);

		job.setPartitionerClass(SecondarySortingPartitioner.class);
		job.setGroupingComparatorClass(SecondarySortingGroupingComparator.class);

	    job.setReducerClass(SecondarySortingReducer.class);
	    job.setMapperClass(SecondarySortingMapper.class);
	    job.setOutputKeyClass(CompositeKey.class);
	    job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	}
	else if ("part3join".equalsIgnoreCase(otherArgs[0])) 
	{

		MultipleInputs.addInputPath(job, new Path(otherArgs[1]),KeyValueTextInputFormat.class, part3join.AddressMapper.class);
		MultipleInputs.addInputPath(job, new Path(otherArgs[2]),TextInputFormat.class, part3join.CountryMapper.class);

		job.setJarByClass(part3join.class);
	    job.setReducerClass(part3join.ReducerImpl.class);
	    job.setOutputKeyClass(part3join.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(part3join.OUTPUT_VALUE_CLASS);

		FileOutputFormat.setOutputPath(job, new Path(otherArgs[3]));
    }
	else if ("part3sort".equalsIgnoreCase(otherArgs[0])) 
	{
		job.setJarByClass(part3CountrySort.class);

	    job.setReducerClass(part3CountrySort.ReducerImpl.class);
	    job.setMapperClass(part3CountrySort.MapperImpl.class);

		job.setPartitionerClass(part3CountrySort.PartitionerImpl.class);
		job.setGroupingComparatorClass(part3CountrySort.GroupingComparator.class);
		job.setSortComparatorClass(part3CountrySort.SortComparator.class);

	    job.setOutputKeyClass(part3CountrySort.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(part3CountrySort.OUTPUT_VALUE_CLASS);

		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	}

	else {
	    System.out.println("Unrecognized job: " + otherArgs[0]);
	    System.exit(-1);
	}

        //FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
        //FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));

        System.exit(job.waitForCompletion(true) ? 0: 1);
    }

}
