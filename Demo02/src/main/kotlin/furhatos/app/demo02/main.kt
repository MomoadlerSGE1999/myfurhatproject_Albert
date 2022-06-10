package furhatos.app.demo02

import furhatos.app.demo02.flow.*
import furhatos.app.demo02.flow.main.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class Demo02Skill : Skill() {
    override fun start() {
        Flow().run(Taxidriverdialogue)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
