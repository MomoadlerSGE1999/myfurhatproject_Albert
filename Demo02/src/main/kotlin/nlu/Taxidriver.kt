import furhatos.nlu.Intent

class Taxidriver : Intent() {
    //var name: String = ""
    //var vorname: String = ""
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf("Taxi driver", "Taxi", "Driver", "Taxifahrer", "Fahrer", "ich bin Taxifahrer", "Ich bin Cara")
    }
    /*fun getFullName(): String =
        "$vorname $name"
*/
}