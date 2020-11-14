package jredh.promocombo.models

case class PromotionCombo(promotionCodes: Seq[String]) {
  override def equals(o: Any) = o match {
    case PromotionCombo(promotionCodes) =>
      this.promotionCodes.toSet == promotionCodes.toSet
    case _ => false
  }
}

object PromotionCombo {
  def apply(set: Set[String]): PromotionCombo = PromotionCombo(set.toSeq)
}
