package furhatos.app.demo02.flow.main.taxi

import furhatos.app.demo02.flow.Parent
import furhatos.app.demo02.flow.main.general.Benutzer
import furhatos.app.demo02.flow.main.general.GetDigitsTaxifahrernochmal
import furhatos.flow.kotlin.*

val WennNummerFalschTaxifahrer : State = state(Parent) {
    onEntry {
        //Bevor das field Patientennummer neu überschrieben wird, wird es genullt.
        Benutzer!!.put("Patientennummer", null)
        GetDigitsTaxifahrernochmal(Benutzer!!, this.furhat, "Patientennummer")
        goto(ValidierungNummerKunde)
    }
}

