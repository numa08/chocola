package net.numa08.models

import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory

case class DirectMessage(val message:String) extends  Notification{
		def notifTo(destination:NotifDestination){
			import twitter4j.DirectMessage
			val twitter = new TwitterFactory().getInstance()
			twitter.sendDirectMessage(destination.identifier, message)
		}
}

case class TwitterUser(val id:String) extends NotifDestination{
	def identifier:String = id
}