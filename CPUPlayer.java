import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait
// être le cas)
class CPUPlayer
{

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;

    private Mark cpu;
    private Mark opponent;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        this.cpu = cpu;
        this.opponent = (cpu == Mark.X) ? Mark.O : Mark.X;
    }

    // Ne pas changer cette méthode
    public int getNumOfExploredNodes(){
        return numExploredNodes;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board)
    {
        numExploredNodes = 0;
        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;

        for (Move move : board.generateMoves()){
            board.play(move, cpu);
            int score = minimax(board, false, 1);
            board.undoMove(move);

            if (score > bestScore){
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore){
                bestMoves.add(move);
            }
        }
        return bestMoves;
    }

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board){
        numExploredNodes = 0;
        ArrayList<Move> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Move move : board.generateMoves()){
            board.play(move, cpu);
            int score = alphaBeta(board, false, alpha, beta, 1);
            board.undoMove(move);

            if (score > bestScore){
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore){
                bestMoves.add(move);
            }
            alpha = Math.max(alpha, bestScore);
        }
        return bestMoves;
    }

    private int minimax(Board board, boolean isMaximizing, int depth){
        numExploredNodes++;
        if (board.isGameOver()){
            int score = board.evaluate(cpu);
            if (score > 0) return score - depth;
            if (score < 0) return score + depth;
            return 0;
        }

        if (isMaximizing){
            int best = Integer.MIN_VALUE;
            for (Move move : board.generateMoves()){
                board.play(move, cpu);
                best = Math.max(best, minimax(board, false, depth + 1));
                board.undoMove(move);
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Move move : board.generateMoves()){
                board.play(move, opponent);
                best = Math.min(best, minimax(board, true, depth + 1));
                board.undoMove(move);
            }
            return best;
        }
    }

    private int alphaBeta(Board board, boolean isMaximizing, int alpha, int beta, int depth){
        numExploredNodes++;
        if (board.isGameOver()){
            int score = board.evaluate(cpu);
            if (score > 0) return score - depth;
            if (score < 0) return score + depth;
            return 0;
        }

        if (isMaximizing){
            int best = Integer.MIN_VALUE;
            for (Move move : board.generateMoves()){
                board.play(move, cpu);
                best = Math.max(best, alphaBeta(board, false, alpha, beta, depth + 1));
                board.undoMove(move);
                alpha = Math.max(alpha, best);
                if (beta <= alpha) break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Move move : board.generateMoves()){
                board.play(move, opponent);
                best = Math.min(best, alphaBeta(board, true, alpha, beta, depth + 1));
                board.undoMove(move);
                beta = Math.min(beta, best);
                if (beta <= alpha) break;
            }
            return best;
        }
    }
}
