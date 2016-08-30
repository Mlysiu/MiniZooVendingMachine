package com.mlysiu.machine.product.api.dto

import com.mlysiu.machine.application.MachineActor.ProductsList
import com.mlysiu.machine.product.api.dto.VendingProductDTO._
import com.mlysiu.machine.product.domain.VendingProduct
import spray.json.DefaultJsonProtocol._

import scala.language.implicitConversions

case class VendingProductsDTO(currentStorage: Set[VendingProductDTO])

object VendingProductsDTO {
  implicit val productsFormat = jsonFormat1(VendingProductsDTO.apply)

  implicit def products2ProductsDto(prodList: ProductsList): VendingProductsDTO =
    VendingProductsDTO(prodList.products.map(prd => VendingProductDTO(prd.name, prd.price, prd.quantity)
    ))
}


