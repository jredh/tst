package jredh.promocombo

import models._
import util._

object PromoComboApp extends App with Logging {

  override def main(args: Array[String]): Unit = {
    import PromotionCodes._

    val allPromotionCombinations = allCombinablePromotions(
      Constants.providedPromotions
    )
    
    logger.info(s"$allPromotionCombinations")
    
    val p1Promotions = combinablePromotions(P1, Constants.providedPromotions)
    
    logger.info(s"$p1Promotions")
    
    val p3Promotions = combinablePromotions(P3, Constants.providedPromotions)
    
    logger.info(s"$p3Promotions")

  }

  def availablePromotionCodes(allPromotions: Seq[Promotion]): Seq[String] = {
    allPromotions.map { promotion =>
      promotion.code
    }
  }

  def badPromotionCombos(allPromotions: Seq[Promotion]): Seq[Set[String]] = {
    allPromotions
      .flatMap { promotion =>
        promotion.notCombinableWith.map(bad => Seq(promotion.code, bad))
      }
      .map { _.toSet }
      .distinct
      .filterNot(_ == Set.empty)
  }

  def allCombinablePromotions(
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {
    val availablePromotions: Seq[String] = availablePromotionCodes(
      allPromotions
    )

    logger.debug(s"available promotions: $availablePromotions")

    val badPromotions: Seq[Set[String]] = badPromotionCombos(allPromotions)

    logger.debug(s"bad promotions: $badPromotions")

    val sets = productSquared(availablePromotions)

    val promotionCombos = sets map { codes =>
      PromotionCombo(codes)
    }

    logger.debug(s"promotion combos: $promotionCombos")

    val filteredBadPromotions = promotionCombos.filterNot { combo =>
      badPromotions.exists { badPromotionCombination =>
        badPromotionCombination.forall(combo.promotionCodes.contains)
      }
    }

    logger.debug(s"filtered: $filteredBadPromotions")

    val filteredSubsets = filteredBadPromotions.filterNot { combo =>
      filteredBadPromotions
        .map { _.promotionCodes.toSet }
        .count(combo.promotionCodes.toSet.subsetOf(_)) > 1
    }

    logger.debug(s"filtered subsets: $filteredSubsets")

    filteredSubsets
  }

  def productSquared(seq: Seq[String]): Seq[Set[String]] = {
    (0 to seq.size)
      .flatMap(seq.combinations)
      .map { _.toSet }
      .distinct
      .filter { _.size > 1 }
  }

  def combinablePromotions(
      promotionCode: String,
      allPromotions: Seq[Promotion]
  ): Seq[PromotionCombo] = {
    allCombinablePromotions(allPromotions).filter {
      _.promotionCodes.contains(promotionCode)
    }
  }
}
