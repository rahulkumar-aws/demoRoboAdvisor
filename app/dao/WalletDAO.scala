package dao

import com.google.inject.Inject
import models.Wallet
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class WalletDAO @Inject()(dbConfigProvider: DatabaseConfigProvider)(
  implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val wallets = TableQuery[WalletTable]

  def getWallet(email: String): Future[Option[Wallet]] = {
    db.run(wallets.filter(_.email === email).result.headOption)
  }

  def addWallet(wallet: Wallet): Future[Wallet] = {
    db.run(wallets += wallet).map(_ => wallet)
  }

  def updateWallet(wallet: Wallet): Future[Wallet] = {
    db.run(wallets.filter(_.email === wallet.email).update(wallet)).map(_ => wallet)
  }

  def deleteWallet(email: String): Future[Unit] = {
    db.run(wallets.filter(_.email === email).delete).map(_ => ())
  }

  private class WalletTable(tag: Tag) extends Table[Wallet](tag, "wallet") {

    def email = column[String]("email", O.PrimaryKey)
    def fund = column[Double]("fund")

    def * = (email, fund) <> ((Wallet.apply _).tupled, Wallet.unapply)
  }
}
