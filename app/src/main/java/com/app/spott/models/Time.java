package com.app.spott.models;

import com.app.spott.R;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

public enum Time implements Illustrable {
    MORNING("Morning", R.drawable.ic_time_sunrise),
    NOON("Noon", R.drawable.ic_time_noon),
    EVENING("Evening", R.drawable.ic_time_sunset),
    NIGHT("Night", R.drawable.ic_time_night);

    private String value;
    private int icon;
    private static HashMap<String, Time> lookup = new HashMap<>();
    private static ArrayList<String> readableNames = new ArrayList<>();
    private static ArrayList<Time> all = new ArrayList<>();

    static {
        for (Time t : EnumSet.allOf(Time.class)) {
            lookup.put(t.toString(), t);
            readableNames.add(t.toString());
            all.add(t);
        }
    }

    Time(String value, int icon) {
        this.value = value;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static List<String> getReadableNames() {
        return readableNames;
    }

    public static Time getTime(String value) {
        return lookup.get(value);
    }

    @Override
    public int getIcon() {
        return icon;
    }

    public static List<Time> getAll() {
        return all;
    }

}
