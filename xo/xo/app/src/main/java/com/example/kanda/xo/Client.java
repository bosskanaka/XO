package com.example.kanda.xo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by KANDA on 29-May-18.
 */

public class Client extends AsyncTask<Void, Void, Void> {

    private int port;
    private String ipAddress, message, response;

    public Client(String ipAddress, int post, String message) {
        this.ipAddress = ipAddress;
        this.port = post;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket(ipAddress, port);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("MSG " + message);
            response = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("send", "onPostExecute: " + response);
    }
}

