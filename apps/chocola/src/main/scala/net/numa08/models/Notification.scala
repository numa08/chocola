package net.numa08.models

trait Notification{
	def notifTo(destination:NotifDestination)
}

trait NotifDestination {
	def identifier:String
}