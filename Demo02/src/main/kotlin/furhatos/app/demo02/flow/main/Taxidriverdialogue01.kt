package furhatos.app.demo02.flow.main

import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Taxidriverdialogue01 : State = state() {

    onEntry {
        furhat.ask("Ist dieser Name korrekt?"
                +Gestures.BigSmile)
    }
    onResponse<Yes> {
        furhat.say("Perfekt, ${furhat.voice.pause("100ms")}, Ich werde nun herausfinden, wo Sie Ihren Kunden abholen müssen")
    }
    onResponse<No> {
        furhat.say("Dann geben Sei bitte nochmal den Namen Ihres Patienten an")
        goto(Taxidriverdialogue)
    }

}