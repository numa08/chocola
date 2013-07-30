package net.numa08.models
import org.scalatest.FunSuite

import java.io.File

import net.numa08.models._

class SendDirectMessageSuid extends FunSuite {
	test("Can send directmessage"){
		val accountFile = new File("src/resources/account.properties")
		val twitterObservation = new TwitterObservation(accountFile)
		val messanger = DirectMessage("this is message", twitterObservation.t4jOauth())
		val numa08 = TwitterUser("numa08")
		messanger.notifTo(numa08)
	}
}