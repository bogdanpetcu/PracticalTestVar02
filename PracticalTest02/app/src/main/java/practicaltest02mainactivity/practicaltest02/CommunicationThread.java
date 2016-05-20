package practicaltest02mainactivity.practicaltest02;

import java.net.Socket;

/**
 * Created by student on 5/20/16.
 */
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
                    String ip = socket.getInetAddress().toString();
                    String type = bufferedReader.readLine();
                    String hour = bufferedReader.readLine();
                    String minute = bufferedReader.readLine();

                    HashMap<String, Timer> data = serverThread.getData();

                    Timer timer = null;

                    if (type != null && !type.isEmpty() && type != null && !type.isEmpty()) {
                        if(type.equals("set")) {
                            data.put(socket.getInetAddress().toString(), new Timer(hour, minute));

                            printWriter.println("succesful set");
                            printWriter.flush();
                        } else if (type.equals("reset")) {
                            data.remove(data.get(socket.getInetAddress().toString()));
                            printWriter.println("succesful reset");
                            printWriter.flush();
                        } else if (type.equals("poll")) {
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpGet httpGet = new HttpGet("http://www.timeapi.org/utc/now");

                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            String content = httpClient.execute(httpGet, responseHandler);

                            printWriter.println("succesful poll");
                            printWriter.flush();
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
            }
        } else {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
        }
    }

}
