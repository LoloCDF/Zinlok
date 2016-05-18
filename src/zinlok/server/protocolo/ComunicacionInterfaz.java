package zinlok.server.protocolo;

import java.io.IOException;

// Facilita las comunicaciones con el cliente
public interface ComunicacionInterfaz {
	/* Este método lee un mensaje
	 *  -> Recibe: nada
	 *  -> Devuelve: objeto Mensaje
	 */
	public Mensaje leeMensaje();
	
	/* Este método manda un mensaje
	 * 	-> Recibe: objeto Mensaje
	 * 	-> Devuelve: se ha podido o no
	 */	
	public Boolean mandaMensaje(String mensaje) throws IOException;
}
