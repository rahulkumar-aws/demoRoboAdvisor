package controllers

import javax.inject.Inject
import models.{Deposit, DepositFact, Wallet}
import play.api.Logger
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc._
import services.{DepositFactService, PortfolioFactService, WalletService}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

class DepositController @Inject()(depositFactService: DepositFactService, portfolioFactService: PortfolioFactService, walletService: WalletService)
                                 (implicit ec: ExecutionContext) extends InjectedController with I18nSupport {

  lazy val logger: Logger = Logger(getClass)

  private def getWalletBalance(email: String): Double = {
    val amount = walletService.getWallet(email)
    amount.map(wallet => {
      wallet.get.fund
    })
    val res = Await.result(amount, 5.seconds)
    res.get.fund
  }

  private def updateWalletBalance(email: String, deposit: Double): Double = {
    val amount = getWalletBalance(email)
    amount - deposit
  }

  private def isValidDeposit(email: String, depositAmount: Double): Boolean = {
    val walletBalance = getWalletBalance(email)
    if(walletBalance>= depositAmount) true else false
  }


  def addDepositToCustomer = Action.async { implicit request =>
    val json = request.body.asJson.get
    val depositIns = json.as[Deposit]
    depositIns.portfolioSplit.foreach(p => {
      val depositFact = DepositFact(depositIns.email, depositIns.depositId, depositIns.depositType,p.portfolioType,p.amount)
      depositFactService.addDeposit(depositFact).onComplete({
        case Success(d) => {
          val result = portfolioFactService.getPortfolio(d.email, p.portfolioType).map(_.get)
          result.onComplete({
            case Success(d) => {
              //Basic check
              if(isValidDeposit(d.email, d.amount)) {
                val _tmp = d.copy(amount = d.amount + p.amount)
                portfolioFactService.updatePortfolio(_tmp)
                val wallet = Wallet(d.email, updateWalletBalance(d.email, d.amount))
                walletService.updateWallet(wallet)
              }else {
                println("Not a valid transaction")
              }

            }
            case Failure(exception) => {
              println(exception.getMessage)
            }
          })
        }
        case Failure(exception) => {
          println("depositFactService " + exception.getMessage)
        }
      })
    })
    Future.successful(Ok(Json.toJson(depositIns)))
  }

}
