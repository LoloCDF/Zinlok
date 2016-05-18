package zinlok.server.cliente;

import java.net.*;
import zinlok.server.protocolo.*;
import zinlok.server.snmp.Snmp;

import java.io.*;

public class Cliente extends Thread implements ClienteInterfaz {
	// Variable que guarda el socket del cliente
	private Socket miSocket;
	private Snmp agente = null;
	private int posicion = 0;
	
	public Cliente(Socket skCliente, Snmp agente, int posicion){
		this.miSocket = skCliente;
		this.agente = agente;
		this.posicion = posicion;
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
			
			// Primero necesitamos el usuario se autentique
			if (mensaje.obtieneComando().equals("request") && mensaje.obtieneParametro().equals("0")){
				// Le damos paso a que nos mande usuario y contraseña
				comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
				
				// De manera experimental ahora tenemos solo un usuario asi que
				mensaje=comunicaciones.leeMensaje();
				if(mensaje!=null && mensaje.obtieneComando().equals("send")){
					if(mensaje.obtieneParametro().equals("user:user")){
						System.out.println("El cliente \""+ this.miSocket.getInetAddress().toString()+"\" se ha"
								+ " autenticado de manera correcta.");
						comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
					}
				}
			}
			
			else
				System.out.println("El cliente \""+ this.miSocket.getInetAddress().toString()+"\" no se ha"
						+ " autenticado de manera correcta.");
			
			System.out.println("Se ha cerrado la conexión con el cliente: "+miSocket.getInetAddress().getHostAddress());
			this.agente.getTabla()[this.posicion].setNconexiones(this.agente.getTabla()[this.posicion].getNconexiones()-1);
			
			if (this.agente.getTabla()[this.posicion].getNconexiones()==0)
				this.agente.getTabla()[this.posicion].liberar();
			
			this.miSocket.close();
		}
		catch (IOException ex){
			System.out.println("Problema al cerrar el socket.");
		}
				
		Thread.currentThread().interrupt();		
	}
}
