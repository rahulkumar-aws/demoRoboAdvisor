package controllers

import javax.inject.Inject
import models.FundReq
import play.api.Logger
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.InjectedController
import services.WalletService

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class WalletController @Inject()( walletService: WalletService)
                                (implicit ec: ExecutionContext) extends InjectedController with I18nSupport {

  lazy val logger: Logger = Logger(getClass)

  def addFundToWallet = Action.async { implicit request =>
    val json = request.body.asJson.get
    val fundReq = json.as[FundReq]
    walletService.getWallet(fundReq.email).onComplete({
      case Success(d) => {
        val wallet = d.get.copy(fund = d.get.fund + fundReq.fund)
        walletService.updateWallet(wallet)
        logger.info(s"Wallet updated")
      }
      case Failure(exception) => {
        println(exception.getMessage)
        logger.info(s"Wallet ${exception.getMessage}")
      }
    })
    Future.successful(Ok(Json.toJson("Wallet updated")))
  }
}
