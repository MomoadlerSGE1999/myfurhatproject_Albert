package furhatos.app.demo02.flow.main

import FrageWiederholen
import Ja
import ReadExcel
import WelcherPlatzRaum
import furhat.libraries.standard.GesturesLib
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location

import nlu.Nein


val Patientdialogue : State = state() {

    onEntry {

        var raumy: Any? = user!!.get("raum")
        var platzx: Any? = user!!.get("platz")
        furhat.say(
            "  Gut, ${user!!.get("name")}. Ich würde Sie ${furhat.voice.emphasis("bittten")} in den ${
                furhat.voice.emphasis(
                    "$raumy"
                )
            } an den ${user!!.get("platz")} zu gehen"
        )
        furhat.gesture(Gestures.Smile)
        furhat.gesture(Gestures.Nod())
        furhat.attendNobody()

        furhat.listen()
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
/*
    onResponse<Ja> {
        var raumy: Any? = user!!.get("raum")
        furhat.say(" Ich würde Sie bitten in den ${furhat.voice.emphasis("$raumy")} an den ${user!!.get("platz")} zu gehen")
        furhat.gesture(GesturesLib.PerformBigSmile1)
        furhat.say("Es war mir eine Freude Ihnen zu helfen")
        furhat.gesture(Gestures.Nod())
        goto(Idle)
    }

    onResponse<Nein> {
        furhat.say("Okay dann fragen Sie bitte an der Rezeption wo sie hin müssen")
        furhat.attend(locationa)
    }
}


 */
