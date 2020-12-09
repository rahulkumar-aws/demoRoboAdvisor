package models

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Format, JsPath, Writes}

case class Customer(
                 email: String,
                 name: String,
                 dob: String,
                 country: String,
                 isVerified: Boolean,
                 isKYC:Boolean
               ) {
  override def equals(that: Any): Boolean = true
}

object Customer {
  val customerReadsBuilder =
    ( (JsPath \ "email").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "dob").read[String] and
      (JsPath \ "country").read[String] and
      (JsPath \ "isVerified").read[Boolean] and
      (JsPath \ "isKYC").read[Boolean]
  ) (Customer.apply _)

  implicit val customerWritesBuilder: Writes[Customer] = (
    (JsPath \ "email").write[String] and
      (JsPath \ "name").write[String] and
      (JsPath \ "dob").write[String] and
      (JsPath \ "country").write[String] and
      (JsPath \ "isVerified").write[Boolean] and
      (JsPath \ "isKYC").write[Boolean]
    )(unlift(Customer.unapply))

  implicit val customerFormat: Format[Customer] =
    Format(customerReadsBuilder, customerWritesBuilder)
}
