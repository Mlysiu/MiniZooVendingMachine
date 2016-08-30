package com.mlysiu.machine.product.api

import akka.actor.{Actor, ActorRef, ActorRefFactory, Props}
import com.mlysiu.machine.application.MachineActor.ListProductsRequest
import spray.http.MediaTypes._
import spray.routing.{HttpService, RequestContext}

class MachineRoutesActor extends Actor with MachineRoutes {
  def receive: Receive = runRoute(machineRoutes)

  def actorRefFactory: ActorRefFactory = context
}

trait MachineRoutes extends HttpService {
  def actorRefFactory: ActorRefFactory

  val machineRoutes =
    pathPrefix("machine") {
      path("products") {
        get { ctx =>
          println(s"GET on /machine/products endpoint called")
          createListingRequester(ctx) ! ListProductsRequest
        }
      } ~ (path(IntNumber) & post) { productId =>
        parameter('cash.as[Int]) { cash =>
          respondWithMediaType(`application/json`) {
            complete {
              ""
            }
          }
        }
      }
    }

  def createListingRequester(implicit ctx: RequestContext): ActorRef =
    actorRefFactory.actorOf(Props(new ListProductsRequestActor), s"ListProductsRequestActor_${System.currentTimeMillis()}")

}
