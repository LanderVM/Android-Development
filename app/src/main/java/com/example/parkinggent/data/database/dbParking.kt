package com.example.parkinggent.data.database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.parkinggent.model.ParkingInfo

@Entity(tableName="parking")
data class dbParking(
    @PrimaryKey
    val name: String,
    val lastupdate: String ,
    val totalcapacity: Int,
    val availablecapacity: Int,
    val occupation: Int,
    val type: String,
    val description: String,
    val id: String,
    val openingtimesdescription: String,
    val isopennow: Boolean,
    val temporaryclosed: Boolean,
    val operatorinformation: String,
    val freeparking: Boolean,
    val urllinkaddress: String,
    val occupancytrend: String,
    val locationanddimension: LocationAndDimension,
    val location: Location,
    val text: String? = "",
    val categorie: String
)

@Entity(tableName="location")
data class Location(
    val lon: Double,
    val lat: Double
)

@Entity(tableName="locationAndDimension")
data class LocationAndDimension(
    val specificAccessInformation: List<String>,
    val level: String,
    val roadNumber: String,
    val roadName: String,
    val contactDetailsTelephoneNumber: String? = null,
    val coordinatesForDisplay: Coordinates
)
@Entity(tableName="coordinates")
data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

fun dbParking.asDomainParking(): ParkingInfo {
    return ParkingInfo(this.name, this.lastupdate, this.totalcapacity, this.availablecapacity, this.occupation,
        this.type, this.description, this.id, this.openingtimesdescription, this.isopennow, this.temporaryclosed,
        this.operatorinformation, this.freeparking, this.urllinkaddress, this.occupancytrend, null, null, this.text, this.categorie  )
}

fun Task.asDbTask(): dbTask {
    return dbTask(name = this.name,
        description = this.description
    )
}

fun List<dbTask>.asDomainTasks(): List<Task>{
    var taskList = this.map {
        Task(it.name, it.description)
    }
    return taskList
}