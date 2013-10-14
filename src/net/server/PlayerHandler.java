package net.server;

import game.Board;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerHandler {

    private TicTacToeServer gameServer;
    private Board board;
    private Socket socket;

    public char piece;
    public ObjectInputStream input;
    public ObjectOutputStream output;

    public PlayerHandler(Socket socket, TicTacToeServer gameServer, Board board, char piece) {
        this.socket = socket;
        this.gameServer = gameServer;
        this.board = board;
        this.piece = piece;
        getIOStreams();
    }

    public void getIOStreams() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Got IO Streams for " + socket.getInetAddress().getHostAddress());
            System.out.println("Sending player " + piece + " starting game info");
            output.writeObject(piece);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void close() {
        try {
            if (output != null) {
                output.close();
            }

            if (input != null) {
                input.close();
            }

            if (socket != null) {
                socket.close();
            }

            System.out.println("Successfully closed player " + piece);
        } catch (IOException ioex) {
            System.err.println("Error cleaning up player " + piece);
        }
    }
}
