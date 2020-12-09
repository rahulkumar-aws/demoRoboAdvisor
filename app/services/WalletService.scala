package services

import java.util.UUID

import dao.WalletDAO
import javax.inject.Inject
import models.Wallet

import scala.concurrent.{ExecutionContext, Future}

class WalletService @Inject()(dao: WalletDAO)(implicit ex: ExecutionContext) {

  def getWallet(email: String): Future[Option[Wallet]] = {
    dao.getWallet(email)
  }
  def addWallet(wallet: Wallet): Future[Wallet] = {
    dao.addWallet(wallet)
  }
  def updateWallet(wallet: Wallet): Future[Wallet] = {
    dao.updateWallet(wallet)
  }

  def roboAdvisorRefCode():String = {
    UUID.randomUUID().toString
  }
}
