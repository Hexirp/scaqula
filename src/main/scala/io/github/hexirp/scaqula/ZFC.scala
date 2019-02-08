package io.github.hexirp.scaqula

/** [[ZFC]] は、ZFC 集合論の Scala での表現を行う。
  *
  * Set は集合の型である。Elem は Set -> Set -> Type というカインドを持たせたかったが、出来ないので
  * 代わりに forall A B : Type, A <\: Set -> B <\: Set -> Type という風に部分型関係で表現する。
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
      def notInclude[X <: Set] : Elem[X, A] => Nothing
    }

    trait EmptySet {
      type A <: Set
      def ev : IsEmptySet[A]
    }

    def empty_set : EmptySet

  }

}
