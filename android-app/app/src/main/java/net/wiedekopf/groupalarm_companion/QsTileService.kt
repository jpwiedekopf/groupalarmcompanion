package net.wiedekopf.groupalarm_companion

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import kotlin.random.Random

private const val TAG = "QsTileService"

class QsTileService : TileService() {

    private var isActive: Boolean = false

    override fun onTileAdded() {
        super.onTileAdded()
        Log.i(TAG, "Added quick settings tile")
    }

    override fun onStartListening() {
        super.onStartListening()
        Log.i(TAG, "on start listening")
        this.qsTile.apply {
            label = Random.nextInt(from=0, until = 42).toString()
            state = if (isActive) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        }.updateTile()
    }

    override fun onStopListening() {
        super.onStopListening()
        Log.i(TAG, "on stop listening")
    }

    override fun onClick() {
        super.onClick()
        Log.i(TAG, "clicked tile")
        isActive = !isActive
        this.qsTile.apply {
            state = if (isActive) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        }.updateTile()
    }

}