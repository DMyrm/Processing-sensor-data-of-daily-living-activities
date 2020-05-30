package main.java;

import main.java.MonitoredData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.File;

public class Start
{
    public static void main(String[] args)
    {
          ActionProcessing actionProcessing = new ActionProcessing();
        try
        {
            FileInputStream fis = new FileInputStream("Activities.txt");
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            br.lines().forEach(line -> {
                actionProcessing.addData(line);
            });
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

//        String line1 = "2011-11-28 02:27:59 2011-11-28 10:18:11 Sleeping";
//        String line2 = "2011-11-29 02:16:00 2011-11-29 11:31:00 Sleeping";
//        String line3 = "2011-11-30 20:14:03 2011-12-01 01:34:27 Spare_Time/TV";
//        String line4 = "2011-11-28 10:21:24 2011-11-28 10:23:36 Toileting";
//        actionProcessing.addData(line1);
//        actionProcessing.addData(line2);
//        actionProcessing.addData(line3);
//        actionProcessing.addData(line4);
//
        actionProcessing.printActivities();
        System.out.println(actionProcessing.dateCount() + "\n");
        System.out.println(actionProcessing.activityCount()+ "\n");
        System.out.println(actionProcessing.timeOfActivity()+ "\n");
        System.out.println(actionProcessing.activityFilter()+ "\n");
        System.out.println(actionProcessing.dailyActivityCount()+ "\n");
    }
}
