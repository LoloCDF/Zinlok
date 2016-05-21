package zinlok.server.objetos;

import java.io.IOException;
import java.net.InetAddress;

// Implementa la estructura de un objeto en general
public interface ObjetoInterfaz {
	// Para obtener el nombre del objeto
	public String getNombre();
	
	// Para obtener el estado del objeto
	public Boolean getEstado();
	
	// Para obtener la dirección IP del objeto
	public InetAddress getDireccionIP();
	
	// Para refrescar el estado del objeto
	public void compruebaEstado() throws IOException;
}
