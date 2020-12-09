package models

import play.api.libs.json.{Format, JsObject, JsResult, JsString, JsSuccess, JsValue}

case class PortfolioFact(
                     email: String,
                     pId: String,
                     pType: String,
                     amount: Double
                   ) {
  override def equals(that: Any): Boolean = true
}

object PortfolioFact {
  implicit object PortfolioFactFormat extends Format[PortfolioFact] {
    def writes(portfolioFact: PortfolioFact): JsValue = {
      val portfolioFactSeq = Seq(
        "email" -> JsString(portfolioFact.email),
        "pId" -> JsString(portfolioFact.pId),
        "pType" -> JsString(portfolioFact.pType),
        "amount" -> JsString(portfolioFact.amount.toString)
      )
      JsObject(portfolioFactSeq)
    }

    def reads(json: JsValue): JsResult[PortfolioFact] = {
      JsSuccess(PortfolioFact(
        (json \ "email").as[String],
        (json \ "pId").as[String],
        (json \ "pType").as[String],
        (json \ "amount").as[Double])
      )
    }
  }
  def tupled = (this.apply _).tupled
}
