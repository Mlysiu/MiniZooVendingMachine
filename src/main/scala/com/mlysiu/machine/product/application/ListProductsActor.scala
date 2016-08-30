package com.mlysiu.machine.product.application

import akka.actor.Actor
import com.mlysiu.machine.storage.ProductsStorage

object ListProductsActor {
  case object ListProducts
}

class ListProductsActor extends Actor {
  override def receive: Receive = {
    case _ =>
      val ret = ProductsStorage.allProducts()
      println(s"Fetched list of all products from storage! [$ret]")
      sender ! ret
  }
}
