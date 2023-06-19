package nl.xservices.plugins.accessor;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.EnumMap;
import nl.xservices.plugins.accessor.AbstractCalendarAccessor;
import org.apache.cordova.CordovaInterface;
/* loaded from: classes.dex */
public class CalendarProviderAccessor extends AbstractCalendarAccessor {
    public CalendarProviderAccessor(CordovaInterface cordova) {
        super(cordova);
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    protected EnumMap<AbstractCalendarAccessor.KeyIndex, String> initContentProviderKeys() {
        EnumMap<AbstractCalendarAccessor.KeyIndex, String> enumMap = new EnumMap<>(AbstractCalendarAccessor.KeyIndex.class);
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.CALENDARS_ID, (AbstractCalendarAccessor.KeyIndex) "_id");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.IS_PRIMARY, (AbstractCalendarAccessor.KeyIndex) "isPrimary");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.CALENDARS_NAME, (AbstractCalendarAccessor.KeyIndex) AppMeasurementSdk.ConditionalUserProperty.NAME);
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.CALENDARS_DISPLAY_NAME, (AbstractCalendarAccessor.KeyIndex) "calendar_displayName");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.CALENDARS_VISIBLE, (AbstractCalendarAccessor.KeyIndex) "visible");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_ID, (AbstractCalendarAccessor.KeyIndex) "_id");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_CALENDAR_ID, (AbstractCalendarAccessor.KeyIndex) "calendar_id");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_DESCRIPTION, (AbstractCalendarAccessor.KeyIndex) "description");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_LOCATION, (AbstractCalendarAccessor.KeyIndex) "eventLocation");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_SUMMARY, (AbstractCalendarAccessor.KeyIndex) "title");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_START, (AbstractCalendarAccessor.KeyIndex) "dtstart");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_END, (AbstractCalendarAccessor.KeyIndex) "dtend");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_RRULE, (AbstractCalendarAccessor.KeyIndex) "rrule");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.EVENTS_ALL_DAY, (AbstractCalendarAccessor.KeyIndex) "allDay");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.INSTANCES_ID, (AbstractCalendarAccessor.KeyIndex) "_id");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.INSTANCES_EVENT_ID, (AbstractCalendarAccessor.KeyIndex) "event_id");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.INSTANCES_BEGIN, (AbstractCalendarAccessor.KeyIndex) "begin");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.INSTANCES_END, (AbstractCalendarAccessor.KeyIndex) "end");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.ATTENDEES_ID, (AbstractCalendarAccessor.KeyIndex) "_id");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.ATTENDEES_EVENT_ID, (AbstractCalendarAccessor.KeyIndex) "event_id");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.ATTENDEES_NAME, (AbstractCalendarAccessor.KeyIndex) "attendeeName");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.ATTENDEES_EMAIL, (AbstractCalendarAccessor.KeyIndex) "attendeeEmail");
        enumMap.put((EnumMap<AbstractCalendarAccessor.KeyIndex, String>) AbstractCalendarAccessor.KeyIndex.ATTENDEES_STATUS, (AbstractCalendarAccessor.KeyIndex) "attendeeStatus");
        return enumMap;
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    protected Cursor queryAttendees(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return this.cordova.getActivity().getContentResolver().query(CalendarContract.Attendees.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    protected Cursor queryCalendars(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return this.cordova.getActivity().getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    protected Cursor queryEvents(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return this.cordova.getActivity().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    protected Cursor queryEventInstances(long startFrom, long startTo, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Uri.Builder buildUpon = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(buildUpon, startFrom);
        ContentUris.appendId(buildUpon, startTo);
        return this.cordova.getActivity().getContentResolver().query(buildUpon.build(), projection, selection, selectionArgs, sortOrder);
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    public boolean deleteEvent(Uri eventsUri, long startFrom, long startTo, String title, String location, String notes) {
        return super.deleteEvent(eventsUri == null ? Uri.parse("content://com.android.calendar/events") : eventsUri, startFrom, startTo, title, location, notes);
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    public boolean deleteEventById(Uri eventsUri, long id, long fromDate) {
        if (eventsUri == null) {
            eventsUri = Uri.parse("content://com.android.calendar/events");
        }
        return super.deleteEventById(eventsUri, id, fromDate);
    }

    @Override // nl.xservices.plugins.accessor.AbstractCalendarAccessor
    public String createEvent(Uri eventsUri, String title, long startTime, long endTime, String description, String location, Long firstReminderMinutes, Long secondReminderMinutes, String recurrence, int recurrenceInterval, String recurrenceWeekstart, String recurrenceByDay, String recurrenceByMonthDay, Long recurrenceEndTime, Long recurrenceCount, String allday, Integer calendarId, String url) {
        return super.createEvent(eventsUri == null ? Uri.parse("content://com.android.calendar/events") : eventsUri, title, startTime, endTime, description, location, firstReminderMinutes, secondReminderMinutes, recurrence, recurrenceInterval, recurrenceWeekstart, recurrenceByDay, recurrenceByMonthDay, recurrenceEndTime, recurrenceCount, allday, calendarId, url);
    }
}
