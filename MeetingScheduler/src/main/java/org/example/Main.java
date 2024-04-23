package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create Gson instance with custom type adapter
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeAdapter());
        Gson gson = gsonBuilder.create();

        // JSON files
        ClassLoader loader = Main.class.getClassLoader();
        File file1 = new File(loader.getResource("jsonFiles/calendar1.json").getFile());
        File file2 = new File(loader.getResource("jsonFiles/calendar2.json").getFile());

        // Set the duration of the meeting in minutes (1-60)
        int meetingDuration = 30;

        // Read and parse JSON files
        Calendar calendar1 = null;
        Calendar calendar2 = null;

        try {
            // Parse File1
            FileReader reader1 = new FileReader(file1);
            calendar1 = gson.fromJson(reader1, Calendar.class);
            reader1.close();

            // Parse File2
            FileReader reader2 = new FileReader(file2);
            calendar2 = gson.fromJson(reader2, Calendar.class);
            reader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if calendars were successfully parsed
        if (calendar1 != null && calendar2 != null) {

            // Find available meeting times
            List<Hours> meetingTimes = MeetingSetter.setMeetings(calendar1, calendar2, meetingDuration);
            // System.out.println(meetingTimes);
        } else {
            System.out.println("Failed to parse calendars.");
        }
    }
}

