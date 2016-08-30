package com.mlysiu.machine.product.api.dto

import spray.json.DefaultJsonProtocol._

case class VendingProductDTO(name: String, price: BigDecimal, quantity: Int)

object VendingProductDTO {
  implicit val productFormat = jsonFormat3(VendingProductDTO.apply)
}
