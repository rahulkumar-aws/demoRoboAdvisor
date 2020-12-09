package models

import play.api.libs.json.{Format, JsObject, JsResult, JsString, JsSuccess, JsValue}

case class PortfolioAmount(
                            custPortfolioId: String,
                            pType: String,
                            amount: Double
               ) {
  override def equals(that: Any): Boolean = true
}


object PortfolioAmount {
  implicit object PortfolioAmountFormat extends Format[PortfolioAmount] {

    def reads(json: JsValue): JsResult[PortfolioAmount] = {
      JsSuccess(PortfolioAmount(
        (json \ "custPortfolioId").as[String],
        (json \ "pType").as[String],
        (json \ "amount").as[Double])
      )
    }

    //override def writes(o: PortfolioAmount): JsValue = ???
    override def writes(o: PortfolioAmount): JsValue = {
      val portfolioFactSeq = Seq(
        "custPortfolioId" -> JsString(o.custPortfolioId),
        "pType" -> JsString(o.pType),
        "amount" -> JsString(o.amount.toString)
      )
      JsObject(portfolioFactSeq)
    }
  }
  def tupled = (this.apply _).tupled
}
