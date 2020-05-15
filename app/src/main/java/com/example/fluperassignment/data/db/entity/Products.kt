package com.example.fluperassignmet.data.db.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Entity(tableName = "Products")
data class Products(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "id") val id: Int = 0,
                    @ColumnInfo(name = "name") var name: String,
                    @ColumnInfo(name = "description") var description: String?,
                    @ColumnInfo(name = "regular_price") var regular_price: String?,
                    @ColumnInfo(name = "sale_price") var sale_price: String?,
                    @ColumnInfo(name = "product_photo") var product_photo: String?,
                    @ColumnInfo(name = "color_id") var color_id: String?,
                    @ColumnInfo(name = "store_id") var store_id: String?
)  {

}