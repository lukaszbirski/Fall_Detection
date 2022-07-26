package pl.birski.android.architecture.blueprints.todoapp.components.interfaces

import pl.birski.android.architecture.blueprints.todoapp.model.Acceleration

interface FallDetector {

    fun detectFall(acceleration: Acceleration)
}
