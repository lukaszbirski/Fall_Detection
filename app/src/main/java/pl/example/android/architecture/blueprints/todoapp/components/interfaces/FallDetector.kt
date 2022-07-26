package pl.example.android.architecture.blueprints.todoapp.components.interfaces

import pl.example.android.architecture.blueprints.todoapp.model.Acceleration

interface FallDetector {

    fun detectFall(acceleration: Acceleration)
}
