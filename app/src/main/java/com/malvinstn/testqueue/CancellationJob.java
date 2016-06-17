package com.malvinstn.testqueue;

import com.birbit.android.jobqueue.CancelReason;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

public class CancellationJob extends Job {

    private static final int JOB_PRIORITY = 100;
    private static final int CANCELLATION_RETRY_LIMIT = 3;
    private static final String TAG = "CancellationJob";
    private final String mId;
    private final String mText;
    private final JobEnum mEnum;
    private final Date mTimestamp;
    private final JobEnum2 mEnum2;

    public CancellationJob(String id, JobEnum anEnum, String text, Date timestamp, JobEnum2 enum2) {
        super(new Params(JOB_PRIORITY)
                .requireNetwork()
                .persist()
                .groupBy("cancel"));

        mId = id;
        mEnum = anEnum;
        mText = text;
        mTimestamp = timestamp;
        mEnum2 = enum2;
        Log.d(TAG, "CancellationJob() called with: " + "id = [" + id + "]");
    }

    @Override
    public void onAdded() {
        Log.d(TAG, "onAdded() called with id: " + mId + "");
    }

    @Override
    public void onRun() throws Throwable {
        throw new RuntimeException("Forced Exception");
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
