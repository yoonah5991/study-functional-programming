sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> FunList<T>.addHeader(head: T) = FunList.Cons(head, this)

//fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
//    FunList.Nil -> FunList.Cons(value, FunList.Nil) //제일 마지막 tail을 이걸로 바꿔치움
//    is FunList.Cons -> FunList.Cons(head, tail.appendTail(value))
//}

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = FunList.Nil): FunList<T> {
    return when (this) {
        FunList.Nil -> FunList.Cons(value, acc).reverse()
        is FunList.Cons -> tail.appendTail(value, acc.addHeader(head))
    }
}

fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> {
    println("reverse :::   this$this ///// acc $acc")
    return when (this) {
        FunList.Nil -> acc
        is FunList.Cons -> tail.reverse(acc.addHeader(head))
    }
}

fun <T> FunList<T>.getTail() = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> tail
}

fun <T> FunList<T>.getHead() = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

/* 연습문제 5-4 */
fun <T> FunList<T>.drop(n: Int): FunList<T> {
    return when (this) {
        FunList.Nil -> this
        is FunList.Cons -> if (n <= 0) this else this.getTail().drop(n - 1)
    }
}

//연습문제 5-5
tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> =
    when (this) {
        FunList.Nil -> this
        is FunList.Cons -> if (p(head)) this else tail.dropWhile(p)
    }

fun <T> FunList<T>.take(n: Int, acc: FunList<T> = FunList.Nil): FunList<T> =
    when (this) {
        FunList.Nil -> acc.reverse()
        is FunList.Cons -> if (n > 0) tail.take(n - 1, acc.addHeader(head)) else acc.reverse()
    }

fun <T, R> FunList<T>.map(acc: FunList<R> = FunList.Nil, f: (T) -> R): FunList<R> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> map(acc.addHeader(f(head)), f)
}

fun <T> funListOf(vararg elements: T) = elements.toFunList()

fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> FunList.Nil
    else -> FunList.Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

//5-8
tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = FunList.Nil, f: (Int, T) -> R): FunList<R> =
    when (this) {
        FunList.Nil -> acc.reverse()
        is FunList.Cons -> tail.indexedMap(index + 1, acc.addHeader(f(index, head)), f)
    }

//foldLeft
tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.foldLeft(f(acc, head), f)
}

fun sumByFoldLeft(list: FunList<Int>): Int = list.foldLeft(0) { acc, x -> acc + x }

fun main() {
    // val list: FunList<Int> = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Nil)))
    val simpleList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Nil)))
//    val appendList = simpleList.appendTail(100)
//    val harryList = simpleList.addHeader(1111).getTail()
//    println("harry $harryList")

    //   println(simpleList.drop(1))
//    println(simpleList.dropWhile { it == 4 })
    // println(simpleList.take(1))


    val intList = funListOf(1, 2, 3, 4)
    //  println(intList)
    //  println(intList.indexedMap { i, value -> value + i })
    println(sumByFoldLeft(intList))
}


