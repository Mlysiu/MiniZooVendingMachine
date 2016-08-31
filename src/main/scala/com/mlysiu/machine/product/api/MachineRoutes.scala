package com.mlysiu.machine.product.api

import akka.actor.{Actor, ActorRef, ActorRefFactory, Props}
import com.mlysiu.machine.application.MachineActor.{BuyProductRequest, ListProductsRequest}
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
          val actor = createListingRequester(ctx)
          println(s"GET on /machine/products endpoint called. Handling with actor [${actor.toString()}]")
          actor ! ListProductsRequest
        }
      } ~ path("products" / Segment) { productName =>
        parameter('cash.as[Int]) { cash =>
          post { ctx =>
            println(s"POST on /machine/products/$productName with cash [$cash]")
            createBuyingProductRequester(ctx) ! BuyProductRequest(productName, cash)
          }
        }
      }
    }

  def createListingRequester(implicit ctx: RequestContext): ActorRef =
    createActorPerRequest("ListProductsRequestActor", Props(new ListProductsRequestActor))

  def createBuyingProductRequester(implicit ctx: RequestContext): ActorRef =
    createActorPerRequest("BuyProductRequestActor", Props(new BuyProductRequestActor()))

  def createActorPerRequest(actorName: String, actorProps: Props)(implicit ctx: RequestContext) =
    actorRefFactory.actorOf(actorProps, s"${actorName}_${System.currentTimeMillis()}")

}
