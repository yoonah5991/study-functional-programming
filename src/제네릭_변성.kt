/*
title: 변성
page: 52 - 57
*/

interface Box<T>
open class Top
open class Middle : Top()
class Bottom : Middle()

fun covariant(box: Box<out Middle>): Box<out Middle> {
    return box
}

fun inCovariant(box: Box<in Middle>): Box<in Middle> {
    return box
}

fun main() {
    val topBox = object : Box<Top> {}
    val bottomBox = object : Box<Bottom> {}

    covariant(topBox)
    covariant(bottomBox)

    inCovariant(topBox)
    inCovariant(bottomBox)
}

/*인터페이스에서의 예*/
interface Source<out T> {}
interface Source2<in T> {}

//함수 파라미터 말고 함수안의 지역변수를 포커스로 둬서 봐야함
fun setSource(source: Source<Middle>) {
    val newSource1: Source<Top> = source  //OK - newSource1의 하위타입이 Source<Middle>이므로 가능
    val newSource2: Source<Bottom> = source
}

fun setSource2(source2: Source2<Middle>) {
    val newSource1: Source2<Top> = source2
    val newSource2: Source2<Bottom> = source2  //OK - newSource2의 상위 타입이 Source<Middle>이므로 가능
}


interface AdvancedSource<out T> {
    fun get(): T
}

interface AdvancedSource2<in T> {
    fun set(value: T)
}

fun setAdvancedSource(source: AdvancedSource<Middle>) {
    val value: Top = source.get()
    val value2: Bottom = source.get()
}

fun setAdvancedSource2(source2: AdvancedSource2<Middle>) {
    source2.set(Top())
    source2.set(Bottom())
}


val AdvancedSourceImpl = object : AdvancedSource<Middle> {
    override fun get(): Middle {
        return Bottom()
    }
}

val AdvancedSource2Impl = object : AdvancedSource2<Middle> {
    override fun set(value: Bottom) {
        //이건 안됨
    }
}

val AdvancedSource3Impl2 = object : AdvancedSource2<Middle> {
    override fun set(value: Top) {
        //이건 안됨
    }
}