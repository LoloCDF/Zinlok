#!/bin/bash

exec 555<>/dev/tcp/192.168.1.83/5000;

sleep 2;
echo -e "request(3)" >&555;
sleep 2;
echo -e "send(router:1)" >&555;
sleep 2;
cat <&555 > /tmp/respuesta.txt;

VAR=`cat /tmp/respuesta.txt`;

if [ $VAR == "ok()" ] ;
then
	echo "Cerrojo abierto." >> /tmp/log.txt;
	echo "1" > /proc/sys/net/ipv4/ip_forward;
elif [ $VAR == "failure()" ] ;
then
	echo "Cerrojo cerrado." >> /tmp/log.txt;
	echo "0" > /proc/sys/net/ipv4/ip_forward;
fi
