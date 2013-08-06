#!/bin/bash
current=$(cd $(dirname $0);pwd)

cp -f ${current}/usr/local/etc/chocola.sh /usr/local/etc/chocola
chmod 755 /usr/local/etc/chocola

cp -f ${current}/etc/rc.d/init.d/chocola.sh /etc/rc.d/init.d/chocola
chmod 755 /etc/rc.d/init.d/chocola

cp -f ${current}/../chocola/target/scala-2.9.3/chocola.jar /usr/local/bin/chocola.jar
chmod 755 /usr/local/bin/chocola.jar
