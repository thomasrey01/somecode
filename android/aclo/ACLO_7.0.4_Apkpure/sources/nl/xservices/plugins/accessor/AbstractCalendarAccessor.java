package nl.xservices.plugins.accessor;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import nl.xservices.plugins.Calendar;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public abstract class AbstractCalendarAccessor {
    public static final String CONTENT_PROVIDER = "content://com.android.calendar";
    public static final String CONTENT_PROVIDER_PATH_ATTENDEES = "/attendees";
    public static final String CONTENT_PROVIDER_PATH_CALENDARS = "/calendars";
    public static final String CONTENT_PROVIDER_PATH_EVENTS = "/events";
    public static final String CONTENT_PROVIDER_PATH_INSTANCES_WHEN = "/instances/when";
    public static final String CONTENT_PROVIDER_PATH_REMINDERS = "/reminders";
    public static final String CONTENT_PROVIDER_PRE_FROYO = "content://calendar";
    public static final String LOG_TAG = "Calendar";
    private EnumMap<KeyIndex, String> calendarKeys = initContentProviderKeys();
    protected CordovaInterface cordova;

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public enum KeyIndex {
        CALENDARS_ID,
        IS_PRIMARY,
        CALENDARS_NAME,
        CALENDARS_VISIBLE,
        CALENDARS_DISPLAY_NAME,
        EVENTS_ID,
        EVENTS_CALENDAR_ID,
        EVENTS_DESCRIPTION,
        EVENTS_LOCATION,
        EVENTS_SUMMARY,
        EVENTS_START,
        EVENTS_END,
        EVENTS_RRULE,
        EVENTS_ALL_DAY,
        INSTANCES_ID,
        INSTANCES_EVENT_ID,
        INSTANCES_BEGIN,
        INSTANCES_END,
        ATTENDEES_ID,
        ATTENDEES_EVENT_ID,
        ATTENDEES_NAME,
        ATTENDEES_EMAIL,
        ATTENDEES_STATUS
    }

    protected abstract EnumMap<KeyIndex, String> initContentProviderKeys();

    protected abstract Cursor queryAttendees(String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryCalendars(String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryEventInstances(long startFrom, long startTo, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    protected abstract Cursor queryEvents(String[] projection, String selection, String[] selectionArgs, String sortOrder);

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class Event {
        boolean allDay;
        ArrayList<Attendee> attendees;
        String endDate;
        String eventId;
        String id;
        String location;
        String message;
        String recurrenceByDay;
        String recurrenceByMonthDay;
        String recurrenceCount;
        String recurrenceFreq;
        String recurrenceInterval;
        String recurrenceUntil;
        String recurrenceWeekstart;
        boolean recurring = false;
        String startDate;
        String title;

        protected Event() {
        }

        public JSONObject toJSONObject() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("id", this.eventId);
                jSONObject.putOpt("message", this.message);
                jSONObject.putOpt(FirebaseAnalytics.Param.LOCATION, this.location);
                jSONObject.putOpt("title", this.title);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                simpleDateFormat.setTimeZone(TimeZone.getDefault());
                if (this.startDate != null) {
                    jSONObject.put("startDate", simpleDateFormat.format(new Date(Long.parseLong(this.startDate))));
                }
                if (this.endDate != null) {
                    jSONObject.put("endDate", simpleDateFormat.format(new Date(Long.parseLong(this.endDate))));
                }
                jSONObject.put("allday", this.allDay);
                if (this.attendees != null) {
                    JSONArray jSONArray = new JSONArray();
                    Iterator<Attendee> it = this.attendees.iterator();
                    while (it.hasNext()) {
                        jSONArray.put(it.next().toJSONObject());
                    }
                    jSONObject.put("attendees", jSONArray);
                }
                if (this.recurring) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.putOpt("freq", this.recurrenceFreq);
                    jSONObject2.putOpt("interval", this.recurrenceInterval);
                    jSONObject2.putOpt("wkst", this.recurrenceWeekstart);
                    jSONObject2.putOpt("byday", this.recurrenceByDay);
                    jSONObject2.putOpt("bymonthday", this.recurrenceByMonthDay);
                    jSONObject2.putOpt("until", this.recurrenceUntil);
                    jSONObject2.putOpt("count", this.recurrenceCount);
                    jSONObject.put("recurrence", jSONObject2);
                }
                return jSONObject;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static class Attendee {
        String email;
        String id;
        String name;
        String status;

        protected Attendee() {
        }

        public JSONObject toJSONObject() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("id", this.id);
                jSONObject.putOpt(AppMeasurementSdk.ConditionalUserProperty.NAME, this.name);
                jSONObject.putOpt("email", this.email);
                jSONObject.putOpt(NotificationCompat.CATEGORY_STATUS, this.status);
                return jSONObject;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AbstractCalendarAccessor(CordovaInterface cordova) {
        this.cordova = cordova;
    }

    protected String getKey(KeyIndex index) {
        return this.calendarKeys.get(index);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0147 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0148  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private nl.xservices.plugins.accessor.AbstractCalendarAccessor.Event[] fetchEventInstances(java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, long r21, long r23) {
        /*
            Method dump skipped, instructions count: 453
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: nl.xservices.plugins.accessor.AbstractCalendarAccessor.fetchEventInstances(java.lang.String, java.lang.String, java.lang.String, java.lang.String, long, long):nl.xservices.plugins.accessor.AbstractCalendarAccessor$Event[]");
    }

    private String[] getActiveCalendarIds() {
        int i = 0;
        String[] strArr = {getKey(KeyIndex.CALENDARS_ID)};
        Cursor queryCalendars = queryCalendars(strArr, getKey(KeyIndex.CALENDARS_VISIBLE) + "=1", null, null);
        if (queryCalendars.moveToFirst()) {
            String[] strArr2 = new String[queryCalendars.getCount()];
            do {
                strArr2[i] = queryCalendars.getString(queryCalendars.getColumnIndex(getKey(KeyIndex.CALENDARS_ID)));
                i++;
            } while (queryCalendars.moveToNext());
            queryCalendars.close();
            return strArr2;
        }
        return null;
    }

    public final JSONArray getActiveCalendars() throws JSONException {
        String[] strArr = {getKey(KeyIndex.CALENDARS_ID), getKey(KeyIndex.CALENDARS_NAME), getKey(KeyIndex.CALENDARS_DISPLAY_NAME), getKey(KeyIndex.IS_PRIMARY)};
        Cursor queryCalendars = queryCalendars(strArr, getKey(KeyIndex.CALENDARS_VISIBLE) + "=1", null, null);
        if (queryCalendars == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        if (queryCalendars.moveToFirst()) {
            do {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("id", queryCalendars.getString(queryCalendars.getColumnIndex(getKey(KeyIndex.CALENDARS_ID))));
                jSONObject.put(AppMeasurementSdk.ConditionalUserProperty.NAME, queryCalendars.getString(queryCalendars.getColumnIndex(getKey(KeyIndex.CALENDARS_NAME))));
                jSONObject.put("displayname", queryCalendars.getString(queryCalendars.getColumnIndex(getKey(KeyIndex.CALENDARS_DISPLAY_NAME))));
                int columnIndex = queryCalendars.getColumnIndex(getKey(KeyIndex.IS_PRIMARY));
                if (columnIndex == -1) {
                    columnIndex = queryCalendars.getColumnIndex("COALESCE(isPrimary, ownerAccount = account_name)");
                }
                jSONObject.put("isPrimary", "1".equals(queryCalendars.getString(columnIndex)));
                jSONArray.put(jSONObject);
            } while (queryCalendars.moveToNext());
            queryCalendars.close();
        }
        return jSONArray;
    }

    private Map<String, Event> fetchEventsAsMap(Event[] instances, String calendarId) {
        String[] split;
        List<String> asList = Arrays.asList(getActiveCalendarIds());
        if (asList.isEmpty()) {
            return null;
        }
        if (calendarId != null) {
            ArrayList arrayList = new ArrayList();
            if (asList.contains(calendarId)) {
                arrayList.add(calendarId);
            }
            asList = arrayList;
        }
        if (asList.isEmpty()) {
            return null;
        }
        char c = 2;
        String[] strArr = {getKey(KeyIndex.EVENTS_ID), getKey(KeyIndex.EVENTS_DESCRIPTION), getKey(KeyIndex.EVENTS_LOCATION), getKey(KeyIndex.EVENTS_SUMMARY), getKey(KeyIndex.EVENTS_START), getKey(KeyIndex.EVENTS_END), getKey(KeyIndex.EVENTS_RRULE), getKey(KeyIndex.EVENTS_ALL_DAY)};
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getKey(KeyIndex.EVENTS_ID) + " IN (");
        stringBuffer.append(instances[0].eventId);
        for (int i = 1; i < instances.length; i++) {
            stringBuffer.append(",");
            stringBuffer.append(instances[i].eventId);
        }
        stringBuffer.append(") AND " + getKey(KeyIndex.EVENTS_CALENDAR_ID) + " IN (");
        String str = "";
        for (String str2 : asList) {
            stringBuffer.append(str);
            stringBuffer.append(str2);
            str = ",";
        }
        stringBuffer.append(")");
        Cursor queryEvents = queryEvents(strArr, stringBuffer.toString(), null, null);
        HashMap hashMap = new HashMap();
        if (queryEvents.moveToFirst()) {
            int[] iArr = new int[8];
            for (int i2 = 0; i2 < 8; i2++) {
                iArr[i2] = queryEvents.getColumnIndex(strArr[i2]);
            }
            while (true) {
                Event event = new Event();
                event.id = queryEvents.getString(iArr[0]);
                event.message = queryEvents.getString(iArr[1]);
                event.location = queryEvents.getString(iArr[c]);
                event.title = queryEvents.getString(iArr[3]);
                event.startDate = queryEvents.getString(iArr[4]);
                event.endDate = queryEvents.getString(iArr[5]);
                if (!TextUtils.isEmpty(queryEvents.getString(iArr[6]))) {
                    event.recurring = true;
                    for (String str3 : queryEvents.getString(iArr[6]).split(";")) {
                        String str4 = str3.split("=")[0];
                        if (str4.equals("FREQ")) {
                            event.recurrenceFreq = str3.split("=")[1];
                        } else if (str4.equals("INTERVAL")) {
                            event.recurrenceInterval = str3.split("=")[1];
                        } else if (str4.equals("WKST")) {
                            event.recurrenceWeekstart = str3.split("=")[1];
                        } else if (str4.equals("BYDAY")) {
                            event.recurrenceByDay = str3.split("=")[1];
                        } else if (str4.equals("BYMONTHDAY")) {
                            event.recurrenceByMonthDay = str3.split("=")[1];
                        } else if (str4.equals("UNTIL")) {
                            event.recurrenceUntil = str3.split("=")[1];
                        } else if (str4.equals("COUNT")) {
                            event.recurrenceCount = str3.split("=")[1];
                        } else {
                            Log.d(LOG_TAG, "Missing handler for " + str3);
                        }
                    }
                } else {
                    event.recurring = false;
                }
                event.allDay = queryEvents.getInt(iArr[7]) != 0;
                hashMap.put(event.id, event);
                if (!queryEvents.moveToNext()) {
                    break;
                }
                c = 2;
            }
            queryEvents.close();
        }
        return hashMap;
    }

    private Map<String, ArrayList<Attendee>> fetchAttendeesForEventsAsMap(String[] eventIds) {
        String str = null;
        if (eventIds.length == 0) {
            return null;
        }
        String[] strArr = {getKey(KeyIndex.ATTENDEES_EVENT_ID), getKey(KeyIndex.ATTENDEES_ID), getKey(KeyIndex.ATTENDEES_NAME), getKey(KeyIndex.ATTENDEES_EMAIL), getKey(KeyIndex.ATTENDEES_STATUS)};
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getKey(KeyIndex.ATTENDEES_EVENT_ID) + " IN (");
        stringBuffer.append(eventIds[0]);
        for (int i = 1; i < eventIds.length; i++) {
            stringBuffer.append(",");
            stringBuffer.append(eventIds[i]);
        }
        stringBuffer.append(")");
        Cursor queryAttendees = queryAttendees(strArr, stringBuffer.toString(), null, getKey(KeyIndex.ATTENDEES_EVENT_ID) + " ASC");
        HashMap hashMap = new HashMap();
        if (queryAttendees.moveToFirst()) {
            int[] iArr = new int[5];
            for (int i2 = 0; i2 < 5; i2++) {
                iArr[i2] = queryAttendees.getColumnIndex(strArr[i2]);
            }
            ArrayList arrayList = null;
            do {
                String string = queryAttendees.getString(iArr[0]);
                if (str == null || !str.equals(string)) {
                    arrayList = new ArrayList();
                    hashMap.put(string, arrayList);
                    str = string;
                }
                Attendee attendee = new Attendee();
                attendee.id = queryAttendees.getString(iArr[1]);
                attendee.name = queryAttendees.getString(iArr[2]);
                attendee.email = queryAttendees.getString(iArr[3]);
                attendee.status = queryAttendees.getString(iArr[4]);
                arrayList.add(attendee);
            } while (queryAttendees.moveToNext());
            queryAttendees.close();
        }
        return hashMap;
    }

    public JSONArray findEvents(String eventId, String title, String location, String notes, long startFrom, long startTo, String calendarId) {
        JSONArray jSONArray = new JSONArray();
        Event[] fetchEventInstances = fetchEventInstances(eventId, title, location, notes, startFrom, startTo);
        if (fetchEventInstances == null) {
            return jSONArray;
        }
        Map<String, Event> fetchEventsAsMap = fetchEventsAsMap(fetchEventInstances, calendarId);
        Map<String, ArrayList<Attendee>> fetchAttendeesForEventsAsMap = fetchAttendeesForEventsAsMap((String[]) fetchEventsAsMap.keySet().toArray(new String[0]));
        for (Event event : fetchEventInstances) {
            Event event2 = fetchEventsAsMap.get(event.eventId);
            if (event2 != null) {
                event.message = event2.message;
                event.location = event2.location;
                event.title = event2.title;
                if (!event2.recurring) {
                    event.startDate = event2.startDate;
                    event.endDate = event2.endDate;
                }
                event.recurring = event2.recurring;
                event.recurrenceFreq = event2.recurrenceFreq;
                event.recurrenceInterval = event2.recurrenceInterval;
                event.recurrenceWeekstart = event2.recurrenceWeekstart;
                event.recurrenceByDay = event2.recurrenceByDay;
                event.recurrenceByMonthDay = event2.recurrenceByMonthDay;
                event.recurrenceUntil = event2.recurrenceUntil;
                event.recurrenceCount = event2.recurrenceCount;
                event.allDay = event2.allDay;
                event.attendees = fetchAttendeesForEventsAsMap.get(event.eventId);
                jSONArray.put(event.toJSONObject());
            }
        }
        return jSONArray;
    }

    public boolean deleteEvent(Uri eventsUri, long startFrom, long startTo, String title, String location, String notes) {
        int i;
        ContentResolver contentResolver = this.cordova.getActivity().getApplicationContext().getContentResolver();
        Event[] fetchEventInstances = fetchEventInstances(null, title, location, notes, startFrom, startTo);
        if (fetchEventInstances != null) {
            i = 0;
            for (Event event : fetchEventInstances) {
                i = contentResolver.delete(ContentUris.withAppendedId(eventsUri, Integer.parseInt(event.eventId)), null, null);
            }
        } else {
            i = 0;
        }
        return i > 0;
    }

    public boolean deleteEventById(Uri eventsUri, long id, long fromTime) {
        long j;
        String str;
        ArrayList arrayList;
        if (id == -1) {
            throw new IllegalArgumentException("Event id not specified.");
        }
        Cursor queryEvents = queryEvents(new String[]{"dtstart", "rrule"}, "_id = ?", new String[]{Long.toString(id)}, "dtstart");
        if (queryEvents.moveToNext()) {
            j = queryEvents.getLong(0);
            str = queryEvents.getString(1);
        } else {
            j = -1;
            str = null;
        }
        queryEvents.close();
        if (j != -1) {
            if (fromTime == -1 || j >= fromTime) {
                this.cordova.getActivity().getContentResolver();
                return this.cordova.getActivity().getContentResolver().delete(ContentUris.withAppendedId(eventsUri, id), null, null) > 0;
            }
            Cursor queryEventInstances = queryEventInstances(fromTime, fromTime + 31708800000L, new String[]{"dtstart"}, "event_id = ?", new String[]{Long.toString(id)}, "dtstart");
            long j2 = queryEventInstances.moveToNext() ? queryEventInstances.getLong(0) : -1L;
            queryEventInstances.close();
            if (j2 == -1) {
                return false;
            }
            if (str == null) {
                str = "";
            }
            Iterator it = new ArrayList(Arrays.asList(str.split(";"))).iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (str2.startsWith("COUNT=") || str2.startsWith("UNTIL=")) {
                    it.remove();
                }
            }
            String str3 = TextUtils.join(";", arrayList) + ";UNTIL=" + Calendar.formatICalDateTime(new Date(fromTime - 1000));
            ContentValues contentValues = new ContentValues();
            contentValues.put("rrule", str3);
            return this.cordova.getActivity().getContentResolver().update(ContentUris.withAppendedId(eventsUri, id), contentValues, null, null) > 0;
        }
        throw new RuntimeException("Could not find event.");
    }

    public String createEvent(Uri eventsUri, String title, long startTime, long endTime, String description, String location, Long firstReminderMinutes, Long secondReminderMinutes, String recurrence, int recurrenceInterval, String recurrenceWeekstart, String recurrenceByDay, String recurrenceByMonthDay, Long recurrenceEndTime, Long recurrenceCount, String allday, Integer calendarId, String url) {
        ContentResolver contentResolver;
        String str;
        String str2;
        Uri insert;
        String str3;
        Integer num;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        ContentResolver contentResolver2 = this.cordova.getActivity().getContentResolver();
        ContentValues contentValues = new ContentValues();
        int i = ("true".equals(allday) && isAllDayEvent(new Date(startTime), new Date(endTime))) ? 1 : 0;
        if (i != 0) {
            contentResolver = contentResolver2;
            contentValues.put("eventTimezone", "UTC");
            contentValues.put("dtstart", Long.valueOf(startTime + TimeZone.getDefault().getOffset(startTime)));
            contentValues.put("dtend", Long.valueOf(TimeZone.getDefault().getOffset(endTime) + endTime));
        } else {
            contentResolver = contentResolver2;
            contentValues.put("eventTimezone", TimeZone.getDefault().getID());
            contentValues.put("dtstart", Long.valueOf(startTime));
            contentValues.put("dtend", Long.valueOf(endTime));
        }
        contentValues.put("allDay", Integer.valueOf(i));
        contentValues.put("title", title);
        if (url == null) {
            str = description;
        } else if (description == null) {
            str = url;
        } else {
            str = description + " " + url;
        }
        contentValues.put("description", str);
        contentValues.put("hasAlarm", Integer.valueOf((firstReminderMinutes.longValue() > -1 || secondReminderMinutes.longValue() > -1) ? 1 : 0));
        contentValues.put("calendar_id", calendarId);
        contentValues.put("eventLocation", location);
        if (recurrence != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("FREQ=");
            sb.append(recurrence.toUpperCase());
            String str9 = "";
            if (recurrenceInterval > -1) {
                str4 = ";INTERVAL=" + recurrenceInterval;
            } else {
                str4 = "";
            }
            sb.append(str4);
            if (recurrenceWeekstart != null) {
                str5 = ";WKST=" + recurrenceWeekstart;
            } else {
                str5 = "";
            }
            sb.append(str5);
            if (recurrenceByDay != null) {
                str6 = ";BYDAY=" + recurrenceByDay;
            } else {
                str6 = "";
            }
            sb.append(str6);
            if (recurrenceByMonthDay != null) {
                str7 = ";BYMONTHDAY=" + recurrenceByMonthDay;
            } else {
                str7 = "";
            }
            sb.append(str7);
            if (recurrenceEndTime.longValue() > -1) {
                str8 = ";UNTIL=" + Calendar.formatICalDateTime(new Date(recurrenceEndTime.longValue()));
            } else {
                str8 = "";
            }
            sb.append(str8);
            if (recurrenceCount.longValue() > -1) {
                str9 = ";COUNT=" + recurrenceCount;
            }
            sb.append(str9);
            contentValues.put("rrule", sb.toString());
        }
        String str10 = null;
        ContentResolver contentResolver3 = contentResolver;
        try {
            insert = contentResolver3.insert(eventsUri, contentValues);
            str10 = insert.getLastPathSegment();
            str3 = "Created event with ID " + str10;
            str2 = LOG_TAG;
        } catch (Exception e) {
            e = e;
            str2 = LOG_TAG;
        }
        try {
            Log.d(str2, str3);
            if (firstReminderMinutes.longValue() > -1) {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("event_id", Long.valueOf(Long.parseLong(insert.getLastPathSegment())));
                contentValues2.put("minutes", firstReminderMinutes);
                num = 1;
                contentValues2.put(FirebaseAnalytics.Param.METHOD, (Integer) 1);
                contentResolver3.insert(Uri.parse("content://com.android.calendar/reminders"), contentValues2);
            } else {
                num = 1;
            }
            if (secondReminderMinutes.longValue() > -1) {
                ContentValues contentValues3 = new ContentValues();
                contentValues3.put("event_id", Long.valueOf(Long.parseLong(insert.getLastPathSegment())));
                contentValues3.put("minutes", secondReminderMinutes);
                contentValues3.put(FirebaseAnalytics.Param.METHOD, num);
                contentResolver3.insert(Uri.parse("content://com.android.calendar/reminders"), contentValues3);
            }
        } catch (Exception e2) {
            e = e2;
            Log.e(str2, "Creating reminders failed, ignoring since the event was created.", e);
            return str10;
        }
        return str10;
    }

    public String createCalendar(String calendarName, String calendarColor) {
        Uri uri;
        try {
            uri = CalendarContract.Calendars.CONTENT_URI;
        } catch (Exception e) {
            e = e;
        }
        try {
            ContentResolver contentResolver = this.cordova.getActivity().getContentResolver();
            Cursor query = contentResolver.query(uri, new String[]{"_id", AppMeasurementSdk.ConditionalUserProperty.NAME, "calendar_displayName"}, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    if ((query.getString(1) != null && query.getString(1).equals(calendarName)) || (query.getString(2) != null && query.getString(2).equals(calendarName))) {
                        query.close();
                        return null;
                    }
                }
                query.close();
            }
            Uri uri2 = CalendarContract.Calendars.CONTENT_URI;
            ContentValues contentValues = new ContentValues();
            contentValues.put("account_name", "AccountName");
            contentValues.put("account_type", "LOCAL");
            contentValues.put(AppMeasurementSdk.ConditionalUserProperty.NAME, calendarName);
            contentValues.put("calendar_displayName", calendarName);
            if (calendarColor != null) {
                contentValues.put("calendar_color", Integer.valueOf(Color.parseColor(calendarColor)));
            }
            contentValues.put("visible", (Integer) 1);
            contentValues.put("calendar_access_level", (Integer) 700);
            contentValues.put("ownerAccount", "AccountName");
            contentValues.put("sync_events", (Integer) 0);
            Uri insert = contentResolver.insert(uri2.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").appendQueryParameter("account_name", "AccountName").appendQueryParameter("account_type", "LOCAL").build(), contentValues);
            if (insert != null) {
                return insert.getLastPathSegment();
            }
        } catch (Exception e2) {
            e = e2;
            Log.e(LOG_TAG, "Creating calendar failed.", e);
            return null;
        }
        return null;
    }

    public void deleteCalendar(String calendarName) {
        try {
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            ContentResolver contentResolver = this.cordova.getActivity().getContentResolver();
            Cursor query = contentResolver.query(uri, new String[]{"_id", AppMeasurementSdk.ConditionalUserProperty.NAME, "calendar_displayName"}, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    if ((query.getString(1) != null && query.getString(1).equals(calendarName)) || (query.getString(2) != null && query.getString(2).equals(calendarName))) {
                        contentResolver.delete(ContentUris.withAppendedId(uri, query.getLong(0)), null, null);
                    }
                }
                query.close();
            }
            deleteCrashingCalendars(contentResolver);
        } catch (Throwable th) {
            System.err.println(th.getMessage());
            th.printStackTrace();
        }
    }

    private void deleteCrashingCalendars(ContentResolver contentResolver) {
        Uri build = CalendarContract.Calendars.CONTENT_URI.buildUpon().appendQueryParameter("caller_is_syncadapter", "true").appendQueryParameter("account_name", "FixingAccountName").appendQueryParameter("account_type", "LOCAL").build();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_name", "FixingAccountName");
        contentValues.put("account_type", "LOCAL");
        if (contentResolver.update(build, contentValues, "account_name IS NULL", null) > 0) {
            Uri uri = CalendarContract.Calendars.CONTENT_URI;
            Cursor query = contentResolver.query(uri, new String[]{"_id", "account_name"}, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    if (query.getString(1) != null && query.getString(1).equals("FixingAccountName")) {
                        contentResolver.delete(ContentUris.withAppendedId(uri, query.getLong(0)), null, null);
                    }
                }
                query.close();
            }
        }
    }

    public static boolean isAllDayEvent(final Date startDate, final Date endDate) {
        return (endDate.getTime() - startDate.getTime()) % 86400000 == 0;
    }
}
