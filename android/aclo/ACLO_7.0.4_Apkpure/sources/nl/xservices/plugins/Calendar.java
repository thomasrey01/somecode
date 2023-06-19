package nl.xservices.plugins;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import nl.xservices.plugins.accessor.AbstractCalendarAccessor;
import nl.xservices.plugins.accessor.CalendarProviderAccessor;
import nl.xservices.plugins.accessor.LegacyCalendarAccessor;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class Calendar extends CordovaPlugin {
    private static final String ACTION_CREATE_CALENDAR = "createCalendar";
    private static final String ACTION_CREATE_EVENT_INTERACTIVELY = "createEventInteractively";
    private static final String ACTION_CREATE_EVENT_WITH_OPTIONS = "createEventWithOptions";
    private static final String ACTION_DELETE_CALENDAR = "deleteCalendar";
    private static final String ACTION_DELETE_EVENT = "deleteEvent";
    private static final String ACTION_DELETE_EVENT_BY_ID = "deleteEventById";
    private static final String ACTION_FIND_EVENT_WITH_OPTIONS = "findEventWithOptions";
    private static final String ACTION_LIST_CALENDARS = "listCalendars";
    private static final String ACTION_LIST_EVENTS_IN_RANGE = "listEventsInRange";
    private static final String ACTION_OPEN_CALENDAR = "openCalendar";
    private static final String HAS_READWRITE_PERMISSION = "hasReadWritePermission";
    private static final String HAS_READ_PERMISSION = "hasReadPermission";
    private static final String HAS_WRITE_PERMISSION = "hasWritePermission";
    private static final String LOG_TAG = "Calendar";
    private static final int PERMISSION_REQCODE_CREATE_CALENDAR = 100;
    private static final int PERMISSION_REQCODE_CREATE_EVENT = 102;
    private static final int PERMISSION_REQCODE_DELETE_CALENDAR = 101;
    private static final int PERMISSION_REQCODE_DELETE_EVENT = 103;
    private static final int PERMISSION_REQCODE_DELETE_EVENT_BY_ID = 104;
    private static final int PERMISSION_REQCODE_FIND_EVENTS = 200;
    private static final int PERMISSION_REQCODE_LIST_CALENDARS = 201;
    private static final int PERMISSION_REQCODE_LIST_EVENTS_IN_RANGE = 202;
    private static final String REQUEST_READWRITE_PERMISSION = "requestReadWritePermission";
    private static final String REQUEST_READ_PERMISSION = "requestReadPermission";
    private static final String REQUEST_WRITE_PERMISSION = "requestWritePermission";
    private static final Integer RESULT_CODE_CREATE = 0;
    private static final Integer RESULT_CODE_OPENCAL = 1;
    private AbstractCalendarAccessor calendarAccessor;
    private CallbackContext callback;
    private JSONArray requestArgs;

    @Override // org.apache.cordova.CordovaPlugin
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callback = callbackContext;
        this.requestArgs = args;
        boolean z = Build.VERSION.SDK_INT < 14;
        if (ACTION_OPEN_CALENDAR.equals(action)) {
            if (z) {
                openCalendarLegacy(args);
            } else {
                openCalendar(args);
            }
            return true;
        } else if (ACTION_CREATE_EVENT_WITH_OPTIONS.equals(action)) {
            if (z) {
                createEventInteractively(args);
            } else {
                createEvent(args);
            }
            return true;
        } else if (ACTION_CREATE_EVENT_INTERACTIVELY.equals(action)) {
            createEventInteractively(args);
            return true;
        } else if (ACTION_LIST_EVENTS_IN_RANGE.equals(action)) {
            listEventsInRange(args);
            return true;
        } else if (!z && ACTION_FIND_EVENT_WITH_OPTIONS.equals(action)) {
            findEvents(args);
            return true;
        } else if (!z && ACTION_DELETE_EVENT.equals(action)) {
            deleteEvent(args);
            return true;
        } else if (!z && ACTION_DELETE_EVENT_BY_ID.equals(action)) {
            deleteEventById(args);
            return true;
        } else if (ACTION_LIST_CALENDARS.equals(action)) {
            listCalendars();
            return true;
        } else if (!z && ACTION_CREATE_CALENDAR.equals(action)) {
            createCalendar(args);
            return true;
        } else if (!z && ACTION_DELETE_CALENDAR.equals(action)) {
            deleteCalendar(args);
            return true;
        } else if (HAS_READ_PERMISSION.equals(action)) {
            hasReadPermission();
            return true;
        } else if (HAS_WRITE_PERMISSION.equals(action)) {
            hasWritePermission();
            return true;
        } else if (HAS_READWRITE_PERMISSION.equals(action)) {
            hasReadWritePermission();
            return true;
        } else if (REQUEST_READ_PERMISSION.equals(action)) {
            requestReadPermission(0);
            return true;
        } else if (REQUEST_WRITE_PERMISSION.equals(action)) {
            requestWritePermission(0);
            return true;
        } else if (REQUEST_READWRITE_PERMISSION.equals(action)) {
            requestReadWritePermission(0);
            return true;
        } else {
            return false;
        }
    }

    private void hasReadPermission() {
        this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, calendarPermissionGranted("android.permission.READ_CALENDAR")));
    }

    private void hasWritePermission() {
        this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, calendarPermissionGranted("android.permission.WRITE_CALENDAR")));
    }

    private void hasReadWritePermission() {
        this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, calendarPermissionGranted("android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR")));
    }

    private void requestReadPermission(int requestCode) {
        requestPermission(requestCode, "android.permission.READ_CALENDAR");
    }

    private void requestWritePermission(int requestCode) {
        requestPermission(requestCode, "android.permission.WRITE_CALENDAR");
    }

    private void requestReadWritePermission(int requestCode) {
        requestPermission(requestCode, "android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR");
    }

    private boolean calendarPermissionGranted(String... types) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String str : types) {
            if (!PermissionHelper.hasPermission(this, str)) {
                return false;
            }
        }
        return true;
    }

    private void requestPermission(int requestCode, String... types) {
        if (calendarPermissionGranted(types)) {
            return;
        }
        PermissionHelper.requestPermissions(this, requestCode, types);
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
        for (int i : grantResults) {
            if (i == -1) {
                Log.d("Calendar", "Permission Denied!");
                this.callback.error("Please allow access to the Calendar and try again.");
                return;
            }
        }
        if (requestCode == 100) {
            createCalendar(this.requestArgs);
        } else if (requestCode == 101) {
            deleteCalendar(this.requestArgs);
        } else if (requestCode == 102) {
            createEvent(this.requestArgs);
        } else if (requestCode == 103) {
            deleteEvent(this.requestArgs);
        } else if (requestCode == 104) {
            deleteEventById(this.requestArgs);
        } else if (requestCode == 200) {
            findEvents(this.requestArgs);
        } else if (requestCode == PERMISSION_REQCODE_LIST_CALENDARS) {
            listCalendars();
        } else if (requestCode == PERMISSION_REQCODE_LIST_EVENTS_IN_RANGE) {
            listEventsInRange(this.requestArgs);
        }
    }

    private void openCalendarLegacy(JSONArray args) {
        try {
            final Long valueOf = Long.valueOf(args.getJSONObject(0).optLong("date"));
            this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.1
                @Override // java.lang.Runnable
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("beginTime", valueOf);
                    intent.setFlags(537001984);
                    intent.setClassName("com.android.calendar", "com.android.calendar.AgendaActivity");
                    Calendar.this.cordova.startActivityForResult(Calendar.this, intent, Calendar.RESULT_CODE_OPENCAL.intValue());
                    Calendar.this.callback.success();
                }
            });
        } catch (JSONException e) {
            PrintStream printStream = System.err;
            printStream.println("Exception: " + e.getMessage());
            this.callback.error(e.getMessage());
        }
    }

    private void openCalendar(JSONArray args) {
        try {
            final Long valueOf = Long.valueOf(args.getJSONObject(0).optLong("date"));
            this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.2
                @Override // java.lang.Runnable
                public void run() {
                    Uri.Builder appendPath = CalendarContract.CONTENT_URI.buildUpon().appendPath("time");
                    ContentUris.appendId(appendPath, valueOf.longValue());
                    Calendar.this.cordova.startActivityForResult(Calendar.this, new Intent("android.intent.action.VIEW").setData(appendPath.build()), Calendar.RESULT_CODE_OPENCAL.intValue());
                    Calendar.this.callback.success();
                }
            });
        } catch (JSONException e) {
            PrintStream printStream = System.err;
            printStream.println("Exception: " + e.getMessage());
            this.callback.error(e.getMessage());
        }
    }

    private void listCalendars() {
        if (!calendarPermissionGranted("android.permission.READ_CALENDAR")) {
            requestReadPermission(PERMISSION_REQCODE_LIST_CALENDARS);
        } else {
            this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        JSONArray activeCalendars = Calendar.this.getCalendarAccessor().getActiveCalendars();
                        if (activeCalendars == null) {
                            activeCalendars = new JSONArray();
                        }
                        Calendar.this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, activeCalendars));
                    } catch (JSONException e) {
                        PrintStream printStream = System.err;
                        printStream.println("JSONException: " + e.getMessage());
                        Calendar.this.callback.error(e.getMessage());
                    } catch (Exception e2) {
                        PrintStream printStream2 = System.err;
                        printStream2.println("Exception: " + e2.getMessage());
                        Calendar.this.callback.error(e2.getMessage());
                    }
                }
            });
        }
    }

    private void createCalendar(JSONArray args) {
        if (args.length() == 0) {
            System.err.println("Exception: No Arguments passed");
        } else if (!calendarPermissionGranted("android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR")) {
            requestReadWritePermission(100);
        } else {
            try {
                JSONObject jSONObject = args.getJSONObject(0);
                final String possibleNullString = getPossibleNullString("calendarColor", jSONObject);
                final String possibleNullString2 = getPossibleNullString("calendarName", jSONObject);
                if (possibleNullString2 == null) {
                    this.callback.error("calendarName is mandatory");
                } else {
                    this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.4
                        @Override // java.lang.Runnable
                        public void run() {
                            Calendar.this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, Calendar.this.getCalendarAccessor().createCalendar(possibleNullString2, possibleNullString)));
                        }
                    });
                }
            } catch (JSONException e) {
                PrintStream printStream = System.err;
                printStream.println("Exception: " + e.getMessage());
                this.callback.error(e.getMessage());
            }
        }
    }

    private void deleteCalendar(JSONArray args) {
        if (args.length() == 0) {
            System.err.println("Exception: No Arguments passed");
        } else if (!calendarPermissionGranted("android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR")) {
            requestReadWritePermission(101);
        } else {
            try {
                final String possibleNullString = getPossibleNullString("calendarName", args.getJSONObject(0));
                if (possibleNullString == null) {
                    this.callback.error("calendarName is mandatory");
                } else {
                    this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.5
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                Calendar.this.getCalendarAccessor().deleteCalendar(possibleNullString);
                                Calendar.this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, "yes"));
                            } catch (Exception e) {
                                PrintStream printStream = System.err;
                                printStream.println("Exception: " + e.getMessage());
                                Calendar.this.callback.error(e.getMessage());
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                PrintStream printStream = System.err;
                printStream.println("Exception: " + e.getMessage());
                this.callback.error(e.getMessage());
            }
        }
    }

    private void createEventInteractively(JSONArray args) {
        try {
            final JSONObject jSONObject = args.getJSONObject(0);
            final JSONObject jSONObject2 = jSONObject.getJSONObject("options");
            this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.6
                @Override // java.lang.Runnable
                public void run() {
                    boolean isAllDayEvent;
                    String possibleNullString = Calendar.getPossibleNullString("allday", jSONObject2);
                    if (possibleNullString != null) {
                        isAllDayEvent = Boolean.parseBoolean(possibleNullString);
                    } else {
                        isAllDayEvent = AbstractCalendarAccessor.isAllDayEvent(new Date(jSONObject.optLong("startTime")), new Date(jSONObject.optLong("endTime")));
                    }
                    Intent putExtra = new Intent("android.intent.action.EDIT").setType("vnd.android.cursor.item/event").putExtra("title", Calendar.getPossibleNullString("title", jSONObject)).putExtra("hasAlarm", 1);
                    if (isAllDayEvent) {
                        putExtra.putExtra("allDay", isAllDayEvent).putExtra("beginTime", jSONObject.optLong("startTime") + TimeZone.getDefault().getOffset(jSONObject.optLong("startTime"))).putExtra("endTime", jSONObject.optLong("endTime") + TimeZone.getDefault().getOffset(jSONObject.optLong("endTime"))).putExtra("eventTimezone", "TIMEZONE_UTC");
                    } else {
                        putExtra.putExtra("beginTime", jSONObject.optLong("startTime")).putExtra("endTime", jSONObject.optLong("endTime"));
                    }
                    if (!jSONObject.isNull(FirebaseAnalytics.Param.LOCATION)) {
                        putExtra.putExtra("eventLocation", jSONObject.optString(FirebaseAnalytics.Param.LOCATION));
                    }
                    String optString = !jSONObject.isNull("notes") ? jSONObject.optString("notes") : null;
                    if (!jSONObject2.isNull(ImagesContract.URL)) {
                        if (optString == null) {
                            optString = jSONObject2.optString(ImagesContract.URL);
                        } else {
                            optString = optString + " " + jSONObject2.optString(ImagesContract.URL);
                        }
                    }
                    putExtra.putExtra("description", optString);
                    putExtra.putExtra("calendar_id", jSONObject2.optInt("calendarId", 1));
                    String possibleNullString2 = Calendar.getPossibleNullString("recurrence", jSONObject2);
                    Long valueOf = jSONObject2.isNull("recurrenceEndTime") ? null : Long.valueOf(jSONObject2.optLong("recurrenceEndTime"));
                    int optInt = jSONObject2.optInt("recurrenceInterval");
                    if (possibleNullString2 != null) {
                        if (valueOf == null) {
                            putExtra.putExtra("rrule", "FREQ=" + possibleNullString2.toUpperCase() + ";INTERVAL=" + optInt);
                        } else {
                            putExtra.putExtra("rrule", "FREQ=" + possibleNullString2.toUpperCase() + ";INTERVAL=" + optInt + ";UNTIL=" + Calendar.formatICalDateTime(new Date(valueOf.longValue())));
                        }
                    }
                    Calendar.this.cordova.startActivityForResult(Calendar.this, putExtra, Calendar.RESULT_CODE_CREATE.intValue());
                }
            });
        } catch (JSONException e) {
            PrintStream printStream = System.err;
            printStream.println("Exception: " + e.getMessage());
            this.callback.error(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractCalendarAccessor getCalendarAccessor() {
        if (this.calendarAccessor == null) {
            if (Build.VERSION.SDK_INT >= 14) {
                Log.d("Calendar", "Initializing calendar plugin");
                this.calendarAccessor = new CalendarProviderAccessor(this.cordova);
            } else {
                Log.d("Calendar", "Initializing legacy calendar plugin");
                this.calendarAccessor = new LegacyCalendarAccessor(this.cordova);
            }
        }
        return this.calendarAccessor;
    }

    private void deleteEvent(JSONArray args) {
        if (args.length() == 0) {
            System.err.println("Exception: No Arguments passed");
        } else if (!calendarPermissionGranted("android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR")) {
            requestReadWritePermission(103);
        } else {
            try {
                final JSONObject jSONObject = args.getJSONObject(0);
                this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.7
                    @Override // java.lang.Runnable
                    public void run() {
                        Calendar.this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, Calendar.this.getCalendarAccessor().deleteEvent(null, jSONObject.optLong("startTime"), jSONObject.optLong("endTime"), Calendar.getPossibleNullString("title", jSONObject), Calendar.getPossibleNullString(FirebaseAnalytics.Param.LOCATION, jSONObject), Calendar.getPossibleNullString("notes", jSONObject))));
                    }
                });
            } catch (JSONException e) {
                PrintStream printStream = System.err;
                printStream.println("Exception: " + e.getMessage());
                this.callback.error(e.getMessage());
            }
        }
    }

    private void deleteEventById(final JSONArray args) {
        if (!calendarPermissionGranted("android.permission.WRITE_CALENDAR")) {
            requestWritePermission(104);
        } else {
            this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        JSONObject optJSONObject = args.optJSONObject(0);
                        Calendar.this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, Calendar.this.getCalendarAccessor().deleteEventById(null, optJSONObject != null ? optJSONObject.optLong("id", -1L) : -1L, optJSONObject != null ? optJSONObject.optLong("fromTime", -1L) : -1L)));
                    } catch (Exception e) {
                        PrintStream printStream = System.err;
                        printStream.println("Exception: " + e.getMessage());
                        Calendar.this.callback.error(e.getMessage());
                    }
                }
            });
        }
    }

    private void findEvents(JSONArray args) {
        if (args.length() == 0) {
            System.err.println("Exception: No Arguments passed");
        } else if (!calendarPermissionGranted("android.permission.READ_CALENDAR")) {
            requestReadPermission(200);
        } else {
            try {
                final JSONObject jSONObject = args.getJSONObject(0);
                final JSONObject jSONObject2 = jSONObject.getJSONObject("options");
                this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.9
                    @Override // java.lang.Runnable
                    public void run() {
                        Calendar.this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, Calendar.this.getCalendarAccessor().findEvents(Calendar.getPossibleNullString("id", jSONObject2), Calendar.getPossibleNullString("title", jSONObject), Calendar.getPossibleNullString(FirebaseAnalytics.Param.LOCATION, jSONObject), Calendar.getPossibleNullString("notes", jSONObject), jSONObject.optLong("startTime"), jSONObject.optLong("endTime"), Calendar.getPossibleNullString("calendarId", jSONObject2))));
                    }
                });
            } catch (JSONException e) {
                PrintStream printStream = System.err;
                printStream.println("Exception: " + e.getMessage());
                this.callback.error(e.getMessage());
            }
        }
    }

    private void createEvent(JSONArray args) {
        if (!calendarPermissionGranted("android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR")) {
            requestReadWritePermission(102);
            return;
        }
        try {
            final JSONObject jSONObject = args.getJSONObject(0);
            final JSONObject jSONObject2 = jSONObject.getJSONObject("options");
            this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String createEvent = Calendar.this.getCalendarAccessor().createEvent(null, Calendar.getPossibleNullString("title", jSONObject), jSONObject.getLong("startTime"), jSONObject.getLong("endTime"), Calendar.getPossibleNullString("notes", jSONObject), Calendar.getPossibleNullString(FirebaseAnalytics.Param.LOCATION, jSONObject), Long.valueOf(jSONObject2.optLong("firstReminderMinutes", -1L)), Long.valueOf(jSONObject2.optLong("secondReminderMinutes", -1L)), Calendar.getPossibleNullString("recurrence", jSONObject2), jSONObject2.optInt("recurrenceInterval", -1), Calendar.getPossibleNullString("recurrenceWeekstart", jSONObject2), Calendar.getPossibleNullString("recurrenceByDay", jSONObject2), Calendar.getPossibleNullString("recurrenceByMonthDay", jSONObject2), Long.valueOf(jSONObject2.optLong("recurrenceEndTime", -1L)), Long.valueOf(jSONObject2.optLong("recurrenceCount", -1L)), Calendar.getPossibleNullString("allday", jSONObject2), Integer.valueOf(jSONObject2.optInt("calendarId", 1)), Calendar.getPossibleNullString(ImagesContract.URL, jSONObject2));
                        if (createEvent != null) {
                            Calendar.this.callback.success(createEvent);
                        } else {
                            Calendar.this.callback.error("Fail to create an event");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Calendar", "Error creating event. Invoking error callback.", e);
            this.callback.error(e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getPossibleNullString(String param, JSONObject from) {
        if (from.isNull(param) || "null".equals(from.optString(param))) {
            return null;
        }
        return from.optString(param);
    }

    private void listEventsInRange(JSONArray args) {
        final Uri parse;
        if (!calendarPermissionGranted("android.permission.READ_CALENDAR")) {
            requestReadPermission(PERMISSION_REQCODE_LIST_EVENTS_IN_RANGE);
            return;
        }
        try {
            final JSONObject jSONObject = args.getJSONObject(0);
            long optLong = jSONObject.optLong("startTime");
            long optLong2 = jSONObject.optLong("endTime");
            if (Build.VERSION.SDK_INT >= 8) {
                parse = Uri.parse("content://com.android.calendar/instances/when/" + String.valueOf(optLong) + "/" + String.valueOf(optLong2));
            } else {
                parse = Uri.parse("content://calendar/instances/when/" + String.valueOf(optLong) + "/" + String.valueOf(optLong2));
            }
            this.cordova.getThreadPool().execute(new Runnable() { // from class: nl.xservices.plugins.Calendar.11
                @Override // java.lang.Runnable
                public void run() {
                    String str;
                    String str2;
                    String str3;
                    ContentResolver contentResolver = Calendar.this.cordova.getActivity().getContentResolver();
                    JSONArray jSONArray = new JSONArray();
                    long optLong3 = jSONObject.optLong("startTime");
                    long optLong4 = jSONObject.optLong("endTime");
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.setTime(new Date(optLong3));
                    java.util.Calendar calendar2 = java.util.Calendar.getInstance();
                    calendar2.setTime(new Date(optLong4));
                    Uri uri = parse;
                    StringBuilder sb = new StringBuilder();
                    sb.append("(deleted = 0 AND   (     (eventTimezone  = 'UTC' AND begin >=");
                    String str4 = "allDay";
                    sb.append(calendar.getTimeInMillis() + TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
                    sb.append(" AND end <=");
                    String str5 = "title";
                    String str6 = "exdate";
                    sb.append(calendar2.getTimeInMillis() + TimeZone.getDefault().getOffset(calendar2.getTimeInMillis()));
                    sb.append(")     OR      (eventTimezone <> 'UTC' AND begin >=");
                    sb.append(calendar.getTimeInMillis());
                    sb.append(" AND end <=");
                    sb.append(calendar2.getTimeInMillis());
                    sb.append(")   ))");
                    Cursor query = contentResolver.query(uri, new String[]{"calendar_id", "title", "begin", "end", "eventLocation", "allDay", "_id", "rrule", "rdate", "exdate", "event_id"}, sb.toString(), null, "begin ASC");
                    if (query != null) {
                        int i = 0;
                        while (query.moveToNext()) {
                            int i2 = i + 1;
                            try {
                                str3 = str6;
                                try {
                                    str2 = str5;
                                    try {
                                        str = str4;
                                        try {
                                            jSONArray.put(i, new JSONObject().put("calendar_id", query.getString(query.getColumnIndex("calendar_id"))).put("id", query.getString(query.getColumnIndex("_id"))).put("event_id", query.getString(query.getColumnIndex("event_id"))).put("rrule", query.getString(query.getColumnIndex("rrule"))).put("rdate", query.getString(query.getColumnIndex("rdate"))).put(str3, query.getString(query.getColumnIndex(str3))).put(str2, query.getString(query.getColumnIndex(str2))).put("dtstart", query.getLong(query.getColumnIndex("begin"))).put("dtend", query.getLong(query.getColumnIndex("end"))).put("eventLocation", query.getString(query.getColumnIndex("eventLocation")) != null ? query.getString(query.getColumnIndex("eventLocation")) : "").put(str, query.getInt(query.getColumnIndex(str))));
                                        } catch (JSONException e) {
                                            e = e;
                                            e.printStackTrace();
                                            i = i2;
                                            str6 = str3;
                                            str5 = str2;
                                            str4 = str;
                                        }
                                    } catch (JSONException e2) {
                                        e = e2;
                                        str = str4;
                                    }
                                } catch (JSONException e3) {
                                    e = e3;
                                    str = str4;
                                    str2 = str5;
                                }
                            } catch (JSONException e4) {
                                e = e4;
                                str = str4;
                                str2 = str5;
                                str3 = str6;
                            }
                            i = i2;
                            str6 = str3;
                            str5 = str2;
                            str4 = str;
                        }
                        query.close();
                    }
                    Calendar.this.callback.sendPluginResult(new PluginResult(PluginResult.Status.OK, jSONArray));
                }
            });
        } catch (JSONException e) {
            PrintStream printStream = System.err;
            printStream.println("Exception: " + e.getMessage());
            this.callback.error(e.getMessage());
        }
    }

    @Override // org.apache.cordova.CordovaPlugin
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CODE_CREATE.intValue()) {
            if (resultCode == -1 || resultCode == 0) {
                Log.d("Calendar", "onActivityResult resultcode: " + resultCode);
                this.callback.success();
                return;
            }
            Log.d("Calendar", "onActivityResult weird resultcode: " + resultCode);
            this.callback.success();
            return;
        }
        Integer num = RESULT_CODE_OPENCAL;
        if (requestCode == num.intValue()) {
            Log.d("Calendar", "onActivityResult requestCode: " + num);
            this.callback.success();
            return;
        }
        Log.d("Calendar", "onActivityResult error, resultcode: " + resultCode);
        CallbackContext callbackContext = this.callback;
        callbackContext.error("Unable to add event (" + resultCode + ").");
    }

    public static String formatICalDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }
}
