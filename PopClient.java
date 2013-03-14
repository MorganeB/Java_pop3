import java.net.*;
import java.io.*;
import java.util.Scanner;		

/* *****************************************
******** Lancement du programme : **********
** 1 : dans une 1ere fenetre de commande : java PopServer
** 2 : dans une 2eme fenetre de commande : java PopClient args [0] args [1] args [2] args [3]
avec 		args [0] = serveur
			args [1] = port 
			args [2] = user
			args [3] = mot de passe 
*************************************** */


/* Note : nous n'avons pas géré les exceptions concernant 
la saisie des donnees en ligne de commande(car pas vues en cours de Java) */


public class PopClient{

	public static void main(String[] args) throws SocketException, IOException, UnknownHostException
	{
	Scanner sc=new Scanner(System.in);
	
	InetAddress adresse =InetAddress.getByName(args[0]);		//adresse du serveur 
	int port=Integer.parseInt(args[1]);						//nom du port 
	String user=(args[2]);						// nom d'utilisateur
	String password=(args[3]);					// mot de passe 
									
									
   	Socket socket=new Socket(adresse, port);
   	BufferedReader rd=new BufferedReader(new InputStreamReader(socket.getInputStream()));
   	BufferedWriter wr=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    

   	while(!rd.readLine().contains("OK"));    //on attend que le serveur dise "ok" pour passer à la suite
	wr.write("user " + user +"\r\n");
	wr.flush();
   	
   	while(!rd.readLine().contains("OK"));
   	 wr.write("pass " + password +"\r\n");
	 System.out.println();
   	 wr.flush();
   				    
   	 while(!rd.readLine().contains("OK"));
   	 wr.write("list\r\n");
   	 wr.flush();
       
   	 int i=-1;	//le compteur de lignes (= le nombre de messages) 
   	 int j;	// le message dans lequel on est 
   	
   	 //on compte le nombre de messages
   	 while(!rd.readLine().contains(".")){
   		i++;
   	 }
   	 for(j=1; j <= i; j++){
   		 wr.write("RETR "+Integer.toString(j)+"\n");
		 wr.flush();
		 
   		 // maintenant on recupere expediteur et sujet
   		String chaine = rd.readLine();
		String expediteur="";
		String objet="";

   		while(chaine.compareTo(".")!=0){
			if(chaine.startsWith("From")==true){
				expediteur=chaine;			//on stock la ligne du "From" dans expediteur 
					System.out.println("Expediteur du message " + j + ":" +expediteur);
			}else{
				if(chaine.startsWith("Subject")==true){
					objet=chaine;
					System.out.println(objet);
					System.out.println("\n");
				}
			}
   			 chaine=rd.readLine();
   		 }
			 
   	 }
   	 
   	 socket.close();
    }

}


/* *** Fin *** */