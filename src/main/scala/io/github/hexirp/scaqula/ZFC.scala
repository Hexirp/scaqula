package io.github.hexirp.scaqula

/** [[ZFC]] は、ZFC 集合論の Scala での表現を行う。
  *
  * ここで集合とは [[ZFC.ZfcModel.Set]] の部分型である型とする。
  * 帰属関係は集合の上を渡る二項述語 [[ZFC.ZfcModel.Elem]] であるとする。
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
      def isEmptySet[X <: Set] : Elem[X, A] => Nothing
    }

    trait EmptySet {
      type A <: Set
      def ev : IsEmptySet[A]
    }

    def empty_set : EmptySet

  }

}
