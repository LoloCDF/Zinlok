package zinlok.server.cliente;

import java.net.*;
import java.util.ArrayList;

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
	private ArrayList<Cliente> listaClientes = null;
	private Usuario usuario = null;

	
	public Cliente(Socket skCliente, Snmp agente, int posicion, ArrayList<Cliente> listaClientes){
		this.miSocket = skCliente;
		this.agente = agente;
		this.posicion = posicion;
		this.listaClientes=listaClientes;
	}
	
	public Usuario getUsuario(){
		return this.usuario;
	}
	
	public Boolean puedoIniciar(String nombre, int tipo){
		Maquina concretoMaquina = null;
		Boolean bandera = true;
		Boolean resultado = false;
		
		for(int i=0; i<this.listaClientes.size() && bandera; i++){
			if(tipo==1){
				concretoMaquina=(Maquina) listaClientes.get(i).getUsuario().getObjeto(nombre);
				
				if(concretoMaquina!=null)
					bandera=false;
			}
		}
		
		if(tipo==1){
			if(concretoMaquina!=null){
				resultado=concretoMaquina.getCandado();
			}
		}
		
		return resultado;
	}
	
	
	public void run(){
		
		// Clase de comunicaciones
		Comunicacion comunicaciones = null;
		
		// Mensaje
		Mensaje mensaje = null;
		
		// Nombre del equipo
		String nombre = "";
		int tipo = 0;
		InetAddress ip = null;
		Boolean ban = true;
		Boolean error = false;
		
		// Objeto del usuario
		Maquina concretoMaquina=null;
		
		// Leemos del stream para ver que nos pide el cliente		
		try {
			comunicaciones = new Comunicacion(this.miSocket);
			mensaje=comunicaciones.leeMensaje();
			if (mensaje!=null && mensaje.obtieneComando().equals("request")){
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
							
							this.usuario=new Usuario("user");
							// Ahora iniciamos el bucle de interacción
							mensaje.formaMensaje("", "");
							while(ban&&error==false){
								mensaje=comunicaciones.leeMensaje();
								if(mensaje!=null && mensaje.obtieneComando().equals("request")){
									
									// Nos están pidiendo realizar una acción sobre un objeto
									if(mensaje.obtieneParametro().equals("2")){
										comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
										mensaje=comunicaciones.leeMensaje();
										
										// Estamos esperando obtener el nombre del objeto y el tipo
										if(mensaje!=null&&mensaje.obtieneComando().equals("send")){
											nombre=mensaje.getNombre();
											tipo=mensaje.getTipo();
											/********************************************************
											 * Se Trata a un Objeto de tipo máquina	                *
											 ********************************************************/
											if (tipo==1){
												concretoMaquina=(Maquina) this.usuario.getObjeto(nombre);
												if(concretoMaquina != null){
													comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
															
													// Una vez obtenido el objeto vemos que quiere hacer el usuario
													mensaje=comunicaciones.leeMensaje();
															
													if(mensaje!=null&&mensaje.obtieneComando().equals("send")){
														
														// Quiere cerrarlo
														if(mensaje.obtieneParametro().equals("1")){
															concretoMaquina.cerrarCandado();
															comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
														}
														
														// Quiere abrirlo
														else if(mensaje.obtieneParametro().equals("0")){
															concretoMaquina.abreCandado();
															comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
														}
														
														// Quiere saber como está
														else if(mensaje.obtieneParametro().equals("2")){
															if(!concretoMaquina.getCandado())
																comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
															else
																comunicaciones.mandaMensaje(mensaje.formaMensaje("failure", ""));			
														}
													}
											}
										}
											
										else
											comunicaciones.mandaMensaje(mensaje.formaMensaje("failure", ""));

										}
									}
									
									// Nos están pidiendo comprobar el estado del objeto
									else if(mensaje!=null&&mensaje.obtieneParametro().equals("5")){
										comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
										// Esperamos el nombre
										mensaje=comunicaciones.leeMensaje();
										
										if(mensaje!=null&&mensaje.obtieneComando().equals("send")){
											nombre=mensaje.obtieneParametro();
											this.usuario.getObjeto(nombre).compruebaEstado();
											
											if(this.usuario.getObjeto(nombre).getEstado())
												comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
											
											else
												comunicaciones.mandaMensaje(mensaje.formaMensaje("failure", ""));
										}
									}
									
									else if(mensaje!=null&&mensaje.obtieneParametro().equals("1"))
										ban=false;
									
									// Nos están pidiendo añadir nuevo objeto al usuario
									else if(mensaje!=null&&mensaje.obtieneParametro().equals("4")){
										// Recibimos: nombre, IP y tipo
										comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
										mensaje=comunicaciones.leeMensaje();
										if(mensaje!=null&&mensaje.obtieneComando().equals("send")){

											nombre=mensaje.getNombre();
											tipo=mensaje.getTipo();
											ip=mensaje.getIP();
											if(tipo==1){
												concretoMaquina=new Maquina(nombre,ip);

												this.usuario.addObjeto((Objeto) concretoMaquina);
												comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));			
											}
										}
										
									}
							}
							if(mensaje!=null)
								mensaje.formaMensaje("", "");
							else {
								error=true;
								mensaje= new Mensaje("request(0)");
								mensaje.formaMensaje("", "");
							}
								
						}
						}
						
						else
							System.out.println("El cliente \""+ this.miSocket.getInetAddress().toString()+"\" no se ha"
									+ " autenticado de manera correcta.");
					}
				}
				
				else if(mensaje.obtieneParametro().equals("3")){
					// Contestamos y obtenemos el nombre
					mensaje=comunicaciones.leeMensaje();
					
					if (mensaje!=null&&mensaje.obtieneComando().equals("send")){
						nombre=mensaje.getNombre();
						tipo=mensaje.getTipo();
						if(!(this.puedoIniciar(nombre, tipo)))
							comunicaciones.mandaMensaje(mensaje.formaMensaje("ok", ""));
						else
							comunicaciones.mandaMensaje(mensaje.formaMensaje("failure",""));

					}
					else
						comunicaciones.mandaMensaje(mensaje.formaMensaje("failure",""));
				}
			}
			System.out.println("Se ha cerrado la conexión con el cliente: "+miSocket.getInetAddress().getHostAddress());
			this.agente.getTabla()[this.posicion].setNconexiones(this.agente.getTabla()[this.posicion].getNconexiones()-1);
			this.agente.decrementaClientes();
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
