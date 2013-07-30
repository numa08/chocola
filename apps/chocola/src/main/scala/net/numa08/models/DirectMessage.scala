package net.numa08.models

import akka.actor._

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.conf.Configuration

case class DirectMessage(val message:String, val config:Configuration) extends  Notification with Actor{
		def notifTo(destination:NotifDestination){
			val system = ActorSystem()
			val messanger = system.actorOf(Props(DirectMessage(message, config)), name = "messanger")
			messanger ! destination
		}

		def receive = {
			case target : TwitterUser => {
				val text = message.substring(0, 140)

				val twitter = new TwitterFactory(config).getInstance()
				twitter.sendDirectMessage(target.identifier, text)
			}
		}
}

case class TwitterUser(val id:String) extends NotifDestination{
	def identifier:String = id
}