package main.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoredData
{
    public Date start_time;
    public Date end_time;
    public String activity_label;

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getActivity_label() {
        return activity_label;
    }

    public void setActivity_label(String activity_label) {
        this.activity_label = activity_label;
    }

    @Override
    public String toString() {
        return "{" +
                "start_time=" + start_time +
                ", end_time=" + end_time +
                ", activity_label='" + activity_label + '\'' +
                '}';
    }

    public void parseAction(String line)
   {
       String[] parts = line.split("\t");
       try
       {
           start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parts[0]+" "+parts[1]);
           end_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parts[2]+" "+parts[3]);
       } catch (ParseException e)
       {
           e.printStackTrace();
       }
       activity_label = parts[4];
   }


}
