package ch.ubique.bestmap.repository

import android.content.Context
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ch.ubique.bestmap.BestMapDatabase
import io.openmobilemaps.mapscore.shared.map.coordinates.Coord
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateConversionHelperInterface
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers
import kotlinx.coroutines.Dispatchers

class PinsRepository(context: Context) {
    private val database = BestMapDatabase(AndroidSqliteDriver(BestMapDatabase.Schema, context, "bestmap.db"))
    private val pinsQueries = database.pinsQueries

    private val conversionHelper = CoordinateConversionHelperInterface.independentInstance()

    fun getAllPins() = pinsQueries.getAllPins().asFlow().mapToList(Dispatchers.IO)

    fun addPin(title: String, icon: String?, coord: Coord): Long {
        val coord4326 = conversionHelper.convert(CoordinateSystemIdentifiers.EPSG4326(), coord)
        pinsQueries.insertPin(title, icon, coord4326.y, coord4326.x)
        return pinsQueries.lastInsertRowId().executeAsOne()
    }

    fun removePin(id: Long) = pinsQueries.removePin(id)

}