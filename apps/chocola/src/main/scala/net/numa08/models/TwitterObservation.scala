package net.numa08.models

import java.io.File
import java.io.FileInputStream
import scala.io.Source
import java.util.Properties
import java.util.concurrent.LinkedBlockingQueue

import com.twitter.hbc.httpclient.auth.OAuth1
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core._
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.core.endpoint.UserstreamEndpoint;

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
		val description = new LinkedBlockingQueue[String](100000)
		val client = new ClientBuilder().hosts(Constants.USERSTREAM_HOST)
										.authentication(oauth())
										.processor(new StringDelimitedProcessor(description))
										.endpoint(new UserstreamEndpoint())
										.build()
	}	
}