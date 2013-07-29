package net.numa08.chocola

import net.numa08.models._

import java.io.File

import org.apache.commons.daemon._

class Chocola extends Daemon {

	def init(context:DaemonContext) {}

	def start() {
		val accountFile = new File("src/resources/account.properties")
		val twitterObservation = new TwitterObservation(accountFile)
		twitterObservation.startObsevation
	}

	def stop() {}

	def destroy() {}
}

object Chocola extends App{
	val chocola = new Chocola
	chocola.init(null)
	chocola.start
}