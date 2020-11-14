package jredh.grouprate.models

case class CabinPrice(
    cabinCode: String,
    rateCode: String,
    price: BigDecimal
)