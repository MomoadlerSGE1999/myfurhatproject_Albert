package furhatos.app.demo02.flow.main


import Patient
import Taxidriver
import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gesture
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location
import org.apache.commons.codec.language.bm.Lang

val locationa = Location(1.0,0.0,2.0)
val locationb = Location(4,0,1)
val Dialogue02 : State = state() {
    onEntry {
        furhat.ask("Sind sie Patient ${furhat.voice.pause("100ms")} oder Taxifahrer?")
    }

    onResponse<Patient>{
        goto(Patientdialogue)
    }
    onResponse<Taxidriver> {
        goto(Taxidriverdialogue)
    }

}