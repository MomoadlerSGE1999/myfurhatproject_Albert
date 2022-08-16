package furhatos.app.demo02.flow.main

import Ja
import ReadExcel
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import nlu.Nein

val Taxidriverdialogue01 : State = state(){
    onEntry {
        furhat.ask("Darf ich Ihnen mitteilen, wo Sie Herrn ${user!!.get("name")} finden werden?"
        //, interruptable = true
        )
    }
    onResponse<Ja> {
        var raumx: Any? = user!!.get("raum")
        furhat.say(" Ich würde Sie bitten Ihren Kunden in den ${furhat.voice.emphasis("$raumx")} an den ${user!!.get("platz")} abzuholen")
        furhat.say("Es war mir eine Freude Ihnen zu helfen")
        furhat.gesture(Gestures.Nod())
        goto(Idle)

    }
    onResponse<Nein> {
        furhat.say("Dann fragen Sie bitte vorne an der Rezeption nach")
        furhat.attend(locationa)
        goto(Idle)
    }

}