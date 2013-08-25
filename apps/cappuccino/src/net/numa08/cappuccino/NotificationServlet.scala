package net.numa08.cappuccino

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import java.util.logging.Logger
import scala.io.Source

class NotificationServlet extends HttpServlet {
  
  override def doPut(req : HttpServletRequest, resp : HttpServletResponse) {
    val status = Source.fromInputStream(req.getInputStream()).getLines().mkString("")
    NotificationServlet.log.info("status is " + status)
  }

}

object NotificationServlet {
  private val log = Logger.getLogger(NotificationServlet.getClass.getName)
}
