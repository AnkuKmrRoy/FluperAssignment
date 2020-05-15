package com.example.fluperassignmet.data.repositories

import com.example.fluperassignmet.data.db.dao.ProductsDao
import com.example.fluperassignmet.data.db.entity.Products

class ProductsRepositories(private val dao: ProductsDao) {

    val products = dao.getAllProducts()


    suspend fun insetProducts(product:Products){
        dao.insertProduct(product)
    }

    fun  getProductUsingID(id:Int){
        dao.getProductUsingID(id)
    }

    suspend fun updateProducts(product: Products){
        dao.updateProducts(product)
    }

    suspend fun deleteProducts(product: Products){
        dao.deleteProducts(product)
    }

    suspend fun deleteAllProducts(){
        dao.deleteAllProducts()
    }

    suspend fun insertProductListFromJson(productList:List<Products>){
        dao.insertProductListFromJson(productList)
    }
}