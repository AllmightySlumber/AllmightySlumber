package no_interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Serveur {
	public static void main(String[] args) {
		ServerSocket server;
		Socket client;
		Scanner scan;
		
		try{
			scan = new Scanner(System.in); // Pour écrire un message personnalisé
			server = new ServerSocket(4444);
			client = server.accept();
			// Récupération du canal de lecture
			BufferedReader in = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
			// Récupération du canal d'écriture
			PrintWriter out = new PrintWriter( client.getOutputStream(), true);
			Morpion plateau = new Morpion();
			plateau.printMorpion();
			
			while(true) {
				// Ici le serveur attends que le client envoie quelque chose pour l'afficher sur la console
				System.out.println("C'est au tour du client.");
				int c = Integer.valueOf(in.readLine()); // Lecture de ce que on a en sortie
				int l = Integer.valueOf(in.readLine()); // Lecture de ce que on a en sortie
				
				plateau.placeMark(l, c, 'X');
				plateau.printMorpion();
				
//				Check for defeat
				if(plateau.checkForWinner()) {
					System.out.println("Vous avez perdu.");
					scan.close();
					break;
				}
//				Check for draw
				if(plateau.isMorpionFull()) {
					System.out.println("Vous avez fait égalité.");
					scan.close();
					break;
				}
				
				System.out.println("C'est à votre tour.");
				
				// C'est au tour du Serveur de jouer
				c=0;
				l=0;
				// Le Serveur doit entrée un numéro de colonne entre 1 et 3
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
				out.println(c);
				out.println(l);
				plateau.placeMark(l, c, 'O');
				plateau.printMorpion();
				
//				Check for victory
				if(plateau.checkForWinner()) {
					System.out.println("Vous avez gagné.");
					scan.close();
					break;
				}

			}
					}
		catch(IOException i){
			System.out.println("Impossible d'écouter sur le port 4444: serait-il occupé?");
		}
	}
}


