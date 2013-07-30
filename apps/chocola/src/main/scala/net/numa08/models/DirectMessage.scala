package net.numa08.models

import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.conf.Configuration

case class DirectMessage(val message:String, val config:Configuration) extends  Notification with Actor{
		def notifTo(destination:NotifDestination){
			val text = message.substring(0, 140)

			println(text)

			import twitter4j.DirectMessage
			val twitter = new TwitterFactory(config).getInstance()
			twitter.sendDirectMessage(destination.identifier, text)
		}

		val log = Logging(context.system, this)
		def receive = {
			case "test" ⇒ log.info("received test")
			case _      ⇒ log.info("received unknown message")
		}
}

case class TwitterUser(val id:String) extends NotifDestination{
	def identifier:String = id
}