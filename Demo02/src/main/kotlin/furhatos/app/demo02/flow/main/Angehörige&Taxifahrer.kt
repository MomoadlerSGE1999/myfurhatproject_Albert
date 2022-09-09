import furhatos.app.demo02.flow.main.GetDigitsTaxifahrer
import furhatos.app.demo02.flow.main.ValidierungNummerKunde
import furhatos.app.demo02.flow.main.user
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import nlu.Nein

val AngehörigeUndTaxifahrer : State = state() {
    onEntry {

        furhat.ask() {
            +"Möchten Sie einen Angehörigen oder einen Kunden bringen beziehungsweise abholen?"
            +blocking {
                furhat.gesture(Gestures.BigSmile, async = false)
            }
        }
    }
    onResponse<Ja> {
        //Der Gesprächspartner wird nach der initialen setzung einer userID von Furhat, also nach der ersten Frage durch User definiert
//Der Parameter user kann nun genutzt werden um den Gesprächspartner in Codelogik einzubauen, zb von der Funktion stellefrage
        user = users.getUser(it.userId)
        furhat.attend(it.userId)
//Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
//user kann nicht mehr null sein deswegen können wir schreiben user!!, da user bereits gesetzt
//TODO hier reentry falls name falsch
        GetDigitsTaxifahrer(user!!, this.furhat, "Patientennummer")
//stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Vornamen ein", "vorname")
//stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Nachnamen ein", "name")

// der Name des Users wird mit den Eingabewerten des fields vorname sowie name gesetzt, der value beschreibt die Zusammensetzung des fields fullname
//user!!.put("Patientennummer", "${user!!.get("name")}, ${user!!.get("vorname")}")
//TODO wenn der name richtig ist weiter ansonsten reentry, weil name falsch
        goto(ValidierungNummerKunde)
    }
    onResponse<Nein> {
        furhat.say() {
            +"Dann melden Sie sich bitte vorne beim Empfang"
            +blocking {
                furhat.gesture(Gestures.Nod)
            }
            +"Danke"
            delay(20000)
            goto(Idle)

        }
    }
    onResponse<FrageWiederholen> {
        reentry()
    }
}
