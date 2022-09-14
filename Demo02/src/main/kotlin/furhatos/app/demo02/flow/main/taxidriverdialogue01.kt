package furhatos.app.demo02.flow.main

import FrageWiederholen
import Ja
import ReadExcel
import WelcherPlatzRaum
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import nlu.Nein

val Taxidriverdialogue01 : State = state(){
    onEntry {
        var raumx: Any? = user!!.get("raum")
        var platzy: Any? = user!!.get("platz")
        furhat.say(" Ich würde Sie bitten ${user!!.get("name")} im $raumx am $platzy abzuholen")
        furhat.say("Es war mir eine Freude Ihnen zu helfen")
        furhat.gesture(Gestures.Nod())
        furhat.listen(timeout = 10000)
    }
    onResponse<FrageWiederholen> {
        reentry()
    }

    onResponse<WelcherPlatzRaum> {
        reentry()
    }
    onNoResponse {
        delay(10000)
        goto(Idle)
    }
    onReentry {
        var raumy: Any? = user!!.get("raum")
        var platzx: Any? = user!!.get("platz")

        furhat.say(" In den $raumy, an den $platzx")
        goto(Idle)
    }
}