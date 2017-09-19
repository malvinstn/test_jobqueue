package com.malvinstn.testqueue;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;

import android.app.Application;
import android.os.Build;
import android.util.Log;

public class MainApplication extends Application{

    private static final String TAG = "MainApplication";
    private static MainApplication sInstance;
    private JobManager mJobManager;

    public static MainApplication getInstance(){
        return sInstance;
    }

    public MainApplication() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        configure(); // Ensure creation.
    }


    public synchronized JobManager getJobManager() {
        Log.d(TAG, "MainApplication::getJobManager()");
        if (mJobManager == null) {
            configure();
        }
        return mJobManager;
    }

    private void configure() {
        Log.d(TAG, "MainApplication::configure()");
        Configuration.Builder builder = new Configuration.Builder(this)
                .minConsumerCount(0)
                .maxConsumerCount(3)
                .customLogger(new CustomLogger() {
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {
                        Log.v(TAG, String.format(text, args));
                    }
                })
                .loadFactor(3)
                .consumerKeepAlive(5);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(this,
                    JobService.class), true);
        } else {
            int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            if (enableGcm == ConnectionResult.SUCCESS) {
                builder.scheduler(GcmJobSchedulerService.createSchedulerFor(this,
                        GcmJobService.class), true);
            }
        }
        mJobManager = new JobManager(builder.build());
    }

}
