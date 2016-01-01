/**
 * Created by Umer on 11/19/2015.
 */
package com.lbpns.android.lbpnsandroid;

import android.os.AsyncTask;

import org.json.JSONArray;

public class ServerRequestTask extends AsyncTask<String, Void, Object> {
    private final static String TAG = "ServerRequestTask";
//We were using the interface instead of the abstract class but had to switch to
// abstract class since you must implement all of the methods of an interface therefore
// we can't do that while abstract class would let us implement any (not all) of the methods
// when we have to execute our task.

    public interface TaskHandler {
        public abstract boolean taskWithBoolean();

        public abstract JSONArray taskWithJSONArray();
    }

    private final TaskHandler taskHandler;

    public ServerRequestTask(TaskHandler handler) {
        this.taskHandler = handler;
    }


    @Override
    protected Object doInBackground(String... params) {
        Object result = null;
        if (this.taskHandler != null) {
            switch (params[0]) {
                case "boolean":
                    boolean a = this.taskHandler.taskWithBoolean();
                    result = a;
                    break;
                case "jsonarray":
                    JSONArray b = this.taskHandler.taskWithJSONArray();
                    result = b;
                    break;
                default:
                    break;
            }

        }
        return result;
    }

}

