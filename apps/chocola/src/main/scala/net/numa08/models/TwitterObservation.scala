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
import twitter4j.conf.ConfigurationBuilder

import org.apache.commons.lang.StringEscapeUtils

class TwitterObservation(val account:File) {
	lazy val oauthRecords = {
		val configure = new Properties
		configure.load(new FileInputStream(account))

		val consumerKey = configure.getProperty("consumer_key")
		val consumerSecret = configure.getProperty("consumer_secret")
		val accessToke = configure.getProperty("access_toke")
		val accessTokeSecret = configure.getProperty("access_toke_secret")

		OauthRecord(consumerKey, consumerSecret, accessToke, accessTokeSecret)
	}


	lazy val oauth = () => {
		new OAuth1(oauthRecords.consumerKey, oauthRecords.consumerSecret, oauthRecords.accessToke, oauthRecords.accessTokeSecret)
	}

	lazy val t4jOauth = () => {
		new ConfigurationBuilder()
								.setDebugEnabled(true)
								.setOAuthConsumerKey(oauthRecords.consumerKey)
								.setOAuthConsumerSecret(oauthRecords.consumerSecret)
								.setOAuthAccessToken(oauthRecords.accessToke)
								.setOAuthAccessTokenSecret(oauthRecords.accessTokeSecret)
								.build
  	}

	val statusListner = new StatusListener(){
		val messageText = (status:Status) => {
			val statusUrl = "https://twitter.com/" + status.getUser.getScreenName + "/status/" + status.getId
			statusUrl + " " + status.getText
		}
		def onStatus(status:Status){
			val filterRule = (status:Status) => {
				val pattern = ".*([nNｎＮ][uUｕＵ][mMｍＭ][aAａＡ]|ぬま|沼|[ヌﾇ][マﾏ])(さん|08|3|4|３|４)?.*"
				val matches = status.getText.matches(pattern)// && status.getInReplyToScreenName.isEmpty
				matches
			}

			Option(status).filter(filterRule).foreach(st => {
				val message = messageText(st)
				println(message)
				val numa08 = TwitterUser("numa08")
				DirectMessage(message, t4jOauth()).notifTo(numa08)
			 })

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

case class OauthRecord(consumerKey:String, consumerSecret:String, accessToke: String, accessTokeSecret:String)