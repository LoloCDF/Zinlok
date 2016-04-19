package zinlok.server;

import java.net.*;

public class Server {
	public static void main (String[] args){
		int puerto=5000;
		
		ServerSocket skServidor;
		
		Socket skCliente;
		
		try{
			skServidor = new ServerSocket(puerto);
			System.out.println("Escucho el puerto"+puerto);
			
			while(true){
				skCliente=skServidor.accept();
				System.out.println("Sirvo al cliente: " + skCliente.getInetAddress().toString());
				skCliente.close();
			}
		} catch( Exception e){
			System.out.println(e.getMessage());
		}
	
	}
}
