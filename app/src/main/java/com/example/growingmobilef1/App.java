package com.example.growingmobilef1;


import android.app.Application;

import com.bugfender.sdk.Bugfender;


public class App extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            Bugfender.init(this, "E1XA2YdMqJeHiBIwl0fdnPvD7XsXY6XP", BuildConfig.DEBUG);
            Bugfender.enableCrashReporting();
            Bugfender.enableUIEventLogging(this);
            Bugfender.enableLogcatLogging();
        }
    }

