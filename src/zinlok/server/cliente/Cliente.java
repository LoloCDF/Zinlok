package zinlok.server.cliente;

import java.net.*;

import zinlok.server.protocolo.*;

import java.io.*;

public class Cliente extends Thread implements ClienteInterfaz {
	// Variable que guarda el socket del cliente
	private Socket miSocket;
	
	public Cliente(Socket skCliente){
		this.miSocket = skCliente;
	}
	
	public void run(){
		
		// Clase de comunicaciones
		Comunicacion comunicaciones = null;
		
		// Mensaje
		Mensaje mensaje = null;
		
		// Leemos del stream para ver que nos pide el cliente		
		try {
			comunicaciones = new Comunicacion(this.miSocket);
			mensaje=comunicaciones.leeMensaje();
			
			// Comprobamos que se ha mandado algo correcto
			if (mensaje!=null){
				/*
				 *	A PARTIR DE AQUÍ EL CÓDIGO ES PARA HACER PRUEBAS.
				 * 	REALMENTE SE DEBERÍA IMPLEMENTAR LA CLASE "ACCIONES" QUE
				 * 	TUVIESE UN MÉTODO POR CADA ACCIÓN QUE SE QUIERA REALIZAR
				 * 	EJ: ABRIR PUERTA, CONSULTA EN BASE DE DATOS, APAGAR BOMBILLA...
				 * 	ESTA CLASE EJECUTARIA LOS DISTINTOS MÉTODOS DESDE SU CONSTRUCTOR
				 * 	PARA ELLO NECESITA EL MENSAJE DESPEDAZADO, Y ELLA YA EJECUTARÁ EL 
				 * 	MÉTODO CON THIS.MÉTODO() QUIZÁS SEA MEJOR DIVIDIR EN VEZ DE
				 * 	EN MÉTODOS EN CLASES. HAY QUE DISCUTIRLO.
				 */
			}
			
			else
				System.out.println("El mensaje no cumplía el formato.");
			
			System.out.println("Se ha cerrado la conexión con el cliente: "+miSocket.getInetAddress().getHostAddress());
			this.miSocket.close();
		}
		catch (IOException ex){
			System.out.println("Problema al cerrar el socket.");
		}
				
		Thread.currentThread().interrupt();		
	}
}
