package furhatos.app.demo02.flow.main

import ReadExcel
import furhat.libraries.standard.GesturesLib
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Patientdialogue : State = state() {
    onEntry {
        furhat.ask("Gut, Herr ${user!!.get("name")}, ${furhat.voice.pause("700ms")} dann kann ich Ihnen zeigen wo Sie heute hin müssen. Sind Sie hier für eine Dialysebehandlung?")
        furhat.gesture(Gestures.Blink(1.0, 2.5))
        furhat.gesture(Gestures.Smile)
    }

        onResponse<Yes> {
            call(ReadExcel)
            furhat.gesture(GesturesLib.PerformBigSmile1)
            furhat.say("Es war mir eine Freude Ihnen zu helfen")
            furhat.gesture(Gestures.Nod())
            delay(10000)
            goto(Idle)
        }

            onResponse<No> {
                furhat.say("Okay dann fragen Sie bitte an der Rezeption wo sie hin müssen")
                furhat.attend(locationa)
            }
        }

