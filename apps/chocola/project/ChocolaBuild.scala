import sbt._
import sbt.Keys._

object ChocolaBuild extends Build {

  lazy val chocola = Project(
    id = "chocola",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "Chocola",
      organization := "net.numa08.chocola",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.0"
      // add other settings here
    )
  )
}
