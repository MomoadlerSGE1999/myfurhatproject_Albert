import furhatos.nlu.Intent

class Patient : Intent() {
    var name: String = ""
    var vorname: String = ""
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf("Patient", "Client", "Inpatient")
    }

    fun getFullName(): String =
        "$vorname $name"
}