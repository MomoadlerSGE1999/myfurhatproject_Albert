import furhatos.nlu.Intent

class Patient : Intent() {
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf("Patient", "Client", "Inpatient")
    }
}