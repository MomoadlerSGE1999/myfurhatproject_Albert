package furhatos.app.demo02.flow.main

import ReadExcel
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Patientdialogue : State = state() {
    onEntry {
        furhat.ask("Gut,${user!!.get("fullname")} ${furhat.voice.pause("700ms")} dann kann ich Ihenen zeigen wo Sie heute hin müssen. Sind Sie hier für eine Dialysebehandlung?")
        furhat.gesture(Gestures.Blink(1.0, 2.5))
    }

        onResponse<Yes> {
            furhat.say("Okay. danke ich finde nun heraus wo Sie hin müssen.")
            furhat.gesture(random(Gestures.BigSmile, Gestures.Roll, Gestures.Thoughtful))
            call(ReadExcel)
        }

            onResponse<No> {
                furhat.say("Okay then please ask the person at the reception where you have to go")
                furhat.attend(locationa)
            }
        }

