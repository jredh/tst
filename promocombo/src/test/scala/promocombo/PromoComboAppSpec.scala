package jredh.promocombo

import org.scalatest._
import models._
import util._

class PromoComboAppSpec extends FlatSpec with Matchers with Logging {
  import PromotionCodes._
  import Constants._

  behavior of "PromoComboApp"

  it should "return an empty array of ALL promotions for an empty set of promotions" in {
    PromoComboApp.allCombinablePromotions(Seq.empty) shouldEqual Seq.empty
  }

  it should "return an empty array of COMBINABLE promotions for an empty set of promotions" in {
    PromoComboApp.combinablePromotions("", Seq.empty) shouldEqual Seq.empty
    PromoComboApp.combinablePromotions(
      "some-promotion",
      Seq.empty
    ) should contain theSameElementsAs Seq.empty
  }

  it should "return an empty array for a promotion code (empty string) not found" in {
    PromoComboApp.combinablePromotions(
      "",
      Seq(Promotion("not-empty", Seq.empty))
    ) should contain theSameElementsAs Seq.empty
  }

  it should "filter based on one-way compatibility" in {
    PromoComboApp.combinablePromotions(
      P1,
      Seq(
        Promotion(P1, Seq(P2)),
        Promotion(P2, Seq.empty)
      )
    ) should contain theSameElementsAs Seq.empty
  }

  it should "filter based on one-way compatibility (reverse)" in {
    PromoComboApp.combinablePromotions(
      P1,
      Seq(
        Promotion(P1, Seq.empty),
        Promotion(P2, Seq(P1))
      )
    ) should contain theSameElementsAs Seq.empty
  }

  it should "filter based on one-way compatibility (mutual)" in {
    PromoComboApp.combinablePromotions(
      P1,
      Seq(
        Promotion(P1, Seq(P2)),
        Promotion(P2, Seq(P1))
      )
    ) should contain theSameElementsAs Seq.empty
  }

  it should "Combine all with no exclusions" in {
    PromoComboApp.allCombinablePromotions(
      Seq(
        Promotion(P1, Seq.empty),
        Promotion(P2, Seq.empty),
        Promotion(P3, Seq.empty)
      )
    ) should contain theSameElementsAs (Seq(
      Set(P1, P2, P3)
    ) map { PromotionCombo.apply })
  }

  it should "Combine with exclusions" in {
    PromoComboApp.allCombinablePromotions(
      Seq(
        Promotion(P1, Seq(P2)),
        Promotion(P2, Seq(P1)),
        Promotion(P3, Seq.empty)
      )
    ) should contain theSameElementsAs (Seq(
      Set(P1, P3),
      Set(P2, P3)
    ) map { PromotionCombo.apply })
  }

  it should "Properly apply all exclusions" in {
    PromoComboApp.allCombinablePromotions(
      Seq(
        Promotion(P1, Seq(P2)),
        Promotion(P2, Seq(P1)),
        Promotion(P3, Seq(P1, P2))
      )
    ) should contain theSameElementsAs Seq.empty
  }

  it should "allCombinablePromotion - TST provided input" in {
    PromoComboApp.allCombinablePromotions(
      Constants.providedPromotions
    ) should contain theSameElementsAs Seq(
      PromotionCombo(Set(P1, P2)),
      PromotionCombo(Set(P1, P4, P5)),
      PromotionCombo(Set(P2, P3)),
      PromotionCombo(Set(P3, P4, P5))
    )
  }

  it should "combinablePromotions - TST provided input (P1)" in {
    PromoComboApp.combinablePromotions(
      P1,
      Constants.providedPromotions
    ) should contain theSameElementsAs Seq(
      PromotionCombo(Set(P1, P2)),
      PromotionCombo(Set(P1, P4, P5))
    )
  }

  it should "combinablePromotions - TST provided input (P3)" in {
    PromoComboApp.combinablePromotions(
      P3,
      Constants.providedPromotions
    ) should contain theSameElementsAs Seq(
      PromotionCombo(Set(P3, P2)),
      PromotionCombo(Set(P3, P4, P5))
    )
  }

  it should "test the product squared of a list (2)" in {
    PromoComboApp.productSquared(
      Seq("A", "B")
    ) should contain theSameElementsAs Seq(Set("A", "B"))
  }

  it should "test the product squared of a list (3)" in {
    PromoComboApp
      .productSquared(Seq("A", "B", "C")) should contain theSameElementsAs Seq(
      Set("A", "B"),
      Set("A", "B", "C"),
      Set("A", "C"),
      Set("B", "C")
    )
  }

  it should "provide the available promotion codes" in {
    PromoComboApp.availablePromotionCodes(
      Seq(
        Promotion("A", Seq.empty),
        Promotion("B", Seq.empty),
        Promotion("C", Seq.empty)
      )
    ) should contain theSameElementsAs Seq("A", "B", "C")
  }

  val testPromotions = Seq(
    Promotion(P1, Seq(P2)),
    Promotion(P2, Seq(P1)),
    Promotion(P3, Seq.empty)
  )

  it should "generate bad promotion combos" in {
    PromoComboApp.badPromotionCombos(
      testPromotions
    ) should contain theSameElementsAs Seq(
      Set(P1, P2)
    )

    PromoComboApp.badPromotionCombos(
      testPromotions ++ Seq(Promotion(P4, Seq(P3)))
    ) should contain theSameElementsAs Seq(
      Set(P1, P2),
      Set(P3, P4)
    )
  }

  it should "test the product squared of provided products" in {
    val productActual = PromoComboApp
      .productSquared(
        Seq(P1, P2, P3, P4, P5)
      )
      .sortBy(_.mkString(""))

    val productExpected = Seq(
      Set(P1, P2, P3, P4, P5),
      Set(P1, P2, P3, P4),
      Set(P1, P2, P3, P5),
      Set(P1, P3, P4, P5),
      Set(P1, P2, P4, P5),
      Set(P2, P3, P4, P5),
      Set(P1, P2, P3),
      Set(P1, P2, P4),
      Set(P1, P2, P5),
      Set(P1, P3, P4),
      Set(P1, P4, P5),
      Set(P1, P3, P5),
      Set(P2, P3, P4),
      Set(P2, P3, P5),
      Set(P2, P4, P5),
      Set(P3, P4, P5),
      Set(P1, P2),
      Set(P1, P3),
      Set(P1, P4),
      Set(P1, P5),
      Set(P2, P3),
      Set(P2, P4),
      Set(P2, P5),
      Set(P3, P4),
      Set(P3, P5),
      Set(P4, P5)
    ).sortBy(_.mkString(""))

    productActual should contain theSameElementsAs productExpected
  }
}
