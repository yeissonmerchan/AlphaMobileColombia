package com.example.alphamobilecolombia.mvp.activity;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import com.example.alphamobilecolombia.utils.crashlytics.LogError;

public class ImagesBackgroundJob extends JobService {

    private static final String TAG = "ExampleJobService";

    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

        doBackgroundWork(jobParameters);

        return true;
    }



    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    Toast.makeText(ImagesBackgroundJob.this, "run: " + i, Toast.LENGTH_SHORT).show();
                    /*Log.d(TAG, "run: " + i);*/
                    if (jobCancelled) {
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }




    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;

        return false;
    }
}
