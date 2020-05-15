package com.example.fluperassignmet.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fluperassignmet.data.db.entity.Products
import javax.sql.DataSource
@Dao
interface ProductsDao {

    @Query("SELECT * FROM Products")
    fun getAllProducts():LiveData<List<Products>>;

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM Products WHERE id=:id ")
    fun getProductUsingID(id: Int): LiveData<Products>

    @Insert
    suspend fun insertProduct(product: Products)

    @Insert
    suspend fun insertProductListFromJson(productList: List<Products>)

    @Delete
    suspend fun deleteProducts(product: Products)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProducts(product: Products)


}