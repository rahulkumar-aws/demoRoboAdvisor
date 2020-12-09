package models

import play.api.libs.json.{Format, JsObject, JsResult, JsString, JsSuccess, JsValue}

case class DepositFact(
                          email: String,
                          depositId: String,
                          depositType: String, //Onetime // Monthly
                          portfolioType: String,
                          depositAmount: Double
                        ) {
  override def equals(that: Any): Boolean = true
}

object DepositFact {
  implicit object DepositFactFormat extends Format[DepositFact] {
    def writes(depositFact: DepositFact): JsValue = {
      val portfolioFactSeq = Seq(
        "email" -> JsString(depositFact.email),
        "depositId" -> JsString(depositFact.depositId),
        "depositType" -> JsString(depositFact.depositType),
        "portfolioType" -> JsString(depositFact.portfolioType),
        "depositAmount" -> JsString(depositFact.depositAmount.toString)
      )
      JsObject(portfolioFactSeq)
    }

    def reads(json: JsValue): JsResult[DepositFact] = {
      JsSuccess(DepositFact(
        (json \ "email").as[String],
        (json \ "depositId").as[String],
        (json \ "depositType").as[String],
        (json \ "portfolioType").as[String],
        (json \ "depositAmount").as[Double])
      )
    }
  }
  def tupled = (this.apply _).tupled
}
