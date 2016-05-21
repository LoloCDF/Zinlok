package zinlok.server.objetos.maquina;

import zinlok.server.objetos.ObjetoInterfaz;

// Representa a una maquina 
public interface MaquinaInterfaz extends ObjetoInterfaz {
	// Para cerrar el candado a la máquina
	public void cerrarCandado();
	
	// Para quitar el candado a la máquina
	public void abreCandado();
	
	// Para obtener el estado del candado
	public Boolean getCandado();
}
