package org.example;

import java.time.LocalTime;
import java.util.Objects;

public class Hours {
    private LocalTime start;
    private LocalTime end;

    public Hours(LocalTime start, LocalTime end) {
        // Check if hours are correct
        if (start.compareTo(end) < 0) {
            this.start = start;
            this.end = end;
        } else {
            System.out.println("Start time cannot be after end time!");
        }
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
//        return "[" +
//                "start=" + start +
//                ", end=" + end +
//                ']';
        return "[" + '"' + start + '"' + ", " + '"' + end + '"' + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hours)) return false;
        Hours hours = (Hours) o;
        return Objects.equals(start, hours.start) &&
                Objects.equals(end, hours.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
