import furhatos.nlu.Intent

class Patient : Intent() {
    var name: String = ""
    var vorname: String = ""
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf("Patient", "Kunde", "Inpatient","Pazient","Patsient", "Patzient","Patsy and")
    }

    fun getFullName(): String =
        "$vorname $name"
}