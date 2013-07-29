package net.numa08.models

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.conf.Configuration

case class DirectMessage(val message:String, val config:Configuration) extends  Notification{
		def notifTo(destination:NotifDestination){
			val text = message.substring(0, 140)

			println(text)

			import twitter4j.DirectMessage
			val twitter = new TwitterFactory(config).getInstance()
			twitter.sendDirectMessage(destination.identifier, text)
		}
}

case class TwitterUser(val id:String) extends NotifDestination{
	def identifier:String = id
}