package org.example.controllers.background;

import org.example.views.ConnectionWindow;
import org.example.Main;
import org.example.helpers.IpEncoder;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GetClient implements Runnable {
    private final AtomicBoolean threadStopped = new AtomicBoolean(false);
    private final ConnectionWindow addWindow;
    private int clientIndex;
    public GetClient(ConnectionWindow theParent) {
        this.addWindow = theParent;
    }
    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(0)) {
            if (threadStopped.get()) {
                return;
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        addWindow.setCode(IpEncoder.convertToHex(InetAddress.getLocalHost().getHostAddress(), server.getLocalPort()));
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            Socket ss = server.accept();
            ClientSocketThread client = new ClientSocketThread(server, ss);
            clientIndex = Main.addClient(client);
            if (clientIndex == -1) {
                return;
            }
            addWindow.changeToAddPanel();
            Thread newThread = new Thread(client);
            newThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        threadStopped.set(true);
    }

    public int getClientIndex() {
        return clientIndex;
    }
}
