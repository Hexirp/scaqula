package io.github.hexirp.scaqula

/** [[ZFC]] は、ZFC 集合論の Scala での表現を行う。
  *
  * ここで集合とは [[ZFC.ZfcModel.Set]] の部分型である型とする。
  * 帰属関係は集合の上を渡る二項述語 [[ZFC.ZfcModel.Elem]] であるとする。
  *
  * ある集合の間の等しさは [[=:=]] で表される。[[<:<]] は特に意味を持たない。
  * {{{Set <:< Set}}} が正しいゆえに {{{Set}}} も集合であることになってしまうが、
  * そのことはモデルの内部からでは意味を持たない。
  *
  * [[https://ja.wikipedia.org/w/index.php?title=公理的集合論&oldid=61603666]] を参考にしているが、
  * 数学の公理系に著作権は存在しないと思われるためライセンスには違反しない。
  *
  */
object ZFC {

  trait ZfcModel {

    type Set
    type Elem[_ <: Set, _ <: Set]

    trait ExtensionalEquality[A <: Set, B <: Set] {
      def left[X <: Set] : Elem[X, A] => Elem[X, B]
      def right[X <: Set] : Elem[X, B] => Elem[X, A]
    }

    def extension[A <: Set, B <: Set] : ExtensionalEquality[A, B] => A =:= B

    trait IsEmptySet[A <: Set] {
      def left[X <: Set] : Elem[X, A] => Nothing
      def right[X <: Set] : Nothing => Elem[X, A] = x => x
    }

    trait EmptySet {
      type A <: Set
      def ev : IsEmptySet[A]
    }

    def empty_set : EmptySet

    type Empty = EmptySet#A

    trait IsPairSet[A <: Set, X <: Set, Y <: Set] {
      def left[Z <: Set] : Elem[Z, A] => Either[Z =:= X, Z =:= Y]
      def right[Z <: Set] : Either[Z =:= X, Z =:= Y] => Elem[Z, A]
    }

    trait PairSet[X <: Set, Y <: Set] {
      type A <: Set
      def ev : IsPairSet[A, X, Y]
    }

    def pair_set[X <: Set, Y <: Set] : PairSet[X, Y]

    type Pairing[X <: Set, Y <: Set] = PairSet[X, Y]#A

    type Singleton[X <: Set] = PairSet[X, X]#A

    trait ElemElem[A <: Set, C <: Set] {
      type B <: Set
      def left : Elem[A, B]
      def right : Elem[B, C]
    }

    trait IsUnionSet[A <: Set, X <: Set] {
      def left[Y <: Set] : Elem[Y, A] => ElemElem[Y, X]
      def right[Y <: Set] : ElemElem[Y, X] => Elem[Y, A]
    }

    trait UnionSet[X <: Set] {
      type A <: Set
      def ev : IsUnionSet[A, X]
    }

    def union_set[X <: Set] : UnionSet[X]

    type BigCup[X <: Set] = UnionSet[X]#A

    type SmallCup[X <: Set, Y <: Set] = BigCup[Pairing[X, Y]]

    trait IsInfinitySet[A <: Set] {
      def zero : Elem[Empty, A]
      def succ[X <: Set] : Elem[X, A] => Elem[SmallCup[X,Singleton[X]], A]
    }

    trait InfinitySet {
      type A <: Set
      def ev : IsInfinitySet[A]
    }

    def infinity_set : InfinitySet

    trait Subset[A <: Set, B <: Set] {
      def include[X <: Set] : Elem[X, A] => Elem[X, B]
    }

    trait IsPowerSet[A <: Set, X <: Set] {
      def left[Y <: Set] : Elem[Y, A] => Subset[Y, X]
      def right[Y <: Set] : Subset[Y, X] => Elem[Y, A]
    }

    trait PowerSet[X <: Set] {
      type A <: Set
      def ev : IsPowerSet[A, X]
    }

    def power_set[X <: Set] : PowerSet[X]

  }

}
