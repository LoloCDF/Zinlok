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
		this.indice = indice-1;
		this.listaClientes = listaClientes;
	}
	
	public void run(){
		System.out.println("Se ha encendido la bombilla.");
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Se ha cerrado la conexión con el cliente: "+miSocket.getInetAddress().getHostAddress());
		try {
			this.miSocket.close();
		}
		catch (IOException ex){
			System.out.println("Problema al cerrar el socket.");
		}
				
		Thread.currentThread().interrupt();
		
	}
}
