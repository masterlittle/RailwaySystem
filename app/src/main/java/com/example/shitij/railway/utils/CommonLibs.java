package com.example.shitij.railway.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Shitij on 23/09/15.
 */
public class CommonLibs {

    public static class TeamDetails {
        public static String TEAM_NUMBER = "teamNumber";
    }

    public static class Priority {

        /**
         * The interface Priority constants.
         */
        @Retention(RetentionPolicy.SOURCE)
        @IntDef({VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH})
        public @interface PriorityConstants {
        }

        /**
         * The constant VERY_LOW.
         */
        public static final int VERY_LOW = 0;
        /**
         * The constant LOW.
         */
        public static final int LOW = 1;
        /**
         * The constant MEDIUM.
         */
        public static final int MEDIUM = 2;
        /**
         * The constant HIGH.
         */
        public static final int HIGH = 3;
        /**
         * The constant VERY_HIGH.
         */
        public static final int VERY_HIGH = 4;
    }

    public static class FragmentId{

    }

    public static class VolleyConstants {
        /**
         * The constant DEFAULT_MAX_RETRIES.
         */
        public static final int DEFAULT_MAX_RETRIES = 2;
        /**
         * The constant DEFAULT_BACKOFF_MULT.
         */
        public static final float DEFAULT_BACKOFF_MULT = 2.0f;
        /**
         * The constant HIGH_TIMEOUT_MS.
         */
        public static final int DEFAULT_TIMEOUT_MS = 500000;
    }

    public static class API_KEYS{
        public static String PUBLIC_KEY = "nhwmu2097";
        public static String DIRECTIONS_KEY = "AIzaSyD9MWFu0uGHloGjepafU7RWIwPgqOJd1Wc";
        public static String DIRECTIONS_KEY_ANDROID = "AIzaSyA719CgwNjYTGjO816pYBldDrhsRxJSuXw";
    }

    public static class SharedPrefsKeys{
        public static String TEAM_NUMBER = "team_number";
    }

    public static class Roles{
        public static String ROLE_ADMINISTRATOR = "Administrator";
        public static String ROLE_MODERATOR = "Moderator";
        public static String ROLE_NORMAL = "Normal";
    }



}
