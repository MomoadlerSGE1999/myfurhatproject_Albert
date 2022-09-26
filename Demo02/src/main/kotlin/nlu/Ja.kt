import furhatos.nlu.Intent

class Ja : Intent() {

    override fun getExamples(lang: furhatos.util.Language): List<String> {
        return listOf(
            "Ja",
            "Genau",
            "Steinau",
            "Richtig",
            "Korrekt",
            "So ist es",
            "Das ist richtig",
            "Ja, korrekt",
            "Yes",
            "Auf jeden Fall",
            "Ganz Genau",
            "Ja, so ist es",
            "Genau richtig",
            "ja, bin ich",
            "ja, das ist wahr",
            "Jap","habt ihr einen",
            "ja",
            "doch",
            "jawohl",
            "ja klar",
            "aber sicher",
            "sicher doch",
            "klar",
            "Janna"
        )
    }
}