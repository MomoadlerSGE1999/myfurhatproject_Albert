package furhatos.app.demo02.flow.main

import Ja
import ReadExcel
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

        furhat.ask("Gut, Patient ${user!!.get("name")}, ${furhat.voice.pause("700ms")} darf ich Ihnen Ihren Raum mitteilen, in dem Ihre Dialyse stattfinden wird?"
        //,interruptable = true
            )
        furhat.gesture(Gestures.Blink(1.0, 2.5))
        furhat.gesture(Gestures.Smile)
    }

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

