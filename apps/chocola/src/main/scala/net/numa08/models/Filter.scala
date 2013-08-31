package net.numa08.models

trait Filter[T]{

  def filter(s: T => Unit)
}
