package practicaltest02mainactivity.practicaltest02;

/**
 * Created by student on 5/20/16.
 */
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String type;
    private String hour;
    private String minute;
    private TextView outputTextView;

    private Socket socket;

    public ClientThread(String address, int port, String type, String hour, String minute, TextView outputTextView) {
        this.address = address;
        this.port = port;
        this.type = type;
        this.hour = hour;
        this.minute = minute;
        this.outputTextView = outputTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
            }

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            if (bufferedReader != null && printWriter != null) {
                printWriter.println(type);
                printWriter.flush();
                printWriter.println(hour);
                printWriter.flush();
                printWriter.println(hour);
                printWriter.flush();

                String timerStatus;
                while ((timerStatus = bufferedReader.readLine()) != null) {
                    final String finalizedTimerStatus = timerStatus;
                    outputTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            outputTextView.append(outputTextView + "\n");
                        }
                    });
                }
            } else {
                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}
