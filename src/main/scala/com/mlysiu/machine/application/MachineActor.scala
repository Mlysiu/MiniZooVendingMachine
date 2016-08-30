package com.mlysiu.machine.application

import akka.actor.{Actor, Props}
import com.mlysiu.machine.application.MachineActor.{ListProductsRequest, ProductsList}
import com.mlysiu.machine.product.application.ListProductsActor
import com.mlysiu.machine.product.application.ListProductsActor.ListProducts
import com.mlysiu.machine.product.domain.VendingProduct

object MachineActor {

  case class ProductsList(products: Map[VendingProduct, Int])

  case object ListProductsRequest

  case object CurrentlyListingProducts

}

class MachineActor extends Actor {
  var originalSender = self

  override def receive: Receive = {
    case ListProductsRequest =>
      originalSender = sender
      context.actorOf(Props[ListProductsActor], s"ListProductActor_${System.currentTimeMillis()}") ! ListProducts
    case msg: ProductsList =>
      originalSender ! msg
  }
}
