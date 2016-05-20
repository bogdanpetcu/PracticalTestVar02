package practicaltest02mainactivity.practicaltest02;

import java.net.Socket;

/**
 * Created by student on 5/20/16.
 */
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);
                if (bufferedReader != null && printWriter != null) {
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client");
                    String type = bufferedReader.readLine();
                    String informationType = bufferedReader.readLine();

                    HashMap<String, Timer> data = serverThread.getData();

                    Timer timer = null;

                    if (type != null && !type.isEmpty() && informationType != null && !informationType.isEmpty()) {
                        if (data.containsKey(type)) {
                            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the cache...");
                            timer = data.get(type);
                        } else {
                            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, type));
                            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                            httpPost.setEntity(urlEncodedFormEntity);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();


                        }

                        if (timer != null) {
                            String result = null;

                            printWriter.println(result);
                            printWriter.flush();
                        } else {
                            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Weather Forecast information is null!");
                        }

                    } else {
                        Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type)!");
                    }
                } else {
                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
                }
                socket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            } catch (JSONException jsonException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + jsonException.getMessage());
                if (Constants.DEBUG) {
                    jsonException.printStackTrace();
                }
            }
        } else {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
        }
    }

}
