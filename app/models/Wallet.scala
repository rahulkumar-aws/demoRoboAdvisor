package models

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Format, JsPath, Writes}

case class Wallet(
                     email: String,
                     fund: Double
                   ) {
  override def equals(that: Any): Boolean = true
}

object Wallet {
  val walletReadsBuilder =
    ( (JsPath \ "email").read[String] and
      (JsPath \ "fund").read[Double]
      ) (Wallet.apply _)

  implicit val walletWritesBuilder: Writes[Wallet] = (
    (JsPath \ "email").write[String] and
      (JsPath \ "fund").write[Double]
    )(unlift(Wallet.unapply))

  implicit val walletFormat: Format[Wallet] =
    Format(walletReadsBuilder, walletWritesBuilder)
}
case class FundReq(
                   email: String,
                   code: String,
                   fund: Double
                 ) {
  override def equals(that: Any): Boolean = true
}

object FundReq {
  val fundReqReadBuilder =
    ( (JsPath \ "email").read[String] and
      (JsPath \ "code").read[String] and
      (JsPath \ "fund").read[Double]
      ) (FundReq.apply _)

  implicit val fundReqWritesBuilder: Writes[FundReq] = (
    (JsPath \ "email").write[String] and
    (JsPath \ "code").write[String] and
      (JsPath \ "fund").write[Double]
    )(unlift(FundReq.unapply))

  implicit val fundReqFormat: Format[FundReq] =
    Format(fundReqReadBuilder, fundReqWritesBuilder)
}
