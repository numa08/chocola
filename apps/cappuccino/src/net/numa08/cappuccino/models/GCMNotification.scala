package net.numa08.cappuccino.models

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.Sender
import akka.actor.ActorSystem
import akka.actor.Actor
import java.util.ResourceBundle

class GCMNotification(message : String) extends Notification {

  def notifTo (destination : NotifDestination) {
    val gcmMessage = new Message.Builder().addData(GCMNotification.MESSAGE, message)
                                           .build
    
    val system =  ActorSystem()
    val messenger = system.actorOf(Props(AsyncMessenger(gcmMessage, destination)), name = "messenger")
    messenger ! GCMNotification.GCMSender
  }
}

object GCMNotification {
  private val MESSAGE = "net.numa08.chocola.text"
  private lazy val GCM_API_KEY = { () => {
      val resource = ResourceBundle.getBundle("gcm")
      resource.getString("api.key")
    }}.apply()
  private lazy val GCMSender = new Sender(GCMNotification.GCM_API_KEY)
}

case class AsynMessenger(message : Message, destination : NotifDestination) extends Actor {

  def receive = {
    case sender : Sender => {
      sender.sendNoRetry(message, destination.identifier)
    }
  }
}
