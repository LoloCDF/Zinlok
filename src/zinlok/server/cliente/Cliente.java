package zinlok.server.cliente;

import java.net.*;

import zinlok.server.objetos.Objeto;
import zinlok.server.objetos.Usuario;
import zinlok.server.objetos.maquina.Maquina;
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
		
		// Nombre del equipo
		String nombre = "";
		
		// Objeto del usuario
		Usuario usuario = null;
		Maquina concreto=null;
		
		// Leemos del stream para ver que nos pide el cliente		
		try {
			comunicaciones = new Comunicacion(this.miSocket);
			mensaje=comunicaciones.leeMensaje();
			if (mensaje.obtieneComando().equals("request")){
				if (mensaje.obtieneParametro().equals("0")){
					// Primero necesitamos el usuario se autentique
					// Le damos paso a que nos mande usuario y contraseña
					comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
					
					// De manera experimental ahora tenemos solo un usuario asi que
					mensaje=comunicaciones.leeMensaje();
					if(mensaje!=null && mensaje.obtieneComando().equals("send")){
						if(mensaje.obtieneParametro().equals("user:user")){
							System.out.println("El cliente \""+ this.miSocket.getInetAddress().toString()+"\" se ha"
									+ " autenticado de manera correcta.");
							comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
							
							usuario=new Usuario("user");
							// Ahora esperamos a que nos pidan alguna acción
						}
						
						else
							System.out.println("El cliente \""+ this.miSocket.getInetAddress().toString()+"\" no se ha"
									+ " autenticado de manera correcta.");
					}
				}
				
				else if(mensaje.obtieneParametro().equals("3")){
					// Contestamos y obtenemos el nombre
					comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
					mensaje=comunicaciones.leeMensaje();
					
					if (mensaje.obtieneComando().equals("send") && usuario != null){
						concreto=(Maquina) usuario.getObjeto(mensaje.obtieneParametro());
						if(concreto!=null){
							if(concreto.getCandado())
								comunicaciones.mandaMensaje(mensaje.formaMensaje("failure", ""));
							else
								comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
						}
						
						else
							comunicaciones.mandaMensaje(mensaje.formaMensaje("failure",""));
					}
					else
						comunicaciones.mandaMensaje(mensaje.formaMensaje("failure",""));
				}
			}
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
