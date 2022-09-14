import furhatos.nlu.Intent

class WelcherPlatzRaum : Intent() {
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf(
            "Welcher Platz", "Welcher Raum", "Welcher Raum nochmal", "Welcher Platz nochmal", "wohin muss ich nochmal", "Wohin", "Nochmal bitte", "Raum", "Platz",
            "Was hat er gesagt", "Welchen Raum", "Welchen Platz", "Wohin", "Wohin muss ich", "Wohin muss ich nochmal", "Was meintest du", "Wohin", "Wo nochmal", "Wohin nochmal"
        )
    }
}