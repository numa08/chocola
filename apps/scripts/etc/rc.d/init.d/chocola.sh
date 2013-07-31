#!/bin/bash
#
#
# Version 0.1
#
# chkconfig: - 80 20
# description: Chocola is service what filter twitter time line.

if [[ -f /usr/local/etc/chocola ]]; then
	./usr/local/etc/chocola
fi

prog=Chocola
RETVAL=0

start() {
	echo -n $"Starting ${prog}:"

	jsvc  -home ${JAVA_HOME} -cp ${CLASSPATH}:${CHOCOLA} -pidfile ${CHOCOLA_PID} ${CHOCOLA_MAIN}
	RETVAL=$?
	if [[ $RETVAL == 0 ]]; then
		echo -e "\t\t [\033[1;32m  OK  \033[0m]"
	else
		echo -e "\t\t [\033[1;31m  FAILED  \033[0m]"
	fi
	return $RETVAL
}

stop() {
	echo -n $"Stopping ${prog}"

	jsvc  -stop -pidfile ${CHOCOLA_PID} ${CHOCOLA_MAIN}
	RETVAL=$?
	if [[ $RETVAL == 0 ]]; then
		echo -e "\t\t [\033[1;32m  OK  \033[0m]"
	else
		echo -e "\t\t [\033[1;31m  FAILED  \033[0m]"
	fi
	return $RETVAL
}

case "$1" in
	start)
	start
	;;
	stop)
	stop
	;;
	restart)
	stop
	start
	;;
	*)
	echo $"Usage: ${prog} {start|top}"
	RETVAL=2
esac

exit $RETVAL