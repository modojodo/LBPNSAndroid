/**
 * Created by Umer on 11/19/2015.
 */
package com.lbpns.android.lbpnsandroid;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class ServerRequestTask extends AsyncTask<Void, Void, Boolean> {
    private final static String TAG = "ServerRequestTask";

    public interface TaskHandler {
        public boolean task();
    }

    private final TaskHandler taskHandler;

    public ServerRequestTask(TaskHandler handler) {
        this.taskHandler = handler;
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        boolean result = false;
        if (this.taskHandler != null) {
            result = this.taskHandler.task();
        }
        return result;
    }


}

