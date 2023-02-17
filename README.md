Andy Do
CSC 369 Lab 3
Professor Migler

Part 1:
- Used 3 separate mapreduce jobs
- First one joined input_access_log and hostname_country.csv using reduce-join. I ended up using reduce-join since it felt more intuitive for me to implement as 
I could make separate mappers for each different input.
- The second mapreduce job took as input part1join and summed up the total request count for each country
- The third mapreduce job sorted our count of each country by using the inbuilt decreasingcomparator class

Part 2:
- Used 2 separate mapreduce jobs
- Similar to part1, the first one used reduce-join to print out our desired output which was the country, url, and count
- Created own WritableComparator called Composite key to handle our primary and secondary sorting which were Country and count
- In our second mapreduce job the value was used to store the country and url as a string for output later since it is not used in our sorting

Part 3:
- Used 2 separate mapreduce jobs
- Similar to part1, the first one used reduce-join to print out our desired output which was the url and country name
- Created own WritableComparator called part3UrlCountryPair to store the url and country name
- Second mapreduce job used part3CountrySort to get each country that visited said url and print it out, removing duplicate country names

