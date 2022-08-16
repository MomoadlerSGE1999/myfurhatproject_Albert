package furhatos.app.demo02.flow.main

import furhatos.flow.kotlin.*

val WennNameFalschPatient : State = state() {
    onEntry {
        //Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
        //user kann nicht mehr null sein deswegen können wir schreiben user!!, da user bereits gesetzt
        //TODO hier reentry falls name falsch
        //stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Vornamen ein", "vorname")
        //stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Nachnamen ein", "name")
        user!!.put("Patientennummer", null)
        GetDigitsPatient(user!!, this.furhat, "Patientennummer")
        goto(ValidierungNummerPatient)
    }
}
