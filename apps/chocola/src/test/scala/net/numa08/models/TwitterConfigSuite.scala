import org.scalatest.FunSuite

import java.io.File

import net.numa08.models._

class TwitterConfigSuite extends FunSuite {
	test("Twitter 4J COnfiguration is not null"){
		val accountFile = new File("src/resources/account.properties")
		val twitterObservation = new TwitterObservation(accountFile)
		assert(twitterObservation.t4jOauth() != null)
	}

	test("HSB Configuration is not null"){
		val accountFile = new File("src/resources/account.properties")
		val twitterObservation = new TwitterObservation(accountFile)
		assert(twitterObservation.oauth() != null)
	}
}