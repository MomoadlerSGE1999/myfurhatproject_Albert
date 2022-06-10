import furhatos.nlu.Intent

class Taxidriver : Intent() {
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf("Taxi driver", "Taxi", "Driver")
    }

}