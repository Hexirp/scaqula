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

    trait IsPairSet[A <: Set, X <: Set, Y <: Set] {
      def left[Z <: Set] : Elem[Z, A] => Either[Z =:= X, Z =:= Y]
      def right[Z <: Set] : Either[Z =:= X, Z =:= Y] => Elem[Z, A]
    }

    trait PairSet[X <: Set, Y <: Set] {
      type A <: Set
      def ev : IsPairSet[A, X, Y]
    }

    def pair_set[X <: Set, Y <: Set] : PairSet[X, Y]

  }

}
