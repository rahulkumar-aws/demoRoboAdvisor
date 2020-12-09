package models

import play.api.libs.json.{Format, JsObject, JsResult, JsString, JsSuccess, JsValue}

case class RoboCustomer(
                     email: String,
                     name: String,
                     dob: String,
                     country: String,
                     isVerified: Boolean,
                     isKYC: Boolean,
                     confidence_level: String
                   ) {
  override def equals(that: Any): Boolean = true
}

object RoboCustomer {
  implicit object CustomerFormat extends Format[RoboCustomer] {
    def writes(customer: RoboCustomer): JsValue = {
      val customerSeq = Seq(
        "email" -> JsString(customer.email),
        "name" -> JsString(customer.name),
        "dob" -> JsString(customer.dob),
        "country" -> JsString(customer.country),
        "isVerified" -> JsString(customer.isVerified.toString),
        "isKYC" -> JsString(customer.isKYC.toString),
        "confidence_level" -> JsString(customer.confidence_level),
      )
      JsObject(customerSeq)
    }

    def reads(json: JsValue): JsResult[RoboCustomer] = {
      JsSuccess(RoboCustomer(
        (json \ "email").as[String],
        (json \ "name").as[String],
        (json \ "dob").as[String],
        (json \ "country").as[String],
        (json \ "isVerified").as[Boolean],
        (json \ "isKYC").as[Boolean],
        (json \ "confidence_level").as[String],
      )
      )
    }
  }
  def tupled = (this.apply _).tupled
}


