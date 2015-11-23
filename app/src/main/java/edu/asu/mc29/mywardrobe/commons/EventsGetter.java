package edu.asu.mc29.mywardrobe.commons;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rrshah9 on 11/22/15.
 */
public class EventsGetter {

    public static ArrayList<Events> events = new ArrayList<>();

    public static ArrayList<Events> readCalendarEvent(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date dateCC = calendar.getTime();
        formatter.format(dateCC);
        calendar.setTime(dateCC);
        long after = calendar.getTimeInMillis();

        //Date endDate
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Calendar endOfDay = Calendar.getInstance();
        endOfDay.setTime(calendar.getTime());
        String dtstart = "dtstart";
        String dtend = "dtend";


        calendar.setTime(dateCC);

        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, "(" + dtstart + ">" + after +
                                " and " + dtend + "<" + endOfDay.getTimeInMillis() + ")",
                        null, "dtstart ASC");
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id

        for (int i = 0; i < CNames.length; i++) {
            Events event = new Events();
            event.setName(cursor.getString(1));
            event.setStartDate(getDate(Long.parseLong(cursor.getString(3))));
            event.setEnddate(getDate(Long.parseLong(cursor.getString(4))));
            event.setDescription(cursor.getString(2));
            CNames[i] = cursor.getString(1);
            cursor.moveToNext();
            events.add(event);
        }
        return events;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
