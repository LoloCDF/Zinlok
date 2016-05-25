#!/bin/bash

EXT="wlan0"
INT="eth0"

iptables -F
iptables -t nat -F
iptables -P FORWARD ACCEPT

iptables -A INPUT -i $EXT -m state --state RELATED,ESTABLISHED -j ACCEPT

iptables -t nat -A POSTROUTING -s 10.0.0.0/24 -o wlan0 -j MASQUERADE

iptables -A FORWARD -s 10.0.0.0/24 -i eth0 -j ACCEPT
iptables -A INPUT -s 10.0.0.0/24 -j ACCEPT
