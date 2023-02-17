package csc369;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import org.apache.hadoop.io.*;

public class part3UrlCountryPair
    implements Writable, WritableComparable<part3UrlCountryPair> {
    
    private final Text url = new Text();
    private final Text country = new Text();
    
    public part3UrlCountryPair() {
    }
    
    public part3UrlCountryPair(String url, String country) {
        this.url.set(url);
        this.country.set(country);
    }
    
    @Override
    public void write(DataOutput out) throws IOException{
        url.write(out);
        country.write(out);
    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        url.readFields(in);
        country.readFields(in);
    }
    
    @Override
    public int compareTo(part3UrlCountryPair pair) {
        if (url.compareTo(pair.geturl()) == 0) {
            return country.compareTo(pair.country);
        }
        return url.compareTo(pair.geturl());
    }
    
    public Text geturl() {
        return url;
    }
    
    public Text getcountry() {
        return country;
    }
    
}