package com.malvinstn.testqueue;

import com.birbit.android.jobqueue.CancelReason;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class CancellationJob extends Job {

    private static final int JOB_PRIORITY = 100;
    private static final int CANCELLATION_RETRY_LIMIT = 3;
    private static final String TAG = "CancellationJob";
    private final String mId;

    public CancellationJob(String id) {
        super(new Params(JOB_PRIORITY)
                .requireNetwork()
                .persist()
                .singleInstanceBy("id:" + id));
        mId = id;
        Log.d(TAG, "CancellationJob() called with: " + "id = [" + id + "]");
    }

    @Override
    public void onAdded() {
        Log.d(TAG, "onAdded() called with id: " + mId + "");
    }

    @Override
    public void onRun() throws Throwable {
        Log.d(TAG, "onRun: Starting " + mId);
        Thread.sleep(20000);
        Log.d(TAG, "onRun: Finished " + mId);
    }

    @Override
    protected void onCancel(@CancelReason int cancelReason, @Nullable Throwable throwable) {
        // Job has been cancelled.
        Log.d(TAG, "onCancel() called with: " + "cancelReason = [" + cancelReason + "], throwable = [" + throwable + "], id = [" + mId + "]");
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        Log.d(TAG, "shouldReRunOnThrowable() called with: " + "throwable = [" + throwable + "], runCount = [" + runCount + "], maxRunCount = [" + maxRunCount + "], id = [" + mId + "]");
        // Create exponential retry.
        return RetryConstraint.createExponentialBackoff(runCount, 60000);
    }

    @Override
    protected int getRetryLimit() {
        return CANCELLATION_RETRY_LIMIT;
    }
}
