#!/bin/bash

 exec 555<> /dev/tcp/192.168.1.83/5000;

 ( sleep 2 ; echo -e "request(3)" >&555 ;	sleep 2 ; echo  -e "send(equipo:1)" >&555 ; sleep 2; cat <&555 > /tmp/respuesta.txt )  



	
	VAR=`cat /tmp/respuesta.txt` ;
	

	
	if [ $VAR == "ok()" ] ;
	then
		echo "Cerrojo abierto"
		
		
	elif [ $VAR == "failure()" ] ;
	then
		echo "Cerrojo echado, apagando equipo"
			
		poweroff
		
	else 
		
		echo "Server off, apagando equipo"	
		#poweroff
	
	fi
	
	
	