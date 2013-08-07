package net.numa08.chocola

import net.numa08.models._

import java.io.File

import org.apache.commons.daemon._

class Chocola extends Daemon {

	def init(context:DaemonContext) {}

  def start() {
    val filePath = Option(getClass.getResource("account.properties"))
      val file = filePath match {
      case Some(path) =>  Option(new File(path.toURI))
      case _ => None
    }
    file match {
        case Some(f) => {val twitterObservation = new TwitterObservation(f)
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
