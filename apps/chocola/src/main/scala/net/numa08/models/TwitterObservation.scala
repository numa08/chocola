package net.numa08.models

import java.io.File
import java.io.FileInputStream
import scala.io.Source
import java.util.Properties

import com.twitter.hbc.httpclient.auth.OAuth1
class TwitterObservation(val account:File) {
	lazy val oauth = () => {
		val configure = new Properties
		configure.load(new FileInputStream(account))

		val consumerKey = configure.getProperty("consumer_key")
		val consumerSecret = configure.getProperty("consumer_secret")
		val accessToke = configure.getProperty("access_toke")
		val accessTokeSecret = configure.getProperty("access_toke_secret")

		new OAuth1(consumerKey, consumerSecret, accessToke, accessTokeSecret)		
	}

	def startObsevation(){
		println(oauth())
	}	
}