import furhatos.nlu.Intent

class Patient : Intent() {
    //var name: String = ""
    //var vorname: String = ""
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf("habt ihr", "hat Jan", "Jens", "impatient", "Bernd", "Dialysepatient", "Patient", "Client", "Inpatient", "the end", "patent", "patsient", "Patzient", "Pate", "pup tent", "Patsy end", "send", "Haitian", "partying", "Passion","habt ihr einen")
    }
}



