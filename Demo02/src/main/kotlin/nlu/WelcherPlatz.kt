import furhatos.nlu.Intent

//Erklärung: Vergleiche Klasse "nlu.Ja"
class WelcherPlatzRaum : Intent() {
    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf(
            "Welcher furhatos.app.demo02.flow.main.readExcelNew.Platz",
            "Welcher Raum",
            "Welcher Raum nochmal",
            "Welcher furhatos.app.demo02.flow.main.readExcelNew.Platz nochmal",
            "wohin muss ich nochmal",
            "Wohin", "Nochmal bitte",
            "Raum",
            "furhatos.app.demo02.flow.main.readExcelNew.Platz",
            "Was hat er gesagt",
            "Welchen Raum",
            "Welchen furhatos.app.demo02.flow.main.readExcelNew.Platz",
            "Wohin",
            "Wohin muss ich",
            "Wohin muss ich nochmal",
            "Was meintest du",
            "Wohin",
            "Wo nochmal",
            "Wohin nochmal",
            "Welcher furhatos.app.demo02.flow.main.readExcelNew.Platz nochmal",
            "Welches Zimmer"
        )
    }
}

