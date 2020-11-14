package jredh.grouprate

import org.scalatest._
import jredh.grouprate.models._
import jredh.grouprate.util._

class BestGroupRateAppSpec extends FlatSpec with Matchers {
  import /*util.*/ CabinCodes._
  import /*util.*/ RateCodes._
  import /*util.*/ RateTypes._

  behavior of "BestGroupRateApp"

  it should "return empty sequences for incomplete data" in {
    BestGroupRateApp.getBestGroupPrices(
      Seq.empty,
      Seq.empty
    ) shouldEqual Seq.empty

    BestGroupRateApp.getBestGroupPrices(
      Seq(Rate(M1, Military)),
      Seq.empty
    ) shouldEqual Seq.empty

    BestGroupRateApp.getBestGroupPrices(
      Seq.empty,
      Seq(CabinPrice(CA, M1, 200.00))
    ) shouldEqual Seq.empty
  }

  it should "return a single price for a single rate group" in {
    val ratePrice: BigDecimal = 100.00
    val rateCode = "RATE-CODE"
    val rateGroup = "RATE-GROUP"
    val cabinCode = "CABIN-CODE"
    BestGroupRateApp.getBestGroupPrices(
      Seq(Rate(rateCode, rateGroup)),
      Seq(CabinPrice(cabinCode, rateCode, ratePrice))
    ) shouldEqual Seq(BestGroupPrice(cabinCode, rateCode, ratePrice, rateGroup))
  }

  it should "return no rates if no matching rate groups" in {
    BestGroupRateApp.getBestGroupPrices(
      Seq(Rate(M1, Military)),
      Seq(CabinPrice(CA, S1, 1000.00))
    ) shouldEqual Seq.empty
  }

  it should "return the expected rates from the given example" in {

    val sampleRates = Seq(
      Rate(M1, Military),
      Rate(M2, Military),
      Rate(S1, Senior),
      Rate(S2, Senior)
    )

    val sampleCabinPrices = Seq(
      CabinPrice(CA, M1, 200.00),
      CabinPrice(CA, M2, 250.00),
      CabinPrice(CA, S1, 225.00),
      CabinPrice(CA, S2, 260.00),
      CabinPrice(CB, M1, 230.00),
      CabinPrice(CB, M2, 260.00),
      CabinPrice(CB, S1, 245.00),
      CabinPrice(CB, S2, 270.00)
    )

    val expectedOutput = Seq(
      BestGroupPrice(CA, M1, 200.00, Military),
      BestGroupPrice(CA, S1, 225.00, Senior),
      BestGroupPrice(CB, M1, 230.00, Military),
      BestGroupPrice(CB, S1, 245.00, Senior)
    )

    val actualOutput = BestGroupRateApp.getBestGroupPrices(
      sampleRates,
      sampleCabinPrices
    )

    actualOutput shouldEqual expectedOutput
  }

}
