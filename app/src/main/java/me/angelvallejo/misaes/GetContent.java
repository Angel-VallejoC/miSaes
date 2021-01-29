package me.angelvallejo.misaes;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;

import me.angelvallejo.misaes.parser.SaesParser;

/**
 * Class with the unique purpose of executing the methods from {@link SaesParser}
 * on a background thread
 */
public class GetContent extends AsyncTask<Object, Void, Pair> {
    private static final String TAG = "GetContent";
    private GetContentListener mListener;

    public GetContent(GetContentListener callback) {
        mListener = callback;
    }

    /**
     * Calls the corresponding method on {@link SaesParser} depending on the action
     * passed as parameter.
     *
     * @param parameters Parameters needed to execute the task.
     *                   It is always required to pass as first parameter the type of
     *                   action that needs to be performed.
     * @return A pair where p.first is the type of action performed and p.second is an object
     * returned from the parser
     */
    @Override
    protected Pair doInBackground(Object... parameters) {

        Log.d(TAG, "doInBackground: parameters " + parameters);

        if (parameters == null || parameters[0] == null) {
            throw new IllegalArgumentException("A GetContent.Action must be provided");
        }

        GetContent.Action action = (Action) parameters[0];
        try {
            switch (action) {
                case LOAD_LOGIN:
                    return Pair.create(parameters[0], SaesParser.getInstance().loadLoginPage());

                case LOGIN:
                    return Pair.create(
                            parameters[0],
                            SaesParser.getInstance().login(
                                    (String) parameters[1],
                                    (String) parameters[2],
                                    (String) parameters[3]
                            )
                    );

                case GET_STUDENT_INFO:
                    return Pair.create(parameters[0], SaesParser.getInstance().getStudentInfo());
            }
        } catch (IOException e) {
            Log.d(TAG, "doInBackground: " + e.getStackTrace());
        }


        return null;
    }

    /**
     * Calls the class listener with the result of doInBackground method
     *
     * @param result A pair where p.first is the type of action performed and p.second is an object
     *               returned from the parser
     */
    @Override
    protected void onPostExecute(Pair result) {
        Log.d(TAG, "onPostExecute: result = ");
        if (mListener != null && result != null)
            mListener.onResultReady((GetContent.Action) result.first, result.second);
    }

    enum Action {
        LOAD_LOGIN,
        LOGIN,
        GET_KARDEX,
        GET_STUDENT_INFO
    }

    interface GetContentListener {
        <T> void onResultReady(GetContent.Action action, T result);
    }

}
