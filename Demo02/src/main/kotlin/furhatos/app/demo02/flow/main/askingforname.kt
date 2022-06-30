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
        var Furhatname: String = random("Furhat", "Felix Felicis", "Doctor Robot")

        furhat.askFor<AskName>("My name is $Furhatname and yours?")
    }
    onResponse<PersonName> {

        furhat.say {
            furhat.attend(user)
            +"${it.text},${furhat.voice.pause("700ms")} what a beautiful name, but unfortunately I dont know how to spell it"
            +Gestures.ExpressSad(2.0,duration = 1.0)
            +"Please type in your sur name"
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



