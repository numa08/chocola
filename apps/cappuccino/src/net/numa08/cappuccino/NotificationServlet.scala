package net.numa08.cappuccino

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import java.util.logging.Logger
import scala.io.Source

import net.numa08.cappuccino.models._

class NotificationServlet extends HttpServlet {
  
  override def doPut(req : HttpServletRequest, resp : HttpServletResponse) {
    val status = Source.fromInputStream(req.getInputStream()).getLines().mkString("")
    status.isEmpty match {
     case true => {Unit}
     case _ => {
        
     }
   }
  }

}

object NotificationServlet {
  private val log = Logger.getLogger(NotificationServlet.getClass.getName)
}
