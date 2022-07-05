package furhatos.app.demo02.flow.main

import bsh.This
import furhatos.app.demo02.flow.Parent
import furhatos.autobehavior.smileBlockDelay
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.AskName
import furhatos.nlu.common.PersonName
import org.apache.poi.ss.usermodel.Sheet
import suchePatient
import org.apache.poi.sl.usermodel.Sheet as Sheet1


val Dialogue01 : State = state() {
    onEntry {
        var Furhatname: String = random("Furhat", "Felix Felicis", "Doctor Robot")

        furhat.askFor<AskName>("Mein Name ist $Furhatname und deiner?")
    }
    onResponse<PersonName> {

        furhat.say {
            furhat.attend(user)
            +"${it.text},${furhat.voice.pause("700ms")} ein schöner Name, aber leider weiß ich nicht wie ich diesen Namen buchstabieren"
            +Gestures.ExpressSad(2.0,duration = 1.0)
            +"Bitte geben Sie Ihren Vornamen ein"
        }
        val userInputvor = readLine()
        furhat.say("Bitte geben Sie auch ihren Nachnamen ein")
        val userInputnach = readLine()
        fun getName(vorname: String? = userInputvor, name: String? = userInputnach): String =
            "$userInputvor $userInputnach"

        val vollername: String = getName()
        furhat.say("Alles klar,von jetzt an nenne ich dich $vollername")
        //furhat.say{suchePatient()}
        //val Patient = suchePatient(Sheet, col = 10, searchName = vollername)

        println(vollername)

        goto(Dialogue02)
    }

}



