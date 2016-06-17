package com.malvinstn.testqueue;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;

import android.support.annotation.NonNull;
import android.util.Log;

public class GcmJobService extends GcmJobSchedulerService {

    private static final String TAG = "GcmJobService";

    @NonNull
    @Override
    protected JobManager getJobManager() {
        Log.d(TAG, "GcmJobService::getJobManager()");
        return MainApplication.getInstance().getJobManager();
    }
}
