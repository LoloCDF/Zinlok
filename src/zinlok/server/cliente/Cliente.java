package zinlok.server.cliente;

import java.net.*;
import java.util.ArrayList;
import java.io.IOException;

public class Cliente extends Thread implements ClienteInterfaz{
	// Variable que guarda el socket del cliente
	Socket miSocket;
	
	// Puntero a la lista de clientes
	ArrayList<Cliente> listaClientes = null;
	
	// Índice de donde estoy en la tabla
	int indice = 0;
	
	public Cliente(Socket skCliente, int indice, ArrayList<Cliente> listaClientes){
		this.miSocket = skCliente;
		this.indice = indice;
		this.listaClientes = listaClientes;
	}
	
	public void run(Socket miSocket){
		System.out.println("Se ha encendido la bombilla.");
		try {
		sleep(5000);
		}
		
		catch (InterruptedException e){
			try {
			System.out.println("Se ha cerrado la conexión con el cliente: "+miSocket.getInetAddress().getHostAddress());
			this.miSocket.close();
			}
			
			catch (IOException ex){
				System.out.println("Problema al cerrar el socket.");
			}
			
			this.listaClientes.remove(indice);
			Thread.currentThread().interrupt();
		}
	}
}
