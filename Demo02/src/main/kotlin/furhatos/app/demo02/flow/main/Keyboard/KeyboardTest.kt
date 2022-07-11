package furhatos.app.demo02.flow.main.Keyboard

fun main(args:Array<String>) {
    var name:String
    var num1: Int
    var num2: Float
    println("Enter your name")
    name = readLine().toString()
    println("Your Name is $name")
    println("Enter number 1")
    num1 = readLine()!!.toInt() // DIe beiden !! sagen, dass der Wert nicht Null wird
    println("Your Name is $name" +
            " the number you have entered is $num1")
}