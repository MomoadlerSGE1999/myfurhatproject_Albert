package furhatos.app.demo02.flow.main

import bsh.This
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.User
import kotlinx.coroutines.withTimeout
import kotlin.concurrent.timer

val Taxidriverdialogue : State = state() {
    onEntry {
        furhat.ask (
            "Sind Sie hier um einen Kunden abzuholen?"
        )
    }
    onResponse<Yes> {

        furhat.say("Bitte geben Sie den Vornamen des Kunden ein")
        furhat.attend(locationb) // Timer einfügen, da er nur für eine gewisse Zeit zur Tastatur schauen soll

        delay(3000)

        furhat.attend(it.userId)

        val userInputclientvor = readLine()

        furhat.say("Bitte geben Sie den Nachnamen des Kunden ein")

        val userInputclientnach = readLine()

        fun getName(vorname: String? = userInputclientvor, name: String? = userInputclientnach): String =
            "$userInputclientvor $userInputclientnach"

        val vollername: String = getName()
        println("Der Name Ihres Kunden ist $vollername")

        furhat.say("Also ist der Name Ihres Kunden: $vollername?")
        goto(Taxidriverdialogue01)
    }
    onResponse<No> {
        furhat.say("Okay, melden sie sich bitte an unserer Rezeption an. Es war mir eine Ehre mit Ihnen zu reden.")
        furhat.gesture(Gestures.BigSmile(1.0, 2.0))
    }


}




