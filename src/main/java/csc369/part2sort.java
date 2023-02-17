package csc369;
import static org.mockito.Answers.values;

import csc369.CompositeKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

public class part2sort {

  public static class SecondarySortingMapper extends Mapper<Object, Text, CompositeKey, Text> {


    public void map(Object inputKey, Text inputValue, Context context) throws IOException, InterruptedException {

        String[] tokens = inputValue.toString().split("\t");

        String id = tokens[0];

        String anotherid[] = id.split("/");
        String myid = anotherid[0];

        String value = tokens[1];

        context.write(new CompositeKey(new Text(myid), new IntWritable(Integer.parseInt(value))), new Text(tokens[0]));
    }
  }

  public static class SecondarySortingPartitioner extends Partitioner<CompositeKey, Text> {

    private HashPartitioner<Text, Text> hashPartitioner = new HashPartitioner<Text, Text>();

    @Override
    public int getPartition(CompositeKey key, Text value, int numPartitions) {

        System.out.println(key.getCount());

        int myhash = key.getCountry().hashCode();

      return Math.abs(myhash % numPartitions);
    }
  }

  public static class SecondarySortingGroupingComparator extends WritableComparator {

    protected SecondarySortingGroupingComparator() {
        super(CompositeKey.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {
        CompositeKey key1 = (CompositeKey) w1;
        CompositeKey key2 = (CompositeKey) w2;

        
        return key1.getCountry().compareTo(key2.getCountry());
    }
  }

  public static class SecondarySortingReducer extends Reducer<CompositeKey, Text, Text, IntWritable> {

    public void reduce(CompositeKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text value : values)
        {
            context.write(new Text(value), key.getCount());
        }
    }
    }
    
}

