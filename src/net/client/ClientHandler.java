package net.client;

import game.Board;
import gui.TicTacToeClient;
import net.packet.GamePacket;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private TicTacToeClient clientFrame;
    private Socket socket;

    public boolean isCurrentPlayer;
    public char piece;
    public ObjectInputStream input;
    public ObjectOutputStream output;

    public ClientHandler(Socket socket, TicTacToeClient clientFrame) {
        this.socket = socket;
        this.clientFrame = clientFrame;
        getIOStreams();
    }

    private void getIOStreams() {
        try {
            System.out.println("Getting IO Streams...");
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            piece = (Character) input.readObject();
            JOptionPane.showMessageDialog(clientFrame, "You are player " + piece);
            System.out.println("Got IO Streams...");
        } catch (IOException ioex) {
            System.err.println("Error connecting to " + socket.getInetAddress().getHostAddress());
        } catch (ClassNotFoundException cnfex) {
            System.err.println("Unknown object data");
        }
    }

    public void run() {
        while (true) {
            try {
                Object obj = input.readObject();
                if (obj instanceof Boolean) {
                    isCurrentPlayer = (Boolean)obj;
                } else if (obj instanceof GamePacket) {
                    GamePacket gp = (GamePacket) obj;
                    clientFrame.setPiece(gp);
                } else if (obj instanceof String) {
                    String msg = (String) obj;
                    if (msg.contains("SERVER$RESET")) {
                        clientFrame.reset();
                    } else {
                        JOptionPane.showMessageDialog(clientFrame, msg);
                    }
                }
            } catch (IOException ioex) {
                System.err.println("Error reading socket");
            } catch (ClassNotFoundException cnfex) {
                System.err.println("Unknown object data");
            }
        }
    }
}
