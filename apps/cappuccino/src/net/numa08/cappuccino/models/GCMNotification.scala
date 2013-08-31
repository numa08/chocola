package net.numa08.cappuccino.models

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.Sender
import scala.actors.Actor

class GCMNotification(message : String) extends Notification {

  def notifTo (destination : NotifDestination) {
    val gcmMessage = new Message.Builder().addData(GCMNotification.MESSAGE, message)
                                           .build
    
    val messenger = system.actorOf(Props(AsyncMessenger(gcmMessage, destination)), name = "messenger")
    messenger ! GCMNotification.GCMSender
  }
}

object GCMNotification {
  private val MESSAGE = "net.numa08.chocola.text"
  private val SENDER_ID = ""

  private lazy val GCMSender = new Sender(GCMNotification.SENDER_ID)
}

case class AsynMessenger(message : Message, destination : NotifDestination) extends Actor {

  def receive = {
    case sender : Sender => {
      sender.msendNoRetry(message, destination.identifier)
    }
  }
}
