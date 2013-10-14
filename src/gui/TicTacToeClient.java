package gui;

import net.client.ClientHandler;
import net.packet.GamePacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class TicTacToeClient extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int BOARD_SIZE = 3;
    public static final int PORT = 12345;

    private ClientHandler clientHandler;
    private JPanel gridPanel;
    private JPanel panel;
    private JButton[][] gameBoard;
    private JTextField hostInput;
    private JButton connectButton;

    public boolean gridEnabled = false;

    public TicTacToeClient() {
        super("Tic Tac Toe Client");
        initFrame();
    }

    public static void main(String[] args) {
        new TicTacToeClient();
    }

    private void initFrame() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridPanel = new JPanel();
        initGameBoard();
        panel = new JPanel();
        hostInput = new JTextField("localhost");
        hostInput.setPreferredSize(new Dimension(200, 30));
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ButtonHandler());
        panel.add(hostInput);
        panel.add(connectButton);
        add(gridPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        pack();
        resetGrid();
        setVisible(true);
    }

    private void initGameBoard() {
        gameBoard = new JButton[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                JButton button = new JButton();
               // button.setEnabled(false);
                button.addActionListener(new ButtonHandler());
                gameBoard[i][j] = button;
            }
        }
        addBoard();
    }

    private void addBoard() {
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        grid.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        for (JButton[] array : gameBoard) {
            for (JButton button : array) {
                grid.add(button);
            }
        }
        gridPanel.add(grid);
    }

    private void connectToServer() {

        if (clientHandler == null) {
            try {
                hostInput.setEnabled(false);
                connectButton.setEnabled(false);
                Socket socket = new Socket(hostInput.getText(), PORT);
                System.out.println("Connected to " + socket.getInetAddress().getHostAddress());
                clientHandler = new ClientHandler(socket, this);
                new Thread(clientHandler).start();
            } catch (IOException ex) {
                hostInput.setEnabled(true);
                connectButton.setEnabled(true);
                System.err.println(ex.getMessage());
            }
        }
    }

    public void enableGrid(boolean state) {
        for (JButton[] array : gameBoard) {
            for (JButton button : array) {
                button.setEnabled(state);
            }
        }
    }

    public void reset() {
        if (clientHandler != null) {
            clientHandler = null;
            hostInput.setEnabled(true);
            connectButton.setEnabled(true);
            resetGrid();
        }
    }

    private class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == connectButton) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        connectToServer();
                    }
                }).start();
            } else {
                if (clientHandler != null && clientHandler.isCurrentPlayer) {
                    try {
                        for (int i = 0; i < gameBoard.length; i++) {
                            for (int j = 0; j < gameBoard[0].length; j++) {
                                if (e.getSource() == gameBoard[i][j]) {
                                    clientHandler.output.writeObject(new GamePacket(clientHandler.piece, i, j));
                                    break;
                                }
                            }
                        }
                    } catch (IOException ioex) {
                        System.err.println("Error writing to socket");
                    }
                }
            }
        }
    }

    private void resetGrid() {
        for (JButton array[] : gameBoard) {
            for (JButton button : array) {
                button.setBackground(Color.WHITE);
                button.setIcon(null);
            }
        }
    }

    public void setPiece(GamePacket gp) {
        char piece = gp.getGamePiece();
        int x = gp.getxPos();
        int y = gp.getyPos();

        JButton button = gameBoard[x][y];
        // test
        if (piece == 'X') {
            button.setIcon(new ImageIcon("img/x.png"));
        } else {
            button.setIcon(new ImageIcon("img/o.png"));
        }
    }

    public synchronized boolean isGridEnabled() {
        return gridEnabled;
    }
}
