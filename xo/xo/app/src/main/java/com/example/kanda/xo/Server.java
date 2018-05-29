package com.example.kanda.xo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by KANDA on 29-May-18.
 */

public class Server {
    private int serverPort;
    XoActivity xoActivity;

    public Server(XoActivity xoActivity, int serverPort) {
        this.serverPort = serverPort;
        this.xoActivity = xoActivity;
        ServerWorker serverWorker = new ServerWorker();
        serverWorker.start();
    }

    private class ServerWorker extends Thread {
        int count = 0;

        @Override
        public void run() {

            try {
                ServerSocket serverSocket = new ServerSocket(serverPort);
                while (true) {
                    final Socket clientSocket = serverSocket.accept();
                    count++;
                    xoActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           /* mainActivity.txtMonitor.append("Client#"+ count
                                    + clientSocket.getInetAddress()+":"
                                    +clientSocket.getPort()+"\n");*/
                        }
                    });
                    ServerCommunication comm = new ServerCommunication(clientSocket);
                    comm.start();
                }
            } catch (IOException e) {

            }
        }
    }

    private class ServerCommunication extends Thread {
        Socket clientSocket;

        public ServerCommunication(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                final String msg = input.readUTF();
                output.writeUTF("ACK 250 " + xoActivity.ipAddress);

                xoActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String msgs[] = msg.split(" ");
                        if (msgs[0].equals("MSG")) {
                            String datas[] = msgs[1].split("-");
                            setResponseAction(datas[0], datas[1], msgs[2]);
                            xoActivity.nameAnemy.setText(msgs[2]);
                        }
                    }
                });

            } catch (IOException e) {

            }

        }
    }

    private void setResponseAction(String btn, String play, String user) {
        switch (btn) {
            case "btn_01":
                xoActivity.board.set(0, play);
                xoActivity.setResponseAction(xoActivity.btn_01, play);
                break;
            case "btn_02":
                xoActivity.board.set(1, play);
                xoActivity.setResponseAction(xoActivity.btn_02, play);
                break;
            case "btn_03":
                xoActivity.board.set(2, play);
                xoActivity.setResponseAction(xoActivity.btn_03, play);
                break;
            case "btn_04":
                xoActivity.board.set(3, play);
                xoActivity.setResponseAction(xoActivity.btn_04, play);
                break;
            case "btn_05":
                xoActivity.board.set(4, play);
                xoActivity.setResponseAction(xoActivity.btn_05, play);
                break;
            case "btn_06":
                xoActivity.board.set(5, play);
                xoActivity.setResponseAction(xoActivity.btn_06, play);
                break;
            case "btn_07":
                xoActivity.board.set(6, play);
                xoActivity.setResponseAction(xoActivity.btn_07, play);
                break;
            case "btn_08":
                xoActivity.board.set(7, play);
                xoActivity.setResponseAction(xoActivity.btn_08, play);
                break;
            case "btn_09":
                xoActivity.board.set(8, play);
                xoActivity.setResponseAction(xoActivity.btn_09, play);
                break;
        }
        xoActivity.checkWinner(play,user);
    }

    public int getPort() {
        return serverPort;
    }

}
