package furhatos.app.demo02.flow.main

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.User
import kotlin.concurrent.timer

val Taxidriverdialogue : State = state() {
    onEntry {
        furhat.ask (
            "Are you here to pick up a client?"
        )}
    onResponse<Yes> {

        furhat.say(
            "Please type in the sur name of your client"
        )
        //furhat.attend(locationb) // Timer einfügen, da er nur für eine gewisse Zeit zur Tastatur schauen soll
        val userInputclientvor = readLine()
        furhat.say(
            "Please also type in your clients last name"
        )
        val userInputclientnach = readLine()
        fun getName(vorname: String? = userInputclientvor, name: String? = userInputclientnach): String =
            "$userInputclientvor $userInputclientnach"

        val vollername: String = getName()
        println("Your clients name is $vollername")

        furhat.say("So your clients name is: $vollername?")
        goto(Taxidriverdialogue01)
    }
        onResponse<No> {
            furhat.say("Okay then please Sign up at our reception. It was a pleasure trying to help you!")
            furhat.gesture(Gestures.BigSmile(1.0, 2.0))
        }


   }




