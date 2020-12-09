package controllers

import javax.inject.Inject
import models.{Customer, RoboCustomer, Wallet}
import play.api.Logger
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc._
import services.{CustomerService, DummyMLService, DummyVerificationService, WalletService}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class CustomerController @Inject()(customerService: CustomerService, walletService: WalletService, fakeMLService: DummyMLService, dummyVerificationService: DummyVerificationService)
                              (implicit ec: ExecutionContext) extends InjectedController with I18nSupport {

  lazy val logger: Logger = Logger(getClass)


  def addCustomer = Action.async { implicit request =>
    val json = request.body.asJson.get
    val customer = json.as[Customer]
    val cl = fakeMLService.getConfidenceLevel(customer.dob)
    val isVerify = dummyVerificationService.getCustomerVerification(customer.email)
    val isKYC = dummyVerificationService.getKYCVerification(customer.email)
    val roboCustomer = RoboCustomer(customer.email, customer.name, customer.dob, customer.country,isVerify, isKYC, cl)
    customerService
      .create(roboCustomer)
      .map(user => {
        val walletInit = Wallet(user.email, 0.0)
        walletService.addWallet(walletInit)
        Created(Json.toJson(user))
      })
  }

  def listCustomers: Action[AnyContent] = Action.async { implicit request =>
    customerService
      .getAll()
      .map(customer => Ok(Json.toJson(customer)))
  }

  def updateCustomer: Action[AnyContent] = Action.async { implicit request =>
    val json = request.body.asJson.get
    val customer = json.as[RoboCustomer]
      customerService
        .update(customer)
        .map(customer => Ok(Json.toJson(customer)))

  }

  def getCustomer(email: String): Action[AnyContent] = Action.async { implicit request =>
    Try(email) match {
      case Success(email) =>
        customerService
          .get(email)
          .map(maybeCustomer => Ok(Json.toJson(maybeCustomer)))
      case Failure(_) => Future.successful(BadRequest("Bad request"))
    }
  }
}
