import java.util.ArrayList;
import java.util.Scanner;

class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();

        System.out.println("Choisissez votre marque (X ou O) : ");
        String input = scanner.next().toUpperCase();
        Mark human = input.equals("X") ? Mark.X : Mark.O;
        Mark cpu   = (human == Mark.X) ? Mark.O : Mark.X;
        CPUPlayer cpuPlayer = new CPUPlayer(cpu);

        System.out.println("Choisissez l'algorithme du CPU (1 = Minimax, 2 = Alpha-Beta) : ");
        boolean useAB = scanner.next().equals("2");

        System.out.println("Vous jouez : " + human + " | CPU joue : " + cpu + " | Algo : " + (useAB ? "Alpha-Beta" : "Minimax"));
        System.out.println("Entrez vos coups sous la forme : ligne colonne (0-2)\n");

        Mark current = Mark.X;
        while (!board.isGameOver()) {
            board.display();
            if (current == human) {
                Move move = null;
                while (move == null) {
                    System.out.print("Votre coup (ligne colonne) : ");
                    int row = scanner.nextInt();
                    int col = scanner.nextInt();
                    if (row < 0 || row > 2 || col < 0 || col > 2) {
                        System.out.println("Case invalide, entrez des valeurs entre 0 et 2.");
                    } else {
                        Move candidate = new Move(row, col);
                        boolean libre = false;
                        for (Move m : board.generateMoves())
                            if (m.getRow() == row && m.getCol() == col) { libre = true; break; }
                        if (!libre) System.out.println("Case deja jouee, choisissez une autre.");
                        else move = candidate;
                    }
                }
                board.play(move, human);
            } else {
                System.out.println("CPU réfléchit...");
                ArrayList<Move> moves = useAB ? cpuPlayer.getNextMoveAB(board) : cpuPlayer.getNextMoveMinMax(board);
                Move chosen = moves.get(0);
                board.play(chosen, cpu);
                System.out.println("CPU joue : (" + chosen.getRow() + ", " + chosen.getCol() + ")");
            }
            current = (current == Mark.X) ? Mark.O : Mark.X;
            System.out.println();
        }

        board.display();
        int result = board.evaluate(human);
        if (result == 100)       System.out.println("Vous avez gagne !");
        else if (result == -100) System.out.println("Le CPU a gagne !");
        else                     System.out.println("Match nul !");

        scanner.close();
    }
}
