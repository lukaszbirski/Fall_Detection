package pl.birski.falldetector.architecture.blueprints.todoapp.components.interfaces

import pl.birski.falldetector.architecture.blueprints.todoapp.model.Acceleration

interface FallDetector {

    fun detectFall(acceleration: Acceleration)
}
