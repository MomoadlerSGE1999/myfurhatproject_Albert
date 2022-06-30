package furhatos.app.demo02.flow.main

import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import java.sql.Timestamp

val Greeting : State = state() {

    onEntry {
        furhat.ask("Hello I am Furhat the new Service Robot are you a Taxi " +
                "Driver ${furhat.voice.pause("70ms")} or a patient in this dialisis centre?")
    }

    onResponse<Yes> {
        furhat.say{+"Okay, then i can help you to manage your orientation here."
                   +Gestures.Smile}
        goto(Dialogue01)
    }

    onResponse<No> {
        furhat.say("Okay, see you soon hopefully. Please register at the reception")
        furhat.attend(locationa)
    }

}
