package furhatos.app.demo02

import furhatos.app.demo02.flow.*
import furhatos.app.demo02.flow.main.*
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class Demo02Skill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}