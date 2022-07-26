package pl.birski.falldetector.architecture.blueprints.todoapp.components.interfaces

interface MessageSender {

    fun startSendMessages(messages: Array<String>)
}