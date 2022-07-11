package furhatos.app.demo02.flow.main

import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Patientdialogue : State = state() {
    onEntry {
        furhat.ask("Gut,${user!!.get("fullname")} ${furhat.voice.pause("700ms")} dann kann ich dir zeigen wo du heute hin musst. Sind Sie hier für eine Dialysebehndlung")
        furhat.gesture(Gestures.Blink(1.0, 2.5))
    }

        onResponse<Yes> {
            furhat.say("Okay, danke dir, ich werde jetzt herausfinden an welchen Platz du musst.")
            furhat.gesture(random(Gestures.BigSmile, Gestures.Roll, Gestures.Thoughtful))
        }

            onResponse<No> {
                furhat.say("Okay, dann fragen Sie bitte an der Rezeption wie Ihnen geholfen werden kann")
                furhat.attend(locationa)
            }
        }

