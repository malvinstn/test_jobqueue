package com.malvinstn.testqueue;

import com.birbit.android.jobqueue.JobManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobManager jobManager = MainApplication.getInstance().getJobManager();
                jobManager.addJobInBackground(new CancellationJob(UUID.randomUUID().toString(), JobEnum.ENUM_A, "lorem ipsum dolor sit amet.", new Date(), JobEnum2.JOB_1));
                jobManager.addJobInBackground(new CancellationJob(UUID.randomUUID().toString(), JobEnum.ENUM_B, "lorem ipsum dolor sit amet.", new Date(), JobEnum2.JOB_1));
                jobManager.addJobInBackground(new CancellationJob(UUID.randomUUID().toString(), JobEnum.ENUM_A, "lorem ipsum dolor sit amet.", new Date(), JobEnum2.JOB_2));
                jobManager.addJobInBackground(new CancellationJob(UUID.randomUUID().toString(), JobEnum.ENUM_B, "lorem ipsum dolor sit amet.", new Date(), JobEnum2.JOB_2));
                jobManager.addJobInBackground(new CancellationJob(UUID.randomUUID().toString(), JobEnum.ENUM_B, "lorem ipsum dolor sit amet.", new Date(), JobEnum2.JOB_2));
            }
        });
    }
}
