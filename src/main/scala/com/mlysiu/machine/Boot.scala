package com.mlysiu.machine

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import com.mlysiu.machine.product.api.MachineRoutesActor
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Boot extends App {

  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[MachineRoutesActor], "route-actor")

  implicit val timeout = Timeout(10.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)

}
