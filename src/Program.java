import game.Board;

public class Program {
    public static void main(String[] args) {
        Board b = new Board(3, 3);
        b.insertPos(0, 2, 'x');
        b.insertPos(1, 1, 'x');
        b.insertPos(2, 0, 'x');
        b.display();
        if (b.insertPos(0, 0, 'x')) {
            System.out.println("Insert is bueno");
        } else {
            System.out.println("Cool story bro");
        }
        if (b.isWinner('x')) System.out.println("Game over!");
        else System.out.println("No winner");

    }
}
