package net.numa08.models

import twitter4j.Status

class NumaFilter(status: Status) extends Filter[Status]{

  def filter(c:Status => Unit){
			val filterRule = (status:Status) => {
				val pattern = ".*([nNｎＮ][uUｕＵ][mMｍＭ][aAａＡ]|ぬま|沼|[ヌﾇ][マﾏ])(さん|08|3|4|３|４)?.*"
				val matches = status.getText.matches(pattern)// && status.getInReplyToScreenName.isEmpty
//        val isNotReply = Option(status.getInReplyToScreenName) match {
//          case Some(name) => name.isEmpty
//          case None => false
//        }
        val isNotRetweet = !status.isRetweet

				matches && /*isNotReply &&*/ isNotRetweet
			}

      Option(status).filter(filterRule).foreach(c)
  }
}
