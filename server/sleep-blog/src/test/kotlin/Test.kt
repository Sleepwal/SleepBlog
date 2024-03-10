//import org.junit.Test
//import org.springframework.boot.test.context.SpringBootTest
//
///**
// * @author  SleepWalker
// * @date  2023/7/19 10:07
// * @version 1.0
// */
//
//@SpringBootTest
//class Test {
//    @Test
//    fun test() {
//        var a = 1 //变量
//        println(a)
//
//        val b  = 2 //常量
//        println(b)
//
//        val str = "Hello"
//        println(str.plus(99)) //基类为 Any
//        println(str == "Hello")
//        println("abc${a}de") //拼接
//        println("abc $a de")
//
//        val str1 = """
//            123
//            456
//            789
//        """.trimIndent()
//        println(str1)
//    }
//
//    @Test
//    fun collectionTest() {
//        val arr = arrayOf(1, 2, 3)
//        println(arr[0])
//
//        val list = listOf(4, 5, 6)
//
//        mutableListOf<Int>(1, 5, 2)
//            .map { it.plus(4) }
//            .filter { it > 5 }
//            .forEach { print("$it ") }
//        println()
//
//        mutableMapOf<String,Int>("1" to 3, "3" to 5)
//            .forEach { print("${it.key} ${it.value} ") }
//        println()
//
//        //二元组 三元组
//        println(Pair(1, 3).first)
//        println(Triple(1, 3, 5).third)
//    }
//
//    @Test
//    fun nullTest() {
//        var b:Int? = null
//        println(b?.plus(435)) //b为空 不执行
////        println(b!!.plus(435)) //强制执行 抛出异常
//    }
//
//    @Test
//    fun contionTest() {
//        val a = 2
//        when(a) {
//            1 -> println("结果为1")
//            in 2 .. 10 -> println("结果为2")
//            else -> println("错误")
//        }
//    }
//
//    @Test
//    fun funTest() {
//        println(sum1(1))
//        println(1 sum 4)
//    }
//
//    private fun sum1(a:Int, b:Int = 2) = a + b
//
//    private infix fun Int.sum(a:Int) = this + a
//}