package com.malvinstn.testqueue;

import com.birbit.android.jobqueue.JobManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobManager jobManager = MainApplication.getInstance().getJobManager();
                jobManager.addJobInBackground(new CancellationJob("" + (++counter)));
            }
        });
    }
}
