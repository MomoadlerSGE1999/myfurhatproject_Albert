package furhatos.app.demo02.flow.main

import bsh.This
import furhatos.app.demo02.flow.Parent
import furhatos.autobehavior.smileBlockDelay
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.AskName
import furhatos.nlu.common.PersonName



val Dialogue01 : State = state() {
    onEntry {
        var text1: String = random("Furhat", "Felix Felicis", "Doctor Robot")
        furhat.ask("My name is $text1 and yours?")

    }
    onResponse<PersonName> {

        furhat.say {
            furhat.attend(user)
            +"${it.text},${furhat.voice.pause("1000ms")} what a beautiful name, but unfortunately I dont know how to spell it"
            +Gestures.ExpressFear(duration = 100.0)
            +"Pleas type in your sur name"
        }
        val userInputvor = readLine()
        furhat.say("Please also type in your last name")
        val userInputnach = readLine()
        fun getName(vorname: String? = userInputvor, name: String? = userInputnach): String =
            "$userInputvor $userInputnach"

        val vollername: String = getName()
        furhat.say("So from now on I will call you $vollername")

        println(vollername)

        goto(Dialogue02)
    }

}



