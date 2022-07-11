package furhatos.app.demo02.flow.main

import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Patientdialogue : State = state() {
    onEntry {
        furhat.ask("Gut,${user!!.get("fullname")} ${furhat.voice.pause("700ms")} dann kann ich dir zeigen wo du heute hin musst. Are you here for a Dialysis treatment")
        furhat.gesture(Gestures.Blink(1.0, 2.5))
    }

        onResponse<Yes> {
            furhat.say("Okay, thank you. I will figure out where you have to go now")
            furhat.gesture(random(Gestures.BigSmile, Gestures.Roll, Gestures.Thoughtful))
        }

            onResponse<No> {
                furhat.say("Okay then please ask the person at the reception where you have to go")
                furhat.attend(locationa)
            }
        }

