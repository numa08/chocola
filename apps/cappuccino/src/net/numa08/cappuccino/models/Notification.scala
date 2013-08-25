package net.numa08.cappuccino.models

trait Notification {
  def notifTo(destination : NotifDestination)
}

trait NotifDestination {
  def identifier: String
}
