package jredh.promocombo.util

object Constants {
  import PromotionCodes._
  import jredh.promocombo.models._

  val providedPromotions = Seq(
    // P1 is not combinable with P3
    Promotion(P1, Seq(P3)),
    // P2 is not combinable with P4 and P5
    Promotion(P2, Seq(P4, P5)),
    // P3 is not combinable with P1
    Promotion(P3, Seq(P1)),
    // P4 is not combinable with P2
    Promotion(P4, Seq(P2)),
    // P5 is not combinable with P2
    Promotion(P5, Seq(P2))
  )
}
