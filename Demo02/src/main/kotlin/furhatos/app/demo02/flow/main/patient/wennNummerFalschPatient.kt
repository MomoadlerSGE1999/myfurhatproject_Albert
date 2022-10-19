package furhatos.app.demo02.flow.main.patient

import furhatos.app.demo02.flow.Parent
import furhatos.app.demo02.flow.main.general.Benutzer
import furhatos.app.demo02.flow.main.general.GetDigitsPatientnochmal
import furhatos.flow.kotlin.*

val WennNummerFalschPatient : State = state(Parent) {
    onEntry {
        //Bevor das field Patientennummer neu überschrieben wird, wird es genullt.
        Benutzer!!.put("Patientennummer", null)
        GetDigitsPatientnochmal(Benutzer!!, this.furhat, "Patientennummer")
        goto(ValidierungNummerPatient)
    }
}

