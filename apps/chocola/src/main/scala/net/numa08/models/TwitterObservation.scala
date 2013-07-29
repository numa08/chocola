package net.numa08.models

import java.io.File
import java.io.FileInputStream
import scala.io.Source
import java.util.Properties
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.Executors
import scala.collection.JavaConversions._

import com.twitter.hbc.httpclient.auth.OAuth1
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core._
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.core.endpoint.UserstreamEndpoint;

import com.twitter.hbc.twitter4j.v3.Twitter4jStatusClient;
import twitter4j.StallWarning;
import twitter4j.StatusListener;
import twitter4j.Status
import twitter4j.StatusDeletionNotice

import org.apache.commons.lang.StringEscapeUtils

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

	val statusListner = new StatusListener(){

		def onStatus(status:Status){
			println(status.getText)
		}
		def onDeletionNotice(statusDeletionNotice :StatusDeletionNotice){}
		def onTrackLimitationNotice(limit:Int){}
		def onScrubGeo(user:Long, upToStatus:Long){}
		def onStallWarning(warning:StallWarning){}
		def onException(e:Exception){}
	}

	def startObsevation(){
		val description = new LinkedBlockingQueue[String](100000)

		val client = new ClientBuilder().hosts(Constants.USERSTREAM_HOST)
										.authentication(oauth())
										.processor(new StringDelimitedProcessor(description))
										.endpoint(new UserstreamEndpoint())
										.build()
		val numOfThread = 4
		val service = Executors.newFixedThreadPool(numOfThread);
		val t4jClient = new Twitter4jStatusClient(client, description, statusListner:: Nil, service)
		t4jClient.connect

		(1 to numOfThread).foreach{_ => t4jClient.process}
		// client.connect
		// println("Connected!!")
		// Stream.continually(description.take).foreach(json => {
		// 													println("hello") 
		// 													val out = StringEscapeUtils.unescapeJava(json)
		// 													println(out)})

	}	
}