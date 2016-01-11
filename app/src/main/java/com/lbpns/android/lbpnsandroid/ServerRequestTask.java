/**
 * Created by Umer on 11/19/2015.
 */
package com.lbpns.android.lbpnsandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;

public class ServerRequestTask extends AsyncTask<String, Void, Object> {
    private final static String TAG = "ServerRequestTask";
    private ProgressBar progressBar;
//    private ProgressDialog progressDialog;
    private Activity context;
    private ProgressDialog progressDialog;

//We were using the interface instead of the abstract class but had to switch to
// abstract class since you must implement all of the methods of an interface therefore
// we can't do that while abstract class would let us implement any (not all) of the methods
// when we have to execute our task.

    public interface TaskHandler {
        public abstract boolean taskWithBoolean();

        public abstract JSONArray taskWithJSONArray();

        public abstract void  onTaskCompletion(JSONArray jsonArray);

    }

    private final TaskHandler taskHandler;

    public ServerRequestTask(Activity activity,TaskHandler handler) {
        this.taskHandler = handler;
        this.context = activity;
//        this.taskCompletion =

        progressDialog = new ProgressDialog(context);
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading Wait kero!");
        progressDialog.show();

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

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        JSONArray jsonArray = (JSONArray) o;
        this.taskHandler.onTaskCompletion(jsonArray);
        progressDialog.dismiss();

    }
}

