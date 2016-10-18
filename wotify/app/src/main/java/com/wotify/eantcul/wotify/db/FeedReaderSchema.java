package com.wotify.eantcul.wotify.db;

import android.provider.BaseColumns;

public final class FeedReaderSchema {
    public FeedReaderSchema() {}

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "alarms";
        public static final String ALARM_NAME = "name";
        public static final String ALARM_TIME = "time";
        public static final String ALARM_DAYS = "days";

    }
}