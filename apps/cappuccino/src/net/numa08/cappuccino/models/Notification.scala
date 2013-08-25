package net.numa08.cappuccino.models

trait Notification {
  def nitifTo(destination : NotifDestination)
}

trait NotifDestination {
  def identifier: String
}
