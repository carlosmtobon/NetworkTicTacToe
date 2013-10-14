package net.packet;

import java.io.Serializable;

public class GamePacket implements Serializable {
    private char gamePiece;
    private int xPos;
    private int yPos;

    public GamePacket(char gamePiece, int xPos, int yPos) {
        this.gamePiece = gamePiece;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public char getGamePiece() {
        return gamePiece;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return  yPos;
    }

    public String toString() {
        return "piece: '" + gamePiece + "', xPos: " + xPos + ", yPos: " + yPos;
    }

    public static void main(String[] args) {
        System.out.println(new GamePacket('x', 3, 2));
    }
}
