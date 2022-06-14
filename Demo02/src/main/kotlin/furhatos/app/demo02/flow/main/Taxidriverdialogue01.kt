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
        furhat.ask("Is that name correct?")
    }
        onResponse<Yes> {
            furhat.say("Perfect ddeddd, I will figure out where you have to pick up your client")
        }
        onResponse<No> {
            furhat.say("Then please enter the name of your client again")
        goto(Taxidriverdialogue)
        }

    }