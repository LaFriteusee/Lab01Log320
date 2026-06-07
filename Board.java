import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait
// être le cas)
class Board
{
    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        board = new Mark[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = Mark.EMPTY;
    }

    // Place la pièce 'mark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark){
        board[m.getRow()][m.getCol()] = mark;
    }

    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        Mark winner = checkWinner();
        if (winner == mark) return 100;
        if (winner != null) return -100;
        return 0;
    }

    // Génère tous les coups possibles (cases vides), gauche→droite, haut→bas
    public ArrayList<Move> generateMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == Mark.EMPTY)
                    moves.add(new Move(i, j));
        return moves;
    }

    // Retourne true si la partie est terminée (victoire ou plateau plein)
    public boolean isGameOver(){
        return checkWinner() != null || generateMoves().isEmpty();
    }

    // Annule un coup (remet la case à EMPTY) pour le backtracking
    public void undoMove(Move m){
        board[m.getRow()][m.getCol()] = Mark.EMPTY;
    }

    public void display(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == Mark.X)      System.out.print(" X ");
                else if (board[i][j] == Mark.O) System.out.print(" O ");
                else                             System.out.print(" . ");
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("---+---+---");
        }
    }

    // Retourne le Mark gagnant, ou null si pas de gagnant
    private Mark checkWinner(){
        // Lignes
        for (int i = 0; i < 3; i++){
            if (board[i][0] != Mark.EMPTY
                    && board[i][0] == board[i][1]
                    && board[i][1] == board[i][2])
                return board[i][0];
        }
        // Colonnes
        for (int j = 0; j < 3; j++){
            if (board[0][j] != Mark.EMPTY
                    && board[0][j] == board[1][j]
                    && board[1][j] == board[2][j])
                return board[0][j];
        }
        // Diagonale principale
        if (board[0][0] != Mark.EMPTY
                && board[0][0] == board[1][1]
                && board[1][1] == board[2][2])
            return board[0][0];
        // Diagonale secondaire
        if (board[0][2] != Mark.EMPTY
                && board[0][2] == board[1][1]
                && board[1][1] == board[2][0])
            return board[0][2];
        return null;
    }
}
