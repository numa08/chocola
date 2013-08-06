package net.numa08.chocola

import net.numa08.models._

import java.io.File

import org.apache.commons.daemon._

class Chocola extends Daemon {

	def init(context:DaemonContext) {}

	def start() {
    println(getClass.getResource("account.properties"))
    val filePath = Option(getClass.getResource("account.properties"))
    filePath match {
      case Some(path) => { val accountFile = new File(path.getPath)
                           val twitterObservation = new TwitterObservation(accountFile)
                           twitterObservation.startObsevation
                         }
     case _ => println("file not found!!")
    }
	}

	def stop() {}

	def destroy() {}
}

object Chocola extends App{
	val chocola = new Chocola
	chocola.init(null)
	chocola.start
}
