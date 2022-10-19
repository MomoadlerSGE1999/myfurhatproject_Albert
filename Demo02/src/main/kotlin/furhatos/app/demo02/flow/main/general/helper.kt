package furhatos.app.demo02.flow.main.general

import FrageWiederholen
import furhatos.app.demo02.flow.main.taxi.ValidierungNummerKunde
import furhatos.app.demo02.flow.main.patient.ValidierungNummerPatient
import furhatos.flow.kotlin.*
import furhatos.records.User


//In der helper.kt sind alle benötigten Funktionen der Interaktion definiert.

fun GetDigitsPatient (Benutzer: User, furhat: Furhat, field: String) {

//Mit furhat.askFor fragt furhat nach einem spezifischem intent. In diesem Fall eine Nummer.
    furhat.askFor<furhatos.nlu.common.Number>("Wie lautet ihre Patientennummer", timeout = 20000, endSil = 5000)
    {
        //Antwortet der Nutzer mit einer Nummer, so triggert onResponse<Number>.
        onResponse<furhatos.nlu.common.Number> {
            //Furhat schaut den User an, der geantwortet hat
            furhat.attend(it.userId)

            //Mit it.text kann das Gesagte des Nutzers manipuliert werden. Mit toString wird aus dem Gesagten
            //ein String gemacht. Anschließend werden alle Leerzeichen mit replace ersetzt.
            val x: String = it.text.toString().replace(" ".toRegex(), "")

            //Aus der Variable X werden alle Buchstaben entfernt, lediglich Zahlen bleiben über.
            //So kann der Nutzer auch sagen: "Meine Patientennummer ist XXXXX. Auch in diesem Fall,
            //würde nur die Patientennummer verarbeitet werden.
            val resultx: String = x.filter { it.isDigit() }

            //Schließlich wird das field Patientennummer mit der vollends korrekt manipulierten Variable
            //resultx beschrieben, dem nutzer ist jetzt eine Patientennummer zugeordnet.
            Benutzer.put("Patientennummer", resultx)
            goto(ValidierungNummerPatient)
        }
        onNoResponse {
        //Antwortet der Nutzer gar nicht, so startet Furhat die Frage (Funktion) nochmals.
            reentry()
        }
        onResponse<FrageWiederholen> {
            //Fordert der Nutzer Furhat auf die Frage noch einmal zu wiederholen,
            // so wiederholt auch durch diesen trigger Furhat die Frage nochmal.
            reentry()
        }
    }
}
fun GetDigitsPatientnochmal (Benutzer: User, furhat: Furhat, field: String) {
//Die GetDigitsNochmal-Funktionen unterscheiden sich nur in der Frage nach der Patientennummer.
    furhat.askFor<furhatos.nlu.common.Number>("Wie dann?", timeout = 15000, endSil = 5000) {
        onResponse<furhatos.nlu.common.Number> {
            furhat.attend(it.userId)
            val x: String = it.text.toString().replace(" ".toRegex(), "")
            val resultx: String = x.filter { it.isDigit() }
            Benutzer.put("Patientennummer", resultx)
            goto(ValidierungNummerPatient)
        }
        onResponse<FrageWiederholen> {
            reentry()
        }
    }
}
fun GetDigitsTaxifahrer(Benutzer: User, furhat: Furhat, field: String) {
//Unterscheidet sich nur in der Art und Weise wie Furhat nach der Patientennummer fragt.
    furhat.askFor<furhatos.nlu.common.Number>(
        "Was ist die Patientennummer ihres Kunden beziehungsweise Angehörigen", timeout = 20000, endSil = 5000)
    {
        onResponse<furhatos.nlu.common.Number> {
            furhat.attend(it.userId)
            val x: String = it.text.toString().replace(" ".toRegex(), "")
            val resultx: String = x.filter { it.isDigit() }
            Benutzer.put("Patientennummer", resultx)
            goto(ValidierungNummerKunde)
        }
        onNoResponse {
            //Antwortet der Nutzer gar nicht, so startet Furhat die Frage (Funktion) nochmals.
            reentry()
        }
        onResponse<FrageWiederholen> {
            //Fordert der Nutzer Furhat auf die Frage noch einmal zu wiederholen,
            // so wiederholt auch durch diesen trigger Furhat die Frage nochmal.
            reentry()
        }
    }
}

fun GetDigitsTaxifahrernochmal(Benutzer: User, furhat: Furhat, field: String) {
//Die GetDigitsNochmal-Funktionen unterscheiden sich nur in der Frage nach der Patientennummer
    furhat.askFor<furhatos.nlu.common.Number>("Wie dann?", timeout = 20000, endSil = 5000) {
        onResponse<furhatos.nlu.common.Number> {
            furhat.attend(it.userId)
            val x: String = it.text.toString().replace(" ".toRegex(), "")
            val resultx: String = x.filter { it.isDigit() }
            Benutzer.put("Patientennummer", resultx)
            goto(ValidierungNummerKunde)
        }
    }
}


