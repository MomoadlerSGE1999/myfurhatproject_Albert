package furhatos.app.demo02.flow.main

import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import java.sql.Timestamp

val Greeting : State = state() {

    onEntry {
        furhat.ask("Hallo, ich bin furhat, der neue Serviceroboter hier im Dialysezentrum ${furhat.voice.pause("70ms")}, bist du Taxifahrer beziehungsweise Patient in diesem Dialysezentrum?"
        )
    }

    onResponse<Yes> {
        furhat.say{+"Okay, dann kann ich dir bei der Orientierung in diesem Dialysezentrum helfen."
            +Gestures.Smile}
        goto(Dialogue01)
    }

    onResponse<No> {
        furhat.say("Okay, hoffentlich sehen wir uns mal wieder. Bitte regestrieren Sie sich an der Rezeption")
        furhat.attend(locationa)
    }

}
