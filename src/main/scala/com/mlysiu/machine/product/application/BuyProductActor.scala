package com.mlysiu.machine.product.application

import akka.actor.Actor
import com.mlysiu.machine.product.application.BuyProductActor.{ProductBought, BuyProduct}
import com.mlysiu.machine.product.domain.VendingProduct
import com.mlysiu.machine.storage.ProductsStorage

import scalaz.{-\/, \/, \/-}

object BuyProductActor {

  case class BuyProduct(name: String, cash: BigDecimal)

  case class ProductBought(name: String, change: BigDecimal)
}

class BuyProductActor extends Actor {
  def currentListOfProducts = ProductsStorage.allProducts()

  private def productExistsInStorage(productName: String): \/[BuyErrors, VendingProduct] =
    currentListOfProducts.products
      .find(_.name == productName) match {
      case Some(value) => \/-(value)
      case None => -\/(PRODUCT_DOES_NOT_EXIST)
    }

  private def productIsOutOfStock(productName: String): \/[BuyErrors, VendingProduct] =
    currentListOfProducts.products
      .find(_.name == productName)
      .filter(_.quantity != 0) match {
      case Some(value) => \/-(value)
      case None => -\/(PRODUCT_IS_OUT_OF_STOCK)
    }

  private def enoughCash(productName: String, cash: BigDecimal): \/[BuyErrors, VendingProduct] =
    currentListOfProducts.products
      .find(_.name == productName)
      .filter(_.price <= cash) match {
      case Some(value) => \/-(value)
      case None => -\/(NOT_ENOUGH_CASH)
    }

  private def buy(productName2Buy: String, cash: BigDecimal) = {
    val productNameBought = ProductsStorage.removeProduct(productName2Buy)
    ProductBought(productNameBought, calculateTheChange(productNameBought, cash))
  }

  private def calculateTheChange(productName: String, cash: BigDecimal): BigDecimal =
    currentListOfProducts.products
      .find(_.name == productName)
      .map(cash - _.price).get

  override def receive: Receive = {
    case msg: BuyProduct =>
      sender ! (for {
        _ <- productExistsInStorage(msg.name)
        _ <- productIsOutOfStock(msg.name)
        prod <- enoughCash(msg.name, msg.cash)
      } yield {
        buy(prod.name, msg.cash)
      })
  }
}

sealed trait BuyErrors

case object PRODUCT_DOES_NOT_EXIST extends BuyErrors

case object PRODUCT_IS_OUT_OF_STOCK extends BuyErrors

case object NOT_ENOUGH_CASH extends BuyErrors
