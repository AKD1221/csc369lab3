package csc369;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class CompositeKey implements WritableComparable<CompositeKey> {

  private Text country;
  private IntWritable count;

  public CompositeKey() {
    this.country = new Text();
    this.count = new IntWritable();
  }

  public CompositeKey(Text Country, IntWritable Count) {
    this.setCountry(Country);
    this.setCount(Count);
}

  public void setCountry(Text country) {
    this.country = country;
  }

  public void setCount(IntWritable count) {
    this.count = count;
  }

  public Text getCountry() {
    return this.country;
  }

  public IntWritable getCount() {
    return this.count;
  }

  public void readFields(DataInput in) throws IOException {
    country.readFields(in);
    count.readFields(in);
  }

  public void write(DataOutput out) throws IOException {
    country.write(out);
    count.write(out);
  }

  public int compareTo(CompositeKey other) {
    int result = this.country.compareTo(other.country);
    if (result == 0) {
      result = count.compareTo(other.count);
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CompositeKey other = (CompositeKey) obj;
    if (country == null) {
      if (other.country != null)
        return false;
    } else if (!country.equals(other.country))
      return false;
    if (count != other.count)
      return false;
    return true;
  }
}
