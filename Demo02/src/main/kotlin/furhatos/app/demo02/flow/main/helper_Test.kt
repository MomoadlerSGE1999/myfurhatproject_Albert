import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Number

/*
val NummerErfragen : State = state() {
    onEntry {
        furhat.run {
            setVoice(Language.GERMAN, Gender.FEMALE, false)
            setInputLanguage(Language.GERMAN)
        }
furhat.askFor<Number>(
    "Können Sie mir bitte Ihre Patientennummer sagen?"
)
        onResponse<Number> {
            furhat.say("${it.intent}")
        }
    }
}


 */
val NummerErfragen = state {
    onEntry {
        var Patientennummer = furhat.askFor<Number>("Was ist ihre Patientennummer") {
            onResponse<DontKnow> {
                furhat.say("Das sollten sie wissen")
                reentry()
            }
        }
        furhat.say("Ihre Patientennummer ist ${Patientennummer}")
        // TODO Test: Patientennummer muss fünfstellig sein und darf nur aus Zahlen bestehen
    }
}


/*val NummerErfragen : State = state() {
    onEntry {
        furhat.run {
            setVoice(Language.GERMAN, Gender.FEMALE, false)
            setInputLanguage(Language.GERMAN)

            var Code = furhat.askFor<furhatos.nlu.common.Number>("Erste Zahl der Patientennummer, bitte")
            furhat.say("$Code")
            var Code2 = furhat.askFor<furhatos.nlu.common.Number>("Zweite Zahl der Patientennummer, bitte")
            furhat.say("$Code2")
            var Code3 = furhat.askFor<furhatos.nlu.common.Number>("Dritte Zahl der Patientennummer, bitte")
            furhat.say("$Code3")
            var Code4 = furhat.askFor<furhatos.nlu.common.Number>("Vierte Zahl der Patientennummer, bitte")
            furhat.say("$Code4")
            var Code5 = furhat.askFor<furhatos.nlu.common.Number>("Letzte Zahl der Patientennummer, bitte")
            furhat.say("$Code5")
        var Patientennummer2: String = Code.toString()+Code2.toString()+Code3.toString()+Code4.toString()+Code5.toString()
            var PatientennummerInt: Int = Patientennummer2.toInt()


            furhat.say("$Code")
        }

    }
}

 */

/*val NummerErfragen : State = state() {

    onEntry {
        furhat.run {
            setVoice(Language.GERMAN, Gender.FEMALE, false)
            setInputLanguage(Language.GERMAN)
    }
        /*furhat.say(
            "Können Sie mir bitte langsam und verständlich Ihre Patientennummer sagen? Ich wiederhole "
        )

         */
        furhat.listen(timeout = 10000, endSil = 1800)
    }
    onResponse<Patientennummer> {
        furhat.say("${it.intent.Patientencodenummer}, alles klar")
    }


 */




