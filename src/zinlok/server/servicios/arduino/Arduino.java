package zinlok.server.servicios.arduino;

import jssc.*;

public class Arduino {
	private SerialPort puerto = null;
	
	private Boolean bandera = false;
	
	public Arduino(){
		this.puerto = new SerialPort("/dev/ttyACM0");
		try {
		    String[] puertos = SerialPortList.getPortNames();
		    for (int i=0; i<puertos.length;i++)
		    	System.out.println(puertos[i]);
		    puerto.openPort();

		    puerto.setParams(SerialPort.BAUDRATE_9600,
		                         SerialPort.DATABITS_8,
		                         SerialPort.STOPBITS_1,
		                         SerialPort.PARITY_NONE);

		    puerto.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
		                                  SerialPort.FLOWCONTROL_RTSCTS_OUT);
		}
		catch (SerialPortException ex) {
		    System.out.println("Error al escribir en el puerto: " + ex);
		}
	}
	
	public Boolean subeBandera(){
		if (this.bandera==false)
			this.bandera=true;
		
		return this.bandera;
	}
	
	public Boolean bajaBandera(){
		if (this.bandera==true)
			this.bandera=false;
		
		return !this.bandera;
	}
	
	public Boolean escribe(int orden) throws SerialPortException{
		this.puerto.writeInt(orden);
		
		return true;
	}
	
}
