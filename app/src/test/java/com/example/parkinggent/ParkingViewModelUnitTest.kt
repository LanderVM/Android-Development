package com.example.parkinggent

import com.example.parkinggent.data.ParkingRepository
import com.example.parkinggent.model.Coordinates
import com.example.parkinggent.model.Location
import com.example.parkinggent.model.LocationAndDimension
import com.example.parkinggent.model.ParkingInfo
import com.example.parkinggent.ui.screens.homescreen.ParkingApiState
import com.example.parkinggent.ui.screens.homescreen.ParkingViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * A unit test class for ParkingViewModel.
 * Tests include the verification of UI state updates and sorting functionalities.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ParkingViewModelUnitTest {

    @Mock
    private lateinit var parkingRepositoryMock: ParkingRepository

    private lateinit var viewModel: ParkingViewModel

    private val testDispatcher = StandardTestDispatcher()

    companion object {
        val tolhuisParkingInfo = ParkingInfo(
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
        val savaanstraatParkingInfo = ParkingInfo(
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
    }

    /**
     * Sets up the ViewModel and mock responses before each test.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockParkingRepositoryResponses()
        runTest {
            viewModel = ParkingViewModel(parkingRepositoryMock)
            advanceUntilIdle()
        }
    }

    /**
     * Mocks the response of the parking repository for testing the ViewModel.
     */
    private fun mockParkingRepositoryResponses() {
        Mockito.`when`(parkingRepositoryMock.getParking()).thenReturn(
            flowOf(listOf(tolhuisParkingInfo, savaanstraatParkingInfo))
        )
    }

    /**
     * Resets resources after each test.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests if parking data is successfully fetched and updates the UI state.
     */
    @Test
    fun fetch_parking_data_updates_uiState() = runTest {
        val parkingListState = viewModel.uiState.first().parkingList
        assertEquals(listOf(tolhuisParkingInfo, savaanstraatParkingInfo), parkingListState)
    }

    /**
     * Tests if sorting by name correctly sorts the parking list in ascending order.
     */
    @Test
    fun sortParkingsByName_sorts_parking_list_by_name() = runTest {
        viewModel.sortParkingsByName()
        val parkingListState = viewModel.uiState.first().parkingList
        val expectedSortedByName = listOf(savaanstraatParkingInfo, tolhuisParkingInfo)
        assertEquals(expectedSortedByName, parkingListState)
    }

    /**
     * Tests if sorting by free places correctly sorts the parking list in descending order.
     */
    @Test
    fun sortParkingsByFreePlaces_sorts_parking_list_by_free_places() = runTest {
        viewModel.sortParkingsByFreePlaces()
        val parkingListState = viewModel.uiState.first().parkingList
        val expectedSortedByName = listOf(tolhuisParkingInfo, savaanstraatParkingInfo)
        assertEquals(expectedSortedByName, parkingListState)
    }

    /**
     * Tests successful data fetching and verifies the corresponding API state.
     */
    @Test
    fun data_fetched_successfully() = runTest {
        val mockData = listOf(tolhuisParkingInfo, savaanstraatParkingInfo)
        Mockito.`when`(parkingRepositoryMock.getParking()).thenReturn(flowOf(mockData))

        viewModel = ParkingViewModel(parkingRepositoryMock)
        advanceUntilIdle()

        assertEquals(ParkingApiState.Success, viewModel.parkingApiState)
        assertEquals(mockData, viewModel.uiState.value.parkingList)
    }
}
