package com.mlysiu.machine.product.api

import akka.actor.{ActorRefFactory, Actor}
import spray.http.MediaTypes._
import spray.routing.HttpService

class MachineRoutesActor extends Actor with MachineRoutes {
  def receive: Receive = runRoute(machineRoutes)

  def actorRefFactory: ActorRefFactory = context
}

trait MachineRoutes extends HttpService {

  val machineRoutes =
    pathPrefix("machine") {
      path("products") {
        get {
          respondWithMediaType(`application/json`) {
            complete {

            }
          }
        } ~ (path(IntNumber) & post) { productId =>
          parameter('cash.as[Int]) { cash =>
            respondWithMediaType(`application/json`) {
              complete {

              }
            }
          }
        }
      }
    }

}
