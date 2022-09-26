package furhatos.app.demo02.flow.main

import FrageWiederholen
import Ja
import Parent
import WelcherPlatzRaum
import furhat.libraries.standard.GesturesLib
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location

import nlu.Nein


val Patientdialogue : State = state(Parent) {

    onEntry {
        val raumy: Any? = user!!.get("raum")
        val platzx: Any? = user!!.get("platz")
        furhat.say (
            "Gut, ${user!!.get("name")}. Ich würde Sie ${furhat.voice.emphasis("bittten")} in " +
                    "den ${furhat.voice.emphasis("$raumy")} an den ${furhat.voice.emphasis("$platzx")} zu gehen"

        )
        furhat.say("Es war mir eine Freude Ihnen helfen zu können, einen ${furhat.voice.emphasis("schönen")} Tag noch")
        furhat.gesture(Gestures.Nod())
        furhat.gesture(Gestures.BigSmile)
        furhat.ledStrip.solid(java.awt.Color.GREEN)
        furhat.attendNobody()
        furhat.listen(timeout = 8000)
    }

    onResponse<FrageWiederholen> {
        furhat.attend(it.userId)
        reentry()
    }

    onResponse<WelcherPlatzRaum> {
        furhat.attend(it.userId)
        reentry()
    }
    onNoResponse {
        delay(6000)
        goto(Idle)
    }
    onReentry {
        var raumy: Any? = user!!.get("raum")
        var platzx: Any? = user!!.get("platz")

        furhat.say(" In den $raumy, an den $platzx")
        delay(5000)
        furhat.attendNobody()
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
