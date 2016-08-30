package com.mlysiu.machine.product.application

import akka.actor.Actor

object BuyProductActor {

  case class BuyProduct(name: String, cash: Int)

}

class BuyProductActor extends Actor {
  override def receive: Receive = ???
}
