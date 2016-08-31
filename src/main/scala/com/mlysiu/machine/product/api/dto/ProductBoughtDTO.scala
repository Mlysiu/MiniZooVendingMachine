package com.mlysiu.machine.product.api.dto

import com.mlysiu.machine.product.application.BuyProductActor.ProductBought
import spray.json.DefaultJsonProtocol._

import scala.language.implicitConversions

case class ProductBoughtDTO(productName: String, change: BigDecimal)

object ProductBoughtDTO {
  implicit val productsFormat = jsonFormat2(ProductBoughtDTO.apply)

  implicit def productBought2ProductBoughtDto(productBought: ProductBought): ProductBoughtDTO =
    ProductBoughtDTO(productBought.name, productBought.change)
}
