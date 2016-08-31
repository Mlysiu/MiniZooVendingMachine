package com.mlysiu.machine.product.api

import akka.actor.{Actor, ActorRef, Props}
import com.mlysiu.machine.application.MachineActor
import com.mlysiu.machine.application.MachineActor.BuyProductRequest
import com.mlysiu.machine.product.api.dto.ProductBoughtDTO
import com.mlysiu.machine.product.application.BuyProductActor.ProductBought
import com.mlysiu.machine.product.application._
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing.RequestContext

import scalaz.{-\/, \/-}

class BuyProductRequestActor(implicit ctx: RequestContext) extends Actor {
  override def receive: Actor.Receive = {
    case msg: BuyProductRequest => createMachineActor ! msg
    case \/-(productBought: ProductBought) =>
      println(s"Returning Product (proudly bought) to the caller. ProductName: [${productBought.name}], Change: [${productBought.change}]")
      ctx.complete(productBought: ProductBoughtDTO)
    case -\/(PRODUCT_DOES_NOT_EXIST) => ctx.complete(StatusCodes.OK -> "Product does not exist")
    case -\/(PRODUCT_IS_OUT_OF_STOCK) => ctx.complete(StatusCodes.OK -> "Product is out of stock")
    case -\/(NOT_ENOUGH_CASH) => ctx.complete(StatusCodes.OK -> "Not enough cash!")
    case -\/(msg) => ctx.complete(StatusCodes.InternalServerError)
  }

  def createMachineActor: ActorRef =
    context.actorOf(Props[MachineActor], s"MachineActor_${System.currentTimeMillis()}")
}
