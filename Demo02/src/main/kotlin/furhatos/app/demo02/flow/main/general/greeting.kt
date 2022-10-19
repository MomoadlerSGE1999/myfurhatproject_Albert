package furhatos.app.demo02.flow.main.general

import AngehoerigeUndTaxifahrer
import FrageWiederholen
import nlu.Ja
import Nein
import furhatos.app.demo02.flow.Parent
import furhatos.app.demo02.flow.main.patient.ValidierungNummerPatient
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.User



var Benutzer: User? = null

//Der State Greeting erbt von dem State Parent, dort ist das User-Handling definiert.
val Greeting : State = state(Parent) {
    onEntry {

        //Zu Beginn des States wird definiert, dass Furhat den aktuellen User weiterhin anschaut.
        furhat.attend(users.current)
        furhat.ask {
            //Furhat stellt eine Frage und zieht während der Frage seine Brauen hoch, async = flase sorgt dafür,
            //dass der State erst nach Beendigung der Gesture weiterläuft
            +"Haben Sie heute einen Dialysetermin?"
            furhat.gesture(Gestures.BrowRaise, async = false)
        }
    }
    onResponse<Ja> {
        furhat.attend(it.userId)
        furhat.say {
            //mit ${furhat.voice.emphasis("Ihnen")} kann Furhat einzelne Abschnitte betonen.
            +"Gut, dann kann ich ${furhat.voice.emphasis("Ihnen")} weiterhelfen."
            furhat.gesture(Gestures.BigSmile, async = false)

        }
        //Nun wird die Variable Benutzer mit dem User überschrieben, der auf dei Frage geantwortet hat.
        Benutzer = users.getUser(it.userId)

        //Der Benutzer wird von Furhat angeschaut.
        furhat.attend(it.userId)

        //Mit der Funktion GetDigitsPatient wird die Frage nach der Patientennummer des Gesprächspartners gestellt.
        //User kann nicht mehr null sein deswegen Benutzer!!, da der Benutzer bereits gesetzt.
        GetDigitsPatient(Benutzer!!, this.furhat, "Patientennummer")
        goto(ValidierungNummerPatient)

    }
    onResponse<Nein>  {
        furhat.attend(it.userId)
        goto(AngehoerigeUndTaxifahrer)
    }
    onResponse<FrageWiederholen> {
        furhat.attend(it.userId)
        reentry()
    }
    onResponseFailed {
        reentry()
    }
}




