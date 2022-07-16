package com.example.android.architecture.blueprints.todoapp.components.interfaces

import com.example.android.architecture.blueprints.todoapp.model.Acceleration

interface FallDetector {

    fun detectFall(acceleration: Acceleration)
}
