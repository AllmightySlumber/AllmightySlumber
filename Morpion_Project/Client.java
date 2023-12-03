package no_interface;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) {
		Socket client;
		
		try{
			client = new Socket("localhost", 4444);
			BufferedReader in = new BufferedReader( new InputStreamReader( client.getInputStream() ) ); // Le bufferedReader permet de récupérer ce qui est sur les sockets
			Scanner scan = new Scanner(System.in); // Le scanner sera utilisé comme un input pour avoir des messages personnalisé
			PrintWriter out = new PrintWriter( client.getOutputStream(), true); // Un writer qui permet d'écrire dans les sockets
			Morpion plateau = new Morpion();
			plateau.printMorpion();
			
			while(true) {
				// C'est au tour du Client de jouer
				int c=0;
				int l=0;
				// Le client doit entrée un numéro de colonne entre 1 et 3
				inputLoop: while (true) {
			        System.out.println("Entrez le numéro de colonne (1-3) :");
			        c = scan.nextInt();
			        System.out.println("Entrez le numéro de ligne (1-3) :");
			        l = scan.nextInt();

			        if (1 <= c && c <= 3 && 1 <= l && l <= 3 && plateau.checkMove(l, c)) {
			            // Sortir de la boucle interne seulement
			            break inputLoop;
			        } else {
			            System.out.println("Coordonnées invalides. Veuillez réessayer.");
			        }
			    }
				out.println(c); 									// Envoie de la colonne au serveur.
				out.println(l); 									// Envoie de la ligne au serveur.
				
				plateau.placeMark(l, c, 'X');					// Met à jour le plateau
				plateau.printMorpion();
//				Check for victory
				if(plateau.checkForWinner()) {
					System.out.println("Vous avez gagné.");
					scan.close();
					break;
				}
//				Check for draw
				if(plateau.isMorpionFull()) {
					System.out.println("Vous avez fait égalité.");
					scan.close();
					break;
				}
				
				// Le client attends que le serveur lui envoie un message
				c = Integer.valueOf(in.readLine()); // Lecture de ce que on a en entrée et stockage dans la variable text
				l = Integer.valueOf(in.readLine());
				plateau.placeMark(l, c, 'O');
				plateau.printMorpion();
//				Check for defeat
				if(plateau.checkForWinner()) {
					System.out.println("Vous avez perdu.");
					scan.close();
					break;
				}
				}

		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException ioe){
			System.out.println("Le port est fermée.");
		}
	}
}
