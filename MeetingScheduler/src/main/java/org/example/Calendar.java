package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private Hours working_hours;
    private List<Hours> planned_meeting;

    public Calendar(Hours workingHours, List<Hours> plannedMeetings) {
        if (checkHours(workingHours, plannedMeetings)) { // verifying hours
            this.working_hours = workingHours;
            this.planned_meeting = plannedMeetings;
        } else {
            System.out.println("Wrong data! Check your working hours and planned meetings!");
        }

    }

    // Checking if planned meetings are within working hours
    private boolean checkHours(Hours workingHours, List<Hours> plannedMeetings) {
        return plannedMeetings.stream()
                .allMatch(meeting -> meeting.getStart().compareTo(workingHours.getStart()) >= 0 &&
                        meeting.getEnd().compareTo(workingHours.getEnd()) <= 0);

    }

    // Setting free meeting slots for the calendar
    public List<Hours> getFreeTime() {
        List<Hours> freeTime = new ArrayList<>();

        for (int i = 0; i < planned_meeting.size(); i++) {
            LocalTime endOfMeeting = planned_meeting.get(i).getEnd();
            LocalTime startMeeting = planned_meeting.get(i).getStart();

            // Setting time between staring work and the first meeting
            if (i == 0 && startMeeting.compareTo(working_hours.getStart()) > 0) {
                freeTime.add(new Hours(working_hours.getStart(), startMeeting));
            }

            // Setting time between end of meeting and start of the next one
            if (i < planned_meeting.size() - 1) {
                LocalTime startNextMeeting = planned_meeting.get(i + 1).getStart();
                if (endOfMeeting.compareTo(startNextMeeting) < 0)
                    freeTime.add(new Hours(endOfMeeting, startNextMeeting));
            }

            // Setting time between the last meeting and end of working hours
            else {
                LocalTime endOfWork = working_hours.getEnd();
                if (endOfMeeting.compareTo(endOfWork) < 0)
                    freeTime.add(new Hours(endOfMeeting, endOfWork));
            }
        }
        return freeTime;
    }
}
