# Processing sensor data of daily living activities

This is an application for analysing the behaviour of a person recorded by a set of sensors installed in its house. The historical log of the person’s activity is stored as tuples (start_time, end_time, activity_label), where start_time and end_time represent the date and time when each activity has started and ended while the activity label represents the type of activity performed by the person: Leaving, Toileting, Showering, Sleeping, Breakfast, Lunch, Dinner, Snack, Spare_Time/TV, Grooming. The data is spread over several days as many entries in the log Activities.txt.

## Usage
The application can be run with the following command :
```
java -jar DataProcessor.jar
```
The Activities.txt log is hardcoded as the read file so it has to be in the same folder as the .jar executable.

## Functionality

It can carry out a series of tasks for analysing this information using java streams:

•It can count the number of distinct days which appear in the monitoring period

•It can count how many times each activity has appeared over the entire monitoring period
 
•It can count for how many times each activity has appeared for each day over the monitoring period

•It can compute the entire duration over the monitoring period for each activity

•It can filter activities that have more than 90% of the monitoring records with a duration of less than 5 minutes


## Results

The application will generate .txt files for each task. If used with the .jar executable all tasks will be called and all 5 of the .txt files generated with the addition of a .txt file that contains all the information from Activities.txt parsed as to demonstrate the correct reading of the file.

![List](https://imgur.com/hoTm2dN.jpg)

![Task 3](https://imgur.com/ia3p41W.png)
![Task 5](https://imgur.com/PFj3FfQ.png)
