package zinlok.server.protocolo;

public class Mensaje implements MensajeInterfaz {
	private String comando="";
	private String parametro="";

	public Mensaje (String mensaje){
		int posComando = 0;
		int posParametro = 0;
		
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
	
	public String formaMensaje(String parametro, String comando){
		return comando+'('+parametro+')';
	}
	
	public Boolean compruebaMensaje(){
		Boolean correcto = true;
		ListaMensajes lista = new ListaMensajes();
		int i = 0;
		
		// Comprobamos el campo comando es valido
		for (i=0; i<lista.comandosValidos().length; i++){
			if (this.comando!=lista.comandosValidos()[i])
				if (i==lista.comandosValidos().length-1)
					correcto=false;
			
			else {
				correcto=true;
			}
		}
		
		return correcto;		
	}
}
