package com.app.spott.models;

import com.app.spott.exceptions.ActivityModelException;
import com.app.spott.exceptions.ModelException;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

@ParseClassName("Activity")
public class Activity extends Model {

    private static final String TAG = Activity.class.getSimpleName();
    private static final String USER = "user";
    private static final String ACTIVITY_TYPE = "activity_type";
    private static final String TIME = "time";
    private static final String FREQUENCY = "frequency";
    private static final String LOCATION = "location";

    private static ParseQuery<Activity> query;

    @Override
    public String getLogTag() {
        return TAG;
    }

    public User getUser() {
        return (User) get(USER);
    }

    public void setUser(User user) {
        put(USER, user);
    }

    public ActivityType getActivityType() {
        return ActivityType.valueOf(getString(ACTIVITY_TYPE));
    }

    public void setActivityType(ActivityType activityType) {
        put(ACTIVITY_TYPE, activityType.toString());
    }

    public Time getTime() {
        return Time.valueOf(getString(TIME));
    }

    public void setTime(Time time) {
        put(TIME, time.toString());
    }

    public Frequency getFrequency() {
        return Frequency.valueOf(getString(FREQUENCY));
    }

    public void setFrequency(Frequency f) {
        put(FREQUENCY, f.toString());
    }

    public Location getLocation(){
        return (Location) get(LOCATION);
    }

    public void setLocation(Location loc){
        put(LOCATION, loc);
    }

    private static ParseQuery<Activity> getQuery(){
        ParseQuery<Activity> q = ParseQuery.getQuery(Activity.class);
        q.include(LOCATION);
        q.include(USER);
        return q;
    }
    public void getMatchedActivities(final FindCallback<Activity> findCallback) throws ParseException {
//        maybe use innerquery for optimization
        getLocation().getNearByLocations(new FindCallback<Location>() {
            @Override
            public void done(List<Location> nearByLocations, ParseException e) {
                query = getQuery();
                query.whereContainedIn(LOCATION, nearByLocations);
                query.setLimit(10);
                query.findInBackground(findCallback);
            }
        });
    }

    public static void getForUser(User user, FindCallback<Activity> findCallback) throws ParseException {
        query = getQuery();
        query.whereEqualTo(USER, user);
        query.findInBackground(findCallback);
    }

    public static void getAll(FindCallback<Activity> findCallback) throws ParseException {
        query = getQuery();
        query.findInBackground(findCallback);
    }

    @Override
    public void saveModel() throws ModelException, ParseException {
        if (this.getActivityType() == null || this.getLocation() == null || this.getUser() == null)
            throw new ActivityModelException();

        this.getLocation().saveModel();
        super.saveModel();
    }
}
