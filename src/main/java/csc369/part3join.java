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

public class part3join {

    public static final Class OUTPUT_KEY_CLASS = Text.class;
    public static final Class OUTPUT_VALUE_CLASS = Text.class;

    public static class AddressMapper extends Mapper <Text, Text, Text, Text>
    {
        public void map(Text key, Text value, Context context) throws IOException, InterruptedException
        {
            String mystring = key.toString();
            String[] parts = mystring.split(" ");
            context.write(new Text(parts[0]), new Text("address\t" + parts[6]));
        }
    }

    public static class CountryMapper extends Mapper <LongWritable, Text, Text, Text>
    {
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String parts[] = value.toString().split(",");
            if (parts.length == 2)
            {
                String ip = parts[0];
                String name = parts[1];
                context.write(new Text(ip), new Text("country\t" + name));
            }
        }
    }

    public static class ReducerImpl extends Reducer<Text, Text, Text, Text> {
        @Override
	protected void reduce(Text key, Iterable<Text> values,
			      Context context) throws IOException, InterruptedException {
            
            String name1 = "temp";
            String name2 = "temp2";
            int count = 0;
            for (Text t : values)
            {
                String parts[] = t.toString().split("\t");
                if (parts[0].equals("country"))
                {
                    name1 = parts[1];
                }
                else if (parts[0].equals("address"))
                {
                    name2 = parts[1];
                    count++;
                }
            }
            context.write(new Text(name2), new Text(name1));
       }
    }

}
