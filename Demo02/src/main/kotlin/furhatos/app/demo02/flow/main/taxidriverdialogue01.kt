package furhatos.app.demo02.flow.main

import Ja
import ReadExcel
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import nlu.Nein

val Taxidriverdialogue01 : State = state(){
    onEntry {
        var raumx: Any? = user!!.get("raum")
        furhat.say(" Ich würde Sie bitten ${furhat.voice.emphasis("${user!!.get("name")}")} im ${furhat.voice.emphasis("$raumx")} am ${user!!.get("platz")} abzuholen")
        furhat.say("Es war mir eine Freude Ihnen zu helfen")
        furhat.gesture(Gestures.Nod())
        delay(20000)
        goto(Idle)
    }
}