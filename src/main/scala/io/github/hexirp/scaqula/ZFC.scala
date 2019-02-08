package io.github.hexirp.scaqula

/** [[ZFC]] は、ZFC 集合論の Scala での表現を行う。
  *
  * Set は集合の型である。Elem は Set -> Set -> Type というカインドを持たせたかったが、出来ないので
  * 代わりに forall A B : Type, A <\: Set -> B <\: Set -> Type という風に部分型関係で表現する。
  *
  */
class ZFC {

  class ZfcModel {

    type Set
    type Elem[_ <: Set, _ <: Set]

    def extension[A <: Set, B <: Set]() : Unit = ()

  }

}
