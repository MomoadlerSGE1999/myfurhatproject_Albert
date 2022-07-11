package furhatos.app.demo02

import furhatos.app.demo02.flow.main.Dialogue02
import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.flow.main.Taxidriverdialogue
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class Demo02Skill : Skill() {
    override fun start() {
        Flow().run(Greeting)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}