package furhatos.app.demo02.flow.main

import AngehoerigeUndTaxifahrer
import FrageWiederholen
import Ja
import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.User
import nlu.Nein


var Benutzer: User? = null

//Der State Greeting erbt von dem State Parent, dort ist das UserHandling definiert
val Greeting : State = state(Parent) {
    onEntry {

        //Zu Beginn des States wird definiert, dass Furhat den aktuellen User weiter anschaut
        furhat.attend(users.current)
        furhat.ask {
            //Furhat stellt eine Frage und zieht während der Frage seine Brauen hoch, async = flase sorgt dafür,
            // dass der State erst nach Beendigung der Gesture weiterläuft
            +"Haben Sie heute einen Dialysetermin?"
            furhat.gesture(Gestures.BrowRaise, async = false)
        }
    }
    onResponse<Ja> {
        furhat.attend(it.userId)
        furhat.say {
            //mit ${furhat.voice.emphasis("Ihnen")} kann Furhat einzelne Abschnitte betonen
            +"Gut, dann kann ich ${furhat.voice.emphasis("Ihnen")} weiterhelfen."
            furhat.gesture(Gestures.BigSmile, async = false)

        }
        //Nun wird die variable Benutzer mit dem User überschrieben, der auf dei Frage geantwortet hat
        Benutzer = users.getUser(it.userId)

        //Der Benutzer wird von Furhat angeschaut
        furhat.attend(it.userId)

        //Mit der Funktion GetDigitsPatient wird die Frage nach der Patientennummer des Gesprächspartners gestellt
        //user kann nicht mehr null sein deswegen können wir schreiben Benutzer!!, da der Benutzer bereits gesetzt
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




