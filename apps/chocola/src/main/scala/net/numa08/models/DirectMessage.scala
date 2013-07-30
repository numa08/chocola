package net.numa08.models

import akka.actor._

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.conf.Configuration

case class DirectMessage(val message:String, val config:Configuration) extends  Notification{
		def notifTo(destination:NotifDestination){
			println("notif!!" + message)
			val system = ActorSystem()
			val twitter = new TwitterFactory(config).getInstance()
			val text = if(message.length > 140) {
							message.substring(0, 140)
						} else {
							message
						}
			val messanger = system.actorOf(Props(Messenger(text, twitter)), name = "messanger")
			messanger ! destination
		}
}

case class TwitterUser(val id:String) extends NotifDestination{
	def identifier:String = id
}

case class Messenger(val text:String, val twitter:Twitter) extends Actor {
	def receive = {
		case target : TwitterUser => {
			println("post!!")
			twitter.sendDirectMessage(target.identifier, text)
		}
	}
}
