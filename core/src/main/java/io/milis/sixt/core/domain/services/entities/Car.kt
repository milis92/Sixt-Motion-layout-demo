package io.milis.sixt.core.domain.services.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
        tableName = "cars",
        indices = [
            Index(value = [
                "name"
            ])
        ]
)
data class Car(

        @PrimaryKey
        @SerializedName("id") val id: String,

        @ColumnInfo(name = "model_identifier")
        @SerializedName("modelIdentifier") val modelIdentifier: String,

        @ColumnInfo(name = "model_name")
        @SerializedName("modelName") val modelName: String,

        @ColumnInfo(name = "name")
        @SerializedName("name") val name: String,

        @ColumnInfo(name = "make")
        @SerializedName("make") val make: String,

        @ColumnInfo(name = "group")
        @SerializedName("group") val group: String,

        @ColumnInfo(name = "color")
        @SerializedName("color") val color: String,

        @ColumnInfo(name = "series")
        @SerializedName("series") val series: String,

        @ColumnInfo(name = "fuel_type")
        @SerializedName("fuelType") val fuelType: String,

        @ColumnInfo(name = "fuel_level")
        @SerializedName("fuelLevel") val fuelLevel: Double,

        @ColumnInfo(name = "transmission")
        @SerializedName("transmission") val transmission: String,

        @ColumnInfo(name = "license_plate")
        @SerializedName("licensePlate") val licensePlate: String,

        @ColumnInfo(name = "latitude")
        @SerializedName("latitude") val latitude: Double,

        @ColumnInfo(name = "longitude")
        @SerializedName("longitude") val longitude: Double,

        @ColumnInfo(name = "inncer_cleanliness")
        @SerializedName("innerCleanliness") val innerCleanliness: String,

        @ColumnInfo(name = "car_image_url")
        @SerializedName("carImageUrl") val carImageUrl: String
)