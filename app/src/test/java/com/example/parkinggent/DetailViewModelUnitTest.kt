package com.example.parkinggent

import com.example.parkinggent.data.ParkingRepository
import com.example.parkinggent.model.Coordinates
import com.example.parkinggent.model.Location
import com.example.parkinggent.model.LocationAndDimension
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.screens.detailscreen.DetailViewModel
import com.example.parkinggent.ui.screens.homescreen.ParkingApiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * A unit test class for DetailViewModel.
 * Tests the functionality related to fetching and handling parking details.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelUnitTest {

    @Mock
    private lateinit var parkingRepositoryMock: ParkingRepository

    private lateinit var viewModel: DetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    /**
     * Sets up the ViewModel and mock responses before each test.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        Mockito.`when`(parkingRepositoryMock.getParking(anyString())).thenReturn(
            flowOf(
                ParkingInfo(
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
                ), ParkingInfo(
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
            )
        )
        viewModel = DetailViewModel(parkingRepositoryMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests the successful retrieval of parking details and the corresponding state update.
     */
    @Test
    fun getParkingDetails_success_updatesState() = runTest {
        val parkingId = "Savaanstraat"
        viewModel.getParkingDetails(parkingId)

        advanceUntilIdle()

        assertNotNull(viewModel.parkingDetailState.value)
        assertEquals(ParkingApiState.Success, viewModel.parkingApiState.value)
    }

    /**
     * Tests the correct extraction of telephone numbers from the parking detail's contact information.
     */
    @Test
    fun getTelephoneNumbers_extractCorrectly() = runTest {
        val parkingId = "Tolhuis"
        viewModel.getParkingDetails(parkingId)

        advanceUntilIdle()

        val contactDetails =
            viewModel.parkingDetailState.value?.locationanddimension?.contactDetailsTelephoneNumber
        assertNotNull(contactDetails)

        val extractedNumbers = viewModel.getTelephoneNumbers(contactDetails)

        val expectedMap = mapOf(
            "09 266 29 00" to "(permanentie)",
            "09 266 29 01" to "(tijdens kantooruren)"
        )
        assertEquals(expectedMap, extractedNumbers)
    }
}
