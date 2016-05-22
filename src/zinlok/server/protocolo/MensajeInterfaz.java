package zinlok.server.protocolo;

import java.net.InetAddress;
import java.net.UnknownHostException;

// La clase mensaje almacena un par comando + parámetro
public interface MensajeInterfaz {
	/* Permite obtener el comando del mensaje
	 *  -> Recibe: nada
	 *  -> Devuelve: mensaje
	 */
	public String obtieneComando();
	
	/* Permite obtener los parámetros del mensaje
	 *  -> Recibe: nada
	 *  -> Devuelve: parametro
	 */
	public String obtieneParametro();
	public String getNombre();
	public InetAddress getIP() throws UnknownHostException;
	public int getTipo();

	
	/* Permite formar el mensaje para mandarlo por el protocolo
	 *  -> Recibe: comando y parametro
	 *  -> Devuelve: mensaje formado
	 */
	public String formaMensaje(String comando, String parametro);
	
	/* Comprueba que el formato del mensaje sea el correcto
	 *  -> Recibe: nada
	 *  -> Devuelve: correcto o no
	 */
	public Boolean compruebaMensaje();



}
