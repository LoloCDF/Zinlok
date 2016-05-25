package zinlok.server.protocolo;

import java.net.Socket;
import java.nio.charset.Charset;
import java.io.*;

public class Comunicacion implements ComunicacionInterfaz{
	// Socket
	private Socket skCliente;
	
	public Comunicacion (Socket skCliente){
		this.skCliente=skCliente;
	}
	
	public Mensaje leeMensaje(){
		// Parámetros para la lectura
		Mensaje mensaje = null;
		InputStream entrada;
		byte[] buffer = new byte[1024];
		int tamanoLeido = 0;
		String cadena = "";
		try {
			entrada=this.skCliente.getInputStream();
			// Leemos byte a byte
			while((tamanoLeido=entrada.read(buffer))==1);
			cadena=new String(buffer,0,tamanoLeido);

			// Despedazamos el mensaje
			mensaje=new Mensaje(cadena);
			
			// Comprobamos que sea correcto
			if(!mensaje.compruebaMensaje()) {
				System.out.println("Error en formato del mensaje.");
				mensaje=null;
			}

		}
		
		catch (IOException e){
			System.out.println(e.getMessage());
		}

		return mensaje;
	}
	
	public Boolean mandaMensaje(String mensaje) throws IOException{
		Boolean resultado = true;
		OutputStream salida;
		
		salida=this.skCliente.getOutputStream();
		salida.write(mensaje.getBytes(Charset.forName("UTF-8")));
		
		return resultado;
	}
}
