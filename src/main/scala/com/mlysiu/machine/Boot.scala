package com.mlysiu.machine

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.mlysiu.machine.product.api.MachineRoutesActor
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {

  implicit val system = ActorSystem("on-spray-can")

  // create and start routes actor
  val routes = system.actorOf(Props[MachineRoutesActor], "machine-routes-actor")

  implicit val timeout = Timeout(10.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(routes, interface = "localhost", port = 8080)
}
