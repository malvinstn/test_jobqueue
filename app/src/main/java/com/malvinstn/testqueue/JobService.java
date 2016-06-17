package com.malvinstn.testqueue;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;

import android.support.annotation.NonNull;
import android.util.Log;

public class JobService extends FrameworkJobSchedulerService {
    private static final String TAG = JobService.class.getName();

    @NonNull
    @Override
    protected JobManager getJobManager() {
        Log.d(TAG, "getJobManager() called with: " + "");
        return MainApplication.getInstance().getJobManager();
    }
}
