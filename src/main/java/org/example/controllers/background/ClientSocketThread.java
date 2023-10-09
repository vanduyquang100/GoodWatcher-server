package org.example.controllers.background;

import org.example.helpers.PCInfoEncoder;
import org.example.views.ConnectionWindow;
import org.example.views.FileViewer;
import org.example.views.WatchedItem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientSocketThread implements Runnable {

    public final Socket client;
    public final String IpAddress;
    public final String clientName;
    public final String MacValue;
    public String notificationMessage = "Waiting for adding watched folder.";
    public DefaultListModel<String> messages;
    private WatchedItem watchedItem = null;
    private final AtomicBoolean threadStopped = new AtomicBoolean(false);
    private final BufferedReader br;
    private final BufferedWriter bw;
    private final ServerSocket serverSocket;
    private FileViewer fileViewer;

    public ClientSocketThread(ServerSocket ss, Socket client) {
        serverSocket = ss;
        this.client = client;
        try {
            IpAddress = client.getInetAddress().getHostAddress();
            System.out.println(IpAddress);
            InputStream is = client.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            OutputStream os = client.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));

            System.out.println("Talking to client.");
            String infoReceived = br.readLine();
            System.out.println("Received " + infoReceived);
            ArrayList<String> decodePCInfo = PCInfoEncoder.decodePCInfo(infoReceived);
            clientName = decodePCInfo.get(0);
            MacValue = decodePCInfo.get(1);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messages = new DefaultListModel<>();
            }
        });
    }

    public void setFolder(String folderPath, boolean select) {
        try {
            if (!select) {
                bw.write("cd:" + folderPath);
            } else {
                bw.write("path:" + folderPath);
            }
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunning() {
        return !threadStopped.get();
    }

    public void stop() {
        threadStopped.set(true);
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
       try {
            bw.newLine();
            bw.flush();

            while (true) {
                String receive = br.readLine();
                System.out.println("Received " + receive);

                if (receive.startsWith("list:")) { // list of folders
                    String input = receive.substring(5);
                    String[] chunks = input.split("\t");
                    ArrayList<String> chunksList = new ArrayList<>(Arrays.asList(chunks));
                    fileViewer.listFiles(chunksList);
                }

                if (receive.equals("0")) { // watch folder has been installed
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            notificationMessage = "No folder interaction yet.";
                            changeNotification(notificationMessage);
                            ArrayList<ConnectionWindow> dialogs = new ArrayList<>();
                            Window[] windows = Window.getWindows();
                            for (Window window : windows) {
                                if (window instanceof ConnectionWindow) {
                                    dialogs.add((ConnectionWindow) window);
                                }
                                if (window instanceof JFrame) {
                                    window.requestFocus();
                                }
                            }
                            for (ConnectionWindow dialog : dialogs) {
                                if (dialog.MacAddress.equals(MacValue)) {
                                    dialog.dispose();
                                }
                            }
                        }
                    });
                }

                if (receive.startsWith("^") || receive.startsWith("*") || receive.startsWith("$")) {
                    String decodeString = decodeToString(receive);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            messages.add(messages.size(), receive);
                            changeNotification(decodeString);
                        }
                    });
                }

                if (receive.equals("quit")) {
                    break;
                }
                if (threadStopped.get()) {
                    break;
                }
            }
            threadStopped.set(true);
        } catch (IOException e) {
            System.out.println("Cannot create stream.");
            threadStopped.set(true);

        }
    }

    private void changeNotification(String neWMessage) {
        int max = 30;
        notificationMessage = neWMessage;
        if (notificationMessage.length() > max) {
            notificationMessage = notificationMessage.substring(0, max) + "...";
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (watchedItem != null) {
                    watchedItem.changeNotification(notificationMessage);
                }
            }
        });
    }
    public static String decodeToString(String code) {
        if ((code.charAt(0)) == '$') {
            return "Watched folder has been removed.";
        }
        String result = "";
        String fileName = code.substring(3);
        if (code.contains("^+:")) {
            result += "Added new folder named ";
        } else if (code.contains("^-:")) {
            result += "Deleted folder ";
        } else if (code.contains("^^:")) {
            result += "Modified folder ";
        } else if (code.contains("*+:")) {
            result += "Added new file named ";
        } else if (code.contains("*-:")) {
            result += "Deleted file/folder ";
        } else if (code.contains("**:")) {
            result += "Modified file ";
        }
        return result + fileName;
    }

    public void connectToWatchedItem(WatchedItem watchedItem) {
        this.watchedItem = watchedItem;
    }

    public void connectToFileViewer(FileViewer fileViewer) {
        this.fileViewer = fileViewer;
    }
}
