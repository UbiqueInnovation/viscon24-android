package ch.ubique.bestmap.map.util

import io.openmobilemaps.mapscore.shared.graphics.common.Vec2F
import io.openmobilemaps.mapscore.shared.map.controls.TouchInterface

open class SimpleTouchListener() : TouchInterface() {
    override fun clearTouch() {
        // not used per default
    }

    override fun onClickConfirmed(posScreen: Vec2F): Boolean {
        // not used per default
        return false
    }

    override fun onClickUnconfirmed(posScreen: Vec2F): Boolean {
        // not used per default
        return false
    }

    override fun onDoubleClick(posScreen: Vec2F): Boolean {
        // not used per default
        return false
    }

    override fun onLongPress(posScreen: Vec2F): Boolean {
        // not used per default
        return false
    }

    override fun onMove(deltaScreen: Vec2F, confirmed: Boolean, doubleClick: Boolean): Boolean {
        // not used per default
        return false
    }

    override fun onMoveComplete(): Boolean {
        // not used per default
        return false
    }

    override fun onTouchDown(posScreen: Vec2F): Boolean {
        // not used per default
        return false
    }

    override fun onTwoFingerClick(posScreen1: Vec2F, posScreen2: Vec2F): Boolean {
        // not used per default
        return false
    }

    override fun onTwoFingerMove(
        posScreenOld: ArrayList<Vec2F>,
        posScreenNew: ArrayList<Vec2F>
    ): Boolean {
        // not used per default
        return false
    }

    override fun onTwoFingerMoveComplete(): Boolean {
        // not used per default
        return false
    }
}