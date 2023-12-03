package no_interface;

public class Morpion {
	 private char[][] board;

	 public Morpion() {
	     // Initialisation du tableau de jeu
	     board = new char[3][3];
	     initializeMorpion();
	 }

	 private void initializeMorpion() {
	     // Remplissage du tableau avec des points
	     for (int i = 0; i < 3; i++) {
	         for (int j = 0; j < 3; j++) {
	             board[i][j] = '.';
	         }
	     }
	 }

	 public void printMorpion() {
	     // Affichage du tableau de jeu
		 // Affichage de la numérotation des colonnes
	     System.out.println("  1 2 3");
	     for (int i = 0; i < 3; i++) {
	    	 // Affichage de la numérotation des lignes
	         System.out.print((i+1) + " ");
	         for (int j = 0; j < 3; j++) {
	             System.out.print(board[i][j] + " ");
	         }
	         // Saut de ligne (pour que l'affichage soit carré)
	         System.out.println();
	     }
	 }

	 public void placeMark(int row, int col, char mark) {
	     // Placement d'un pion sur le tableau
	     if (board[row-1][col-1] == '.') {
	         board[row-1][col-1] = mark;
	     }
	 }
	 
	 public boolean checkMove(int raw, int line) {
		 // Vérifie si le coup est valide
		 if (board[raw-1][line-1] == '.') {
	         return true; // Coup valide
	     }
		 else {
			 return false; // Coup invalide
		 }
		 
	 }

	 public boolean checkForWinner() {
	     // Vérification des lignes et des colonnes
	     for (int i = 0; i < 3; i++) {
	         if ((board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '.') ||
	             (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '.')) {
	             return true; // Il y a un gagnant
	         }
	     }

	     // Vérification des diagonales
	     if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '.') ||
	         (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '.')) {
	         return true; // Il y a un gagnant
	     }

	     return false; // Aucun gagnant
	 }

	 public boolean isMorpionFull() {
	     // Vérification si le tableau est plein (match nul)
	     for (int i = 0; i < 3; i++) {
	         for (int j = 0; j < 3; j++) {
	             if (board[i][j] == '.') {
	                 return false; // Le tableau n'est pas plein
	             }
	         }
	     }
	     return true; // Le tableau est plein (match nul)
	 }

}

