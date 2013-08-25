package net.numa08.cappuccino

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest

class CappuccinoServlet extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.setContentType("text/plain")
    resp.getWriter().println("Hello , Scala workd")
  }

}
