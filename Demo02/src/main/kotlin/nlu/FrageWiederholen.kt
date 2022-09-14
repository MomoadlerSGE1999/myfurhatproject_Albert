import furhatos.nlu.Intent
import furhatos.nlu.TextGenerator

class FrageWiederholen : Intent() {

    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf(
            "Wiederholen",
        "Kanst du die frage wiederholen",
        "Nochmal bitte",
        "Kannst du das nochmal sagen",
        "Das habe ich nicht verstanden",
        "Was",
        "Nochmal",
        "wie bitte",
        "Was sagt er?", "Kannst du das bitte nochmal sagen",
        "Was sagtest du",
        "Hä",
        "Wie bitte",
        "Was hat er gesagt",
        "Was hast du gesagt"


        )
    }
}



