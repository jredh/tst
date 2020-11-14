package jredh.grouprate

import models._
import util._

object BestGroupRateApp extends App with Logging {

  import util.CabinCodes._
  import util.RateCodes._
  import util.RateTypes._

  override def main(args: Array[String]): Unit = {

    // copy and pasted from BestGroupRateAppSpec
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

    getBestGroupPrices(sampleRates, sampleCabinPrices) foreach { groupPrice => logger.info(s"$groupPrice") }

  }

  def getBestGroupPrices(
      rates: Seq[Rate],
      prices: Seq[CabinPrice]
  ): Seq[BestGroupPrice] = {
    // Get all unique cabin codes from the price map
    val cabinCodes = prices.map { cabinPrice => cabinPrice.cabinCode }.distinct
    // Get all unique rate groups from the provided rates
    val rateGroups = rates.map { rate => rate.rateGroup }.distinct

    // Create the target rate map
    val groups: Seq[CabinGroup] = cabinCodes.map { cc: String =>
      rateGroups.map { rg: String => CabinGroup(cc, rg) }
    }.flatten

    // DEBUG: Output the target rate map
    groups foreach { group =>
      logger.debug(s"Generating lowest prices for $group")
    }

    // Create a raw price map

    groups.flatMap { group =>
      val groupPrice = prices
        .filter(price =>
          rates
            .filter(rate => rate.rateGroup == group.rateGroup)
            .map { _.rateCode }
            .contains(price.rateCode)
        )
        .filter(price => price.cabinCode == group.cabinCode)
        .sortBy(_.price)
        .headOption // use this so if nothing matches, we're handling an option

      groupPrice.map { price =>
        BestGroupRateApp.toBestPrice(
          group,
          price
        )
      }
    }
  }

  // transformation method
  def toBestPrice(
      cabinGroup: CabinGroup,
      cabinPrice: CabinPrice
  ): BestGroupPrice = {
    BestGroupPrice(
      cabinGroup.cabinCode,
      cabinPrice.rateCode,
      cabinPrice.price,
      cabinGroup.rateGroup
    )
  }
}
