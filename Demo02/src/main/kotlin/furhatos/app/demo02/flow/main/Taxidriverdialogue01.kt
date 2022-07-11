package furhatos.app.demo02.flow.main

import Patient
import furhat.libraries.standard.utils.attendClosestUser
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.MultiIntentCandidate
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.User

val Taxidriverdialogue01 : State = state(){
    onEntry {
        furhat.ask("Ich notiere folgenden Namen, ${user!!.get("fullname")}, stimmt das ?" )
    }
    onResponse<Yes> {

        furhat.say("Perfekt, ${furhat.voice.pause("100ms")}, Ich werde nun herausfinden, wo Sie Ihren Kunden, ${user!!.get("fullname")} abholen müssen")
    }
    onResponse<No> {
        furhat.say("Dann geben Sei bitte nochmal den Namen Ihres Patienten an")
        goto(Taxidriverdialogue)
    }

}