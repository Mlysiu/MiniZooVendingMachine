package com.mlysiu.machine.product.api

import akka.actor.{Actor, ActorRef, Props}
import com.mlysiu.machine.application.MachineActor
import com.mlysiu.machine.application.MachineActor.{ListProductsRequest, ProductsList}
import com.mlysiu.machine.product.api.dto.VendingProductsDTO
import com.mlysiu.machine.product.api.dto.VendingProductsDTO._
import spray.httpx.SprayJsonSupport._
import spray.routing.RequestContext

class ListProductsRequestActor(implicit ctx: RequestContext) extends Actor {
  override def receive: Actor.Receive = {
    case ListProductsRequest => createMachineActor ! ListProductsRequest
    case msg: ProductsList =>
      println("Returning to requester list of products")
      ctx.complete(msg: VendingProductsDTO)
  }

  def createMachineActor: ActorRef =
    context.actorOf(Props[MachineActor], s"MachineActor_${System.currentTimeMillis()}")
}
