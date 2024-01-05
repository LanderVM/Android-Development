package com.example.parkinggent

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.parkinggent.data.database.ParkingDao
import com.example.parkinggent.data.database.ParkingDb
import com.example.parkinggent.data.database.asDbParking
import com.example.parkinggent.data.database.asDomainParking
import com.example.parkinggent.model.Coordinates
import com.example.parkinggent.model.Location
import com.example.parkinggent.model.LocationAndDimension
import com.example.parkinggent.model.ParkingInfo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * A test class for the ParkingDao of the Parking Gent app.
 * It uses Room's in-memory database for testing database interactions.
 */
@RunWith(AndroidJUnit4::class)
class ParkingDaoTest {
    private lateinit var parkingDao: ParkingDao
    private lateinit var parkingDb: ParkingDb

    private var parking1 = ParkingInfo(
        name = "Tolhuis",
        lastupdate = "2023-11-29T01:17:11",
        totalcapacity = 150,
        availablecapacity = 72,
        occupation = 52,
        type = "offStreetParkingGround",
        description = "Ondergrondse parkeergarage Tolhuis in Gent",
        id = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-tolhuis",
        openingtimesdescription = "24/7",
        isopennow = true,
        temporaryclosed = false,
        operatorinformation = "Mobiliteitsbedrijf Gent",
        freeparking = false,
        urllinkaddress = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-tolhuis",
        occupancytrend = "unknown",
        locationanddimension = LocationAndDimension(
            specificAccessInformation = listOf("inrit"),
            level = "0",
            roadNumber = "?",
            roadName = "Vrijdagmarkt 1 \n9000 Gent",
            contactDetailsTelephoneNumber = "Tel.: 09 266 29 00 \n(permanentie)\nTel.: 09 266 29 01\n(tijdens kantooruren)",
            coordinatesForDisplay = Coordinates(
                latitude = 51.05713405953412,
                longitude = 3.726071777876147
            )
        ),
        location = Location(
            lon = 3.724968367281895,
            lat = 51.0637023559265
        ),
        text = null,
        categorie = "parking in LEZ"
    )
    private var parking2 = ParkingInfo(
        name = "Savaanstraat",
        lastupdate = "2024-01-04T22:45:11",
        totalcapacity = 382,
        availablecapacity = 62,
        occupation = 26,
        type = "carPark",
        description = "Ondergrondse parkeergarage Savaanstraat in Gent",
        id = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-savaanstraat",
        openingtimesdescription = "24/7",
        isopennow = true,
        temporaryclosed = false,
        operatorinformation = "Mobiliteitsbedrijf Gent",
        freeparking = false,
        urllinkaddress = "https://stad.gent/nl/mobiliteit-openbare-werken/parkeren/parkings-gent/parking-savaanstraat",
        occupancytrend = "unknown",
        locationanddimension = LocationAndDimension(
            specificAccessInformation = listOf("inrit"),
            level = "0",
            roadNumber = "?",
            roadName = "Savaanstraat 13\n9000 Gent",
            contactDetailsTelephoneNumber = "Tel.: 09 266 29 40",
            coordinatesForDisplay = Coordinates(
                latitude = 51.04877362543108,
                longitude = 3.7234627726667133
            )
        ),
        location = Location(
            lon = 3.7234627726667133,
            lat = 51.04877362543108
        ),
        text = null,
        categorie = "parking in LEZ"
    )

    /**
     * Inserts a single parking entry into the test database.
     */
    private suspend fun addOneParkingToDb() {
        parkingDao.insert(parking1.asDbParking())
    }

    /**
     * Inserts two different parking entries into the test database.
     */
    private suspend fun addTwoParkingsToDb() {
        parkingDao.insert(parking1.asDbParking())
        parkingDao.insert(parking2.asDbParking())
    }

    /**
     * Prepares the in-memory database and Dao for testing.
     * This is executed before each test.
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        parkingDb = Room.inMemoryDatabaseBuilder(context, ParkingDb::class.java)
            .allowMainThreadQueries()
            .build()
        parkingDao = parkingDb.parkingDao()
    }

    /**
     * Closes the in-memory database after testing.
     * This is executed after each test.
     */
    @After
    @Throws(IOException::class)
    fun closeDb() {
        parkingDb.close()
    }

    /**
     * Tests if a single parking can be inserted into the database.
     */
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertParkingIntoDB() = runBlocking {
        addOneParkingToDb()
        val allItems = parkingDao.getAllItems().first()
        assertEquals(allItems[0].asDomainParking(), parking1)
    }

    /**
     * Tests if all parkings can be retrieved from the database.
     */
    @Test
    @Throws(Exception::class)
    fun daoGetAllParkings_returnsAllParkingsFromDB() = runBlocking {
        addTwoParkingsToDb()
        val allItems = parkingDao.getAllItems().first()
        assertEquals(allItems[0].asDomainParking(), parking1)
        assertEquals(allItems[1].asDomainParking(), parking2)
    }
}
