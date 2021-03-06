package com.cloniamix.lesson12engurazovkotlin.data

import com.cloniamix.lesson12engurazovkotlin.data.model.Bridge

class BridgesData private constructor() {

    private var bridgesList: List<Bridge> = ArrayList()

    companion object {
        private lateinit var INSTANCE: BridgesData

        fun getInstance(): BridgesData {
            if (!::INSTANCE.isInitialized) {
                synchronized(BridgesData::class.java) {
                    INSTANCE = BridgesData()
                }
            }
            return INSTANCE
        }
    }

    fun setBridgesList(bridgesList: List<Bridge>) {
        this.bridgesList = bridgesList
    }

    fun getBridgesList(): List<Bridge> {
        return bridgesList
    }
}