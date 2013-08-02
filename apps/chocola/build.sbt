import AssemblyKeys._ 

libraryDependencies += "com.twitter" % "hbc-core" % "1.3.0"
 
libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

libraryDependencies += "commons-daemon" % "commons-daemon" % "1.0.10"

libraryDependencies += "com.twitter" % "hbc-twitter4j-v3" % "1.4.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
 
libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "com.google.guava" % "guava" % "13.0.1"

assemblySettings

jarName in assembly := "chocola.jar"

test in assembly := {}

assembleArtifact in packageScala := false

assembleArtifact in packageBin := false