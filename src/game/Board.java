package game;

public class Board {

    private char[][] board;

    public Board(int rows, int cols) {
        board = new char[rows][cols];
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private boolean checkPos(int x, int y) {
        return board[x][y] == ' ';
    }

    public boolean insertPos(int x, int y, char piece) {
        if (checkPos(x, y)) {
            board[x][y] = piece;
            return true;
        }
        return false;
    }

    public boolean isWinner(char piece) {
        return (vert(piece) || horiz(piece) || diagonal(piece));
    }

    public boolean diagonal(char piece) {
        return (ldiag(piece) || rdiag(piece));
    }

    public boolean ldiag(char piece) {
        int counter = 0;
        for (int i = 0, j = board.length - 1; i < board.length; i++, j--) {
            if (board[i][j] == piece) {
                counter++;
            }
        }
        return (counter == board.length);
    }

    public boolean rdiag(char piece) {
        int counter = 0;
        for (int i = 0, j = 0; i < board.length; i++, j++) {
            if (board[i][j] == piece) {
                counter++;
            }
        }
        return (counter == board.length);
    }

    public boolean horiz(char piece) {
        for (int i = 0; i < board.length; i++) {
            int counter = 0;
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == piece) {
                    counter++;
                }
            }
            if (counter == board.length) {
                return true;
            }
        }
        return false;
    }

    public boolean vert(char piece) {
        for (int i = 0; i < board.length; i++) {
            int counter = 0;
            for (int j = 0; j < board[0].length; j++) {
                if (board[j][i] == piece) {
                    counter++;
                }
            }
            if (counter == board.length) {
                return true;
            }
        }
        return false;
    }

    public void display() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (j != board[0].length - 1) {
                    System.out.print(board[i][j] + "|");
                } else {
                    System.out.println(board[i][j]);
                }
            }
            if (i != board.length - 1) {
                System.out.println("-+-+-");
            }
        }
        System.out.println("\n\n");
    }
}
