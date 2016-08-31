package com.mlysiu.machine.storage

import com.mlysiu.machine.application.MachineActor.ProductsList
import com.mlysiu.machine.product.domain.VendingProduct

object ProductsStorage {

  private var productsInStorage = Set(
    VendingProduct("Mars", 2.50, 5),
    VendingProduct("Snickers", 3.20, 19),
    VendingProduct("Grzeski", 1.99, 1),
    VendingProduct("Lion", 2.00, 5),
    VendingProduct("Twix", 2.00, 0),
    VendingProduct("Bounty", 1.99, 0)
  )

  def allProducts(): ProductsList = ProductsList(productsInStorage)

  def removeProduct(name: String) =
    productsInStorage
      .find(_.name == name)
      .map { v => productsInStorage = productsInStorage - v; v }
      .map(v => VendingProduct(v.name, v.price, v.quantity - 1))
      .map { v => productsInStorage = productsInStorage + v; v }
      .get.name
}
