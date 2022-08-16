package nlu

import furhatos.nlu.Intent

class Nein : Intent() {

    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf("Nein", "Nei", "Nain", "Fein", "rein", "sein", "ne", "nein auf gar keinen fall", "Nicht korrekt", "Falsch", "Das stimmt nicht so ganz", "Nein, ist falsch", "nope", "Falsch, das stimmt nicht", "Nö")
    }
}