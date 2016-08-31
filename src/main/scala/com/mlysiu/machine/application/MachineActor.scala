package com.mlysiu.machine.application

import akka.actor.{Actor, InvalidMessageException, Props}
import com.mlysiu.machine.application.MachineActor.{BuyProductRequest, ListProductsRequest, ProductsList}
import com.mlysiu.machine.product.application.BuyProductActor.{BuyProduct, ProductBought}
import com.mlysiu.machine.product.application.ListProductsActor.ListProducts
import com.mlysiu.machine.product.application.{BuyErrors, BuyProductActor, ListProductsActor}
import com.mlysiu.machine.product.domain.VendingProduct

import scalaz.\/

object MachineActor {

  case class ProductsList(products: Set[VendingProduct])

  case object ListProductsRequest

  case class BuyProductRequest(name: String, forCash: BigDecimal)

}

class MachineActor extends Actor {

  override def receive: Receive = {
    case ListProductsRequest =>
      context.actorOf(Props[ListProductsActor], s"ListProductActor_${System.currentTimeMillis()}") ! ListProducts
    case msg: BuyProductRequest =>
      context.actorOf(Props[BuyProductActor], s"BuyProductActor_${System.currentTimeMillis()}") ! BuyProduct(msg.name, msg.forCash)
    case msg: ProductsList =>
      context.parent ! msg // actor-per-request there is no possibility that some other actor may get this message
    case msg: \/[BuyErrors, ProductBought] =>
      context.parent ! msg
    case msg => throw InvalidMessageException(s"Method could not be parsed [$msg]")
  }
}
