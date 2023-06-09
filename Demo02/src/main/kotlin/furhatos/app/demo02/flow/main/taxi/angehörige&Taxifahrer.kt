import furhatos.app.demo02.flow.Parent
import furhatos.app.demo02.flow.main.general.Benutzer
import furhatos.app.demo02.flow.main.general.GetDigitsTaxifahrer
import furhatos.app.demo02.flow.main.general.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import nlu.Ja


val AngehoerigeUndTaxifahrer : State = state(Parent) {
    onEntry {

        furhat.ask {
            +"Sind Sie hier, um Jemanden zu bringen beziehungsweise zu holen?"
            furhat.gesture(Gestures.BigSmile, async = false)
        }
    }
    onResponse<Ja> {

        //Nun wird die Variable Benutzer mit dem User überschrieben, der auf die Frage geantwortet hat.
        Benutzer = users.getUser(it.userId)

        //Der Benutzer wird von Furhat angeschaut
        furhat.attend(it.userId)

        //Mit der Funktion GetDigitsPatient wird die Frage nach der Patientennummer des Gesprächspartners gestellt.
        //User kann nicht null sein deswegen können wir schreiben Benutzer!!, da der Benutzer bereits gesetzt ist.
        GetDigitsTaxifahrer(Benutzer!!, this.furhat, "Patientennummer")
    }
    onResponse<Nein> {
        furhat.say {
            //Sollte der User weder einen Dialysetermin haben, noch jemanden abholen wollen, so
            //kann furhat ihm nicht behilflich sein und schickt ihn an den Empfang.
            +"Dann melden Sie sich bitte vorne beim Empfang"
            +blocking {
                furhat.gesture(Gestures.Nod)
            }
            +"Danke"
            delay(8000)
            goto(Idle)
        }
    }
    onResponse<FrageWiederholen> {
        reentry()
    }
    onNoResponse {
        reentry()
    }
}



