package zinlok.server.protocolo;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Mensaje implements MensajeInterfaz {
	private String comando="";
	private String parametro="";
	
	public Mensaje (String mensaje){
		int posComando = 0;
		int posParametro = 0;
		Boolean bandera = false;
		if (mensaje != null){
			// Obtenemos el comando
			for (posComando=0; posComando<mensaje.length(); posComando++){
				if(mensaje.charAt(posComando)!='(')
					this.comando=this.comando+mensaje.charAt(posComando);
				
				else {
					posParametro=posComando+1;
					posComando=mensaje.length();
				}
			}
			
			// Obtenemos el parametro
			for (;posParametro<mensaje.length();posParametro++){
				if(mensaje.charAt(posParametro)!=')')
					this.parametro=this.parametro+mensaje.charAt(posParametro);
				
				else 
					posParametro=mensaje.length();
			}
		}
	}
	
	public String obtieneComando(){
		return this.comando;
	}
	
	public String obtieneParametro(){
		return this.parametro;
	}
	
	public String getNombre(){
		String []cadena = this.parametro.split(":");
	
		return cadena[0];
	}
	
	public InetAddress getIP() throws UnknownHostException {
		String []cadena = this.parametro.split(":");
		
		return InetAddress.getByName(cadena[2]);
	}
	
	public int getTipo(){
		String []cadena = this.parametro.split(":");
		return Integer.parseInt(cadena[1]);
	}

	
	public String formaMensaje(String comando, String parametro){
		return comando+'('+parametro+')';
	}
	
	public Boolean compruebaMensaje(){
		Boolean correcto = false;
		ListaMensajes lista = new ListaMensajes();
		int i = 0;
		
		// Comprobamos el campo comando es valido
		for (i=0; i<lista.comandosValidos().length; i++){
			if (this.comando.compareTo(lista.comandosValidos()[i])==0){
				correcto=true;
				i=lista.comandosValidos().length;
			}				
		}
		
		return correcto;		
	}
}
