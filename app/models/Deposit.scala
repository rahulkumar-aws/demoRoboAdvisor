package models

import play.api.libs.json.{Format, JsObject, JsResult, JsString, JsSuccess, JsValue, Json}

case class PortfolioSplit(portfolioType: String, amount: Double)

case class Deposit(
                        email: String,
                        depositId: String,
                        depositType: String, //Onetime // Monthly
                        portfolioSplit: Seq[PortfolioSplit]
                      ) {
  override def equals(that: Any): Boolean = true
}

object PortfolioSplit {
  implicit val portfolioSplitFormat = Json.format[PortfolioSplit]
  implicit val portfolioSplitReads = Json.reads[PortfolioSplit]
  implicit val portfolioSplitWrites = Json.writes[PortfolioSplit]
}

object Deposit {
  implicit val depositSplitFormat = Json.format[Deposit]
  implicit val depositSplitReads = Json.reads[Deposit]
  implicit val depositSplitWrites = Json.writes[Deposit]
}
