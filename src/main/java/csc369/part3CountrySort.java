package csc369;

import static org.mockito.Mockito.timeout;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Partitioner;

public class part3CountrySort {

    public static final Class OUTPUT_KEY_CLASS = part3UrlCountryPair.class;
    public static final Class OUTPUT_VALUE_CLASS = Text.class;

    public static class MapperImpl extends Mapper<LongWritable, Text, part3UrlCountryPair, Text> {
          @Override
          public void map(LongWritable key, Text value, Context
                          context) throws IOException, InterruptedException {
              String line = value.toString();
              String[] tokens = line.split("\t");
              context.write(new part3UrlCountryPair(tokens[0], tokens[1].trim()), new Text(tokens[1].trim()));
          }
    }
    

    public static class PartitionerImpl extends Partitioner<part3UrlCountryPair, IntWritable> {
        @Override
        public int getPartition(part3UrlCountryPair pair,
                                IntWritable temperature,
                                int numberOfPartitions) {
            return Math.abs(pair.geturl().hashCode() % numberOfPartitions);
        }
    }
    
    public static class GroupingComparator extends WritableComparator {
        public GroupingComparator() {
            super(part3UrlCountryPair.class, true);
        }
        
        @Override
        public int compare(WritableComparable wc1,
                           WritableComparable wc2) {
            part3UrlCountryPair pair = (part3UrlCountryPair) wc1;
            part3UrlCountryPair pair2 = (part3UrlCountryPair) wc2;
            return pair.geturl().compareTo(pair2.geturl());
        }
    }

    public static class SortComparator extends WritableComparator {
        protected SortComparator() {
            super(part3UrlCountryPair.class, true);
        }
        
        @Override
        public int compare(WritableComparable wc1,
                           WritableComparable wc2) {
            part3UrlCountryPair pair = (part3UrlCountryPair) wc1;
            part3UrlCountryPair pair2 = (part3UrlCountryPair) wc2;
            return pair.compareTo(pair2);
        }
    }
    
    public static class ReducerImpl extends Reducer<part3UrlCountryPair, Text, Text, Text> {

            @Override
            protected void reduce(part3UrlCountryPair key,
                                  Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
                String result="";
                for (Text value : values) {
                    String test[] = result.split(",");
                    Boolean isin = false;
                    for (String temp : test)
                    {
                        if (value.toString().equals(temp))
                        {
                            isin = true;
                        }
                    }
                    if (isin.equals(false))
                    {
                    result += (value.toString()+",");
                    }
                }
                result = result.substring(0, result.length()-1);
                context.write(key.geturl(), new Text(result));
            }
    }

}