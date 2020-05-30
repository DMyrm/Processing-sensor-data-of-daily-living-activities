package main.java;

import main.java.MonitoredData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class ActionProcessing
{
    ArrayList<MonitoredData> monitoredData = new ArrayList<>();

    public void addData(String line)
    {
        MonitoredData newData = new MonitoredData();
        newData.parseAction(line);
        monitoredData.add(newData);
    }

    public void printActivities()
    {
        File file = new File("Task_1.txt");
        String s = monitoredData.stream().map(e -> e.getStart_time().toString() + " " +
                e.getEnd_time().toString() + " " +
                e.getActivity_label() + "\n").reduce("", String::concat);

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long dateCount()
    {
        ArrayList<Date> days = new ArrayList<Date>();
        for(MonitoredData d : monitoredData)
        {
            days.add(d.start_time);
            days.add(d.end_time);
        }

        ArrayList<Date> aux = new ArrayList<Date>(days.size());

        for(Date date : days)
        {
            Date auxDate = new Date(date.getTime());
            aux.add(auxDate);
        }

        for(Date d : aux)
        {
            d.setHours(0);
            d.setMinutes(0);
            d.setSeconds(0);
        }

        File file = new File("Task_2.txt");

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(String.valueOf(aux.stream().distinct().count()));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return aux.stream()
                .distinct()
                .count();
    }

    public Map<String,Integer> activityCount()
    {
        Map<String,Integer> activityCounter = new HashMap<String, Integer>();
        ArrayList<String> activities = new ArrayList<String>();
        for(MonitoredData d : monitoredData)
        {
            activities.add(d.activity_label);
        }
        String[] distinctActivities = activities.stream().distinct().toArray(String[]::new);
        for(String activ : distinctActivities)
        {
            Integer count = toIntExact(monitoredData.stream()
                    .filter(s -> s.activity_label.equals(activ))
                    .count());
            activityCounter.put(activ,count);
        }
        File file = new File("Task_3.txt");

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            activityCounter.forEach( (activity, count) ->
            {
                try
                {
                    writer.write(activity + " = " + count + "\n");
                } catch (IOException e)
                {
                        e.printStackTrace();
                }
            });
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activityCounter;
    }

    public Map<Integer,Map<String,Integer>> dailyActivityCount()
    {
        Map<Integer,Map<String,Integer>> dailyActivityCounter = new HashMap<Integer,Map<String,Integer>>();

        //ArrayList<MonitoredData> auxiliaryData = new ArrayList<>();

//        for(MonitoredData data : monitoredData)
//        {
//            MonitoredData auxData = new MonitoredData();
//            auxData.setActivity_label(data.getActivity_label());
//            auxData.setStart_time(data.getStart_time());
//            auxData.setEnd_time(data.getEnd_time());
//            auxiliaryData.add(auxData);
//        }

        ArrayList<Date> days = new ArrayList<Date>();
        for(MonitoredData d : monitoredData)
        {
            days.add(d.start_time);
            days.add(d.end_time);
        }

//        ArrayList<Date> aux = new ArrayList<Date>();
//
//        for(Date date : days)
//        {
//            Date auxDate = new Date(date.getTime());
//            aux.add(auxDate);
//        }

        for(Date d : days)
        {
            d.setHours(0);
            d.setMinutes(0);
            d.setSeconds(0);
        }

        //auxiliaryData.forEach(d -> System.out.println(d.toString()));

        Date[] differentDays = days.stream()
                .distinct()
                .toArray(Date[]::new);

        ArrayList<String> activities = new ArrayList<String>();
        for(MonitoredData d : monitoredData)
        {
            activities.add(d.activity_label);
        }
        String[] distinctActivities = activities.stream()
                .distinct()
                .toArray(String[]::new);

        int counter = 1;
        for(Date d : differentDays)
        {
            Map<String,Integer> todayCount = new HashMap<String, Integer>();
            for(String activ : distinctActivities)
            {
                Integer count = toIntExact(monitoredData.stream().filter(s -> s.activity_label.equals(activ)&&
                        (s.start_time.compareTo(d) == 0 ||s.end_time.compareTo(d) == 0)).count());
                todayCount.put(activ,count);
            }
            dailyActivityCounter.put(counter,todayCount);
            counter++;
        }

        File file = new File("Task_4.txt");

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            dailyActivityCounter.forEach( (dayNo, map) ->
            {
                map.forEach((action,times) ->
                {
                    try
                    {
                        writer.write(dayNo + " => " + action + " = " + times + "\n");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });

            });
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        days.clear();
//        for(Date auxiliary : aux)
//        {
//            //System.out.println(auxiliary.toString());
//            Date auxDate = new Date(auxiliary.getTime());
//            days.add(auxDate);
//
//        }
        return dailyActivityCounter;
    }

    public Map<String, LocalTime> timeOfActivity()
    {
        Map<String, LocalTime> activityTimes = new HashMap<String, LocalTime>();

        ArrayList<String> activities = new ArrayList<String>();
        for(MonitoredData d : monitoredData)
        {
            activities.add(d.activity_label);
        }
        String[] distinctActivities = activities.stream()
                .distinct()
                .toArray(String[]::new);

        for(String activ : distinctActivities)
        {
            LocalTime time =  LocalTime.of(0, 0, 0,0);

            for(MonitoredData data : monitoredData)
            {
                if(data.activity_label.equals(activ))
                {
                    long diff = data.end_time.getTime() - data.start_time.getTime();
                    time = time.plusSeconds(diff / 1000 % 60);
                    time = time.plusMinutes(diff / (60 * 1000) % 60);
                    time = time.plusHours(diff / (60 * 60 * 1000));
                }
            }
            activityTimes.put(activ,time);
        }

        File file = new File("Task_5.txt");

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            activityTimes.forEach( (activity, time) ->
            {
                try
                {
                    writer.write(activity + " = " + time.toString() + "\n");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activityTimes;
    }

    public List<String> activityFilter()
    {
        ArrayList<String> activities = new ArrayList<>();
        ArrayList<LocalTime> times = new ArrayList<>();

        for(MonitoredData data : monitoredData)
        {
            LocalTime time =  LocalTime.of(0, 0, 0,0);
            long diff = data.end_time.getTime() - data.start_time.getTime();
            time = time.plusSeconds(diff / 1000 % 60);
            time = time.plusMinutes(diff / (60 * 1000) % 60);
            time = time.plusHours(diff / (60 * 60 * 1000));
            times.add(time);
            activities.add(data.activity_label);
        }

        Iterator<String> i1 = activities.iterator();
        Iterator<LocalTime> i2 = times.iterator();
        Map<String,LocalTime> map = new HashMap<String,LocalTime>();

        while (i1.hasNext() && i2.hasNext())
        {
            map.put(i1.next(), i2.next());
        }

        List<String> filteredActivities = new LinkedList<>();
        Set<Map.Entry<String,LocalTime>> set = map.entrySet()
                .stream()
                .filter(e -> e.getValue().getHour() > 0 || e.getValue().getMinute() >= 4)
                .distinct()
                .collect(Collectors.toSet());

        for(Map.Entry<String,LocalTime> s : set)
        {
            filteredActivities.add(s.getKey());
        }
        File file = new File("Task_6.txt");

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            filteredActivities.forEach(activity ->
            {
                try
                {
                    writer.write(activity + "\n");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredActivities;
    }
}
