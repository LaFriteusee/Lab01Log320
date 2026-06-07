import java.util.ArrayList;

class Test {
    public static void main(String[] args) {
        // Test 1 : plateau vide, CPU joue X
        Board board = new Board();
        CPUPlayer cpu = new CPUPlayer(Mark.X);

        ArrayList<Move> movesMinMax = cpu.getNextMoveMinMax(board);
        System.out.println("=== Minimax (plateau vide, X) ===");
        System.out.println("Noeuds explores : " + cpu.getNumOfExploredNodes());
        System.out.print("Meilleurs coups : ");
        for (Move m : movesMinMax)
            System.out.print("(" + m.getRow() + "," + m.getCol() + ") ");
        System.out.println();

        ArrayList<Move> movesAB = cpu.getNextMoveAB(board);
        System.out.println("=== Alpha-Beta (plateau vide, X) ===");
        System.out.println("Noeuds explores : " + cpu.getNumOfExploredNodes());
        System.out.print("Meilleurs coups : ");
        for (Move m : movesAB)
            System.out.print("(" + m.getRow() + "," + m.getCol() + ") ");
        System.out.println();

        // Test 2 : plateau avec un coup gagnant immédiat
        // X . X
        // . X .
        // O O .   → O doit jouer (2,2) pour gagner
        Board board2 = new Board();
        board2.play(new Move(0, 0), Mark.X);
        board2.play(new Move(0, 2), Mark.X);
        board2.play(new Move(1, 1), Mark.X);
        board2.play(new Move(2, 0), Mark.O);
        board2.play(new Move(2, 1), Mark.O);

        CPUPlayer cpuO = new CPUPlayer(Mark.O);
        ArrayList<Move> winMoves = cpuO.getNextMoveMinMax(board2);
        System.out.println("\n=== Minimax (O peut gagner) ===");
        System.out.println("Noeuds explores : " + cpuO.getNumOfExploredNodes());
        System.out.print("Meilleurs coups : ");
        for (Move m : winMoves)
            System.out.print("(" + m.getRow() + "," + m.getCol() + ") ");
        System.out.println();

        // Vérification evaluate
        System.out.println("\n=== evaluate ===");
        Board board3 = new Board();
        board3.play(new Move(0, 0), Mark.X);
        board3.play(new Move(0, 1), Mark.X);
        board3.play(new Move(0, 2), Mark.X);
        System.out.println("X gagne, evaluate(X) = " + board3.evaluate(Mark.X) + " (attendu: 100)");
        System.out.println("X gagne, evaluate(O) = " + board3.evaluate(Mark.O) + " (attendu: -100)");
    }
}
