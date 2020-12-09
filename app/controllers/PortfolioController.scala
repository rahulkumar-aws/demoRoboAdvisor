package controllers

import javax.inject.Inject
import models.PortfolioFact
import play.api.Logger
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc._
import services.PortfolioFactService

import scala.concurrent.ExecutionContext

class PortfolioController @Inject()(portfolioFactService: PortfolioFactService)
                                   (implicit ec: ExecutionContext) extends InjectedController with I18nSupport {

  lazy val logger: Logger = Logger(getClass)

  def addPortfolioToCustomer = Action.async { implicit request =>
    val json = request.body.asJson.get
    val portfolio = json.as[PortfolioFact]
    portfolioFactService
      .addPortfolio(portfolio)
      .map(portfolio => Created(Json.toJson(portfolio)))
  }
}
