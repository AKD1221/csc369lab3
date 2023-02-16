package csc369;

import static org.mockito.Answers.values;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class part1 {

    public static final Class OUTPUT_KEY_CLASS = Text.class;
    public static final Class OUTPUT_VALUE_CLASS = IntWritable.class;


    public static class CountryMapper extends Mapper <Object, Text, Text, Text>
    {
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            String mystring = value.toString();
            String[] parts = mystring.split(",");
            context.write(new Text(parts[0]), new Text("country    " + parts[1]));
        }
    }

    public static class AddressMapper extends Mapper <Object, Text, Text, Text>
    {
        private final IntWritable one = new IntWritable(1);
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            
            String mystring = value.toString();
            String[] parts = mystring.split(" ");
            context.write(new Text(parts[0]), new Text("address    " + one));
        }
    }

    public static class ReducerImpl extends Reducer<Text, Text, Text, Text> {
	private IntWritable result = new IntWritable();
    
        @Override
	protected void reduce(Text hostname, Iterable<Text> values,
			      Context context) throws IOException, InterruptedException {
            
            String name = "";
            int count = 0;
            for (Text t : values)
            {
                String parts[] = t.toString().split("  ");
                if (parts[0].equals("country"))
                {
                    name = parts[0];
                }
                else if (parts[0].equals("address"))
                {
                    count++;
                }
            }
            context.write(new Text(name), new Text(Integer.toString(count)));
       }
    }

}
