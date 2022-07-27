package pl.birski.falldetector.model

data class Acceleration(val x: Double, val y: Double, val z: Double, val timeStamp: Long) {

    constructor() : this(0.0, 0.0, 0.0, 0)
}
