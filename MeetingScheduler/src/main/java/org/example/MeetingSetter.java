package org.example;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingSetter {
    // Setting possible meeting times
    public static List<Hours> setMeetings(Calendar calendar1, Calendar calendar2, int meetingDuration){
        // change the while loop into a for loop in java
        List<Hours> sharedFreeTime = getTogetherFreeTime(calendar1, calendar2);
        List<Hours> meetingTimes = new ArrayList<>();

        // Looping through shared free time
        for (int i = 0; i < sharedFreeTime.size(); i++) {
            Hours freeTime = sharedFreeTime.get(i);
            LocalTime temp = freeTime.getStart();

            // As long as the time between the start of a possible meeting and its end is greater than the meeting length, it adds it to the list
            while (Duration.between(temp, freeTime.getEnd()).toMinutes() >= meetingDuration) {
                LocalTime endOfMeeting = temp.plusMinutes(meetingDuration);
                meetingTimes.add(new Hours(temp, endOfMeeting));
                temp = endOfMeeting;
            }
        }

        if (meetingTimes.isEmpty()) {
            System.out.println("No opportunity for a meeting!");
        } else {
            System.out.println("Opportunities for a meeting that lasts " + meetingDuration + " minutes, are: \n" + meetingTimes);
        }
        return meetingTimes;
    }

    // Finding shared free time in both calendars
    private static List<Hours> getTogetherFreeTime(Calendar person1Calendar, Calendar person2Calendar) {
        List<Hours> freeTime1 = person1Calendar.getFreeTime();
        List<Hours> freeTime2 = person2Calendar.getFreeTime();

        List<Hours> togetherFreeTime = new ArrayList<>();

        // Looping through calendar 1
        for (int i = 0; i < freeTime1.size(); i++) {
            LocalTime startFreeTime = freeTime1.get(i).getStart();
            LocalTime endFreeTime = freeTime1.get(i).getEnd();

            // Looping through calendar 2
            for (int j = 0; j < freeTime2.size(); j++) {
                LocalTime startFreeTime2 = freeTime2.get(j).getStart();
                LocalTime endFreeTime2 = freeTime2.get(j).getEnd();

                // Finding end of free time (for calendar with shorter work hours
                LocalTime endTogetherFreeTime = endFreeTime.compareTo(endFreeTime2) > 0 ? endFreeTime2 : endFreeTime;

                // Checking there is a common part between the free times slots
                if (startFreeTime.compareTo(startFreeTime2) >= 0 && startFreeTime.compareTo(endFreeTime2) < 0) {
                    togetherFreeTime.add(new Hours(startFreeTime, endTogetherFreeTime));
                } else if (startFreeTime.compareTo(startFreeTime2) <= 0 && startFreeTime2.compareTo(endFreeTime) < 0) {
                    togetherFreeTime.add(new Hours(startFreeTime2, endTogetherFreeTime));
                }
            }
        }
        return togetherFreeTime;
    }
}
