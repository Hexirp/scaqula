package io.github.hexirp.scaqula

/** [[Proposition]] は、Scala の上で命題論理を表現する。
  *
  * これはカリー・ハワード対応によるものである。
  *
  */
object Proposition {

  type Empty = Nothing

  type Prop[A, B] = (A, B)

  type Sum[A, B] = Either[A, B]

  type Neg[A] = A => Nothing

  def example0[A](f : Neg[Neg[A]]) : A = {
    return f (a => return a)
  }

}
