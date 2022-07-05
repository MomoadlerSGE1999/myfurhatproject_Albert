package furhatos.app.demo02.flow.main

import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import suchePatient

val Patientdialogue : State = state(Parent) {
    onEntry {

        furhat.ask("Alles klar,${furhat.voice.pause("700ms")} dann kann ich Ihnen sagen wo Sie heute hin müssen. Sind sie hier für eine Dialysebehandlung")
        furhat.gesture(Gestures.Blink(1.0, 2.5))
    }

    onResponse<Yes> {
        furhat.say("Okay, danke für die information. Ich werde jetzt herausfinden, wo Sie heute hin müssen")
        furhat.gesture(Gestures.Thoughtful)
    }

    onResponse<No> {
        furhat.say("Okay, dann fragen Sie bitte an der Rezeption wo Sie heute hin müssen")
        furhat.attend(locationa)
        }
    }
