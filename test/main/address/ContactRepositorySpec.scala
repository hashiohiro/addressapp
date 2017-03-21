package main.address

import org.scalatestplus.play.PlaySpec
import main.infra.Identifier
import main.common.PersonName
import main.common.EmailAddress
import main.common.PhoneNumber
import main.port.repository.h2.H2ContactRepository

class H2ContactRepositorySpec extends PlaySpec {
  
  "H2ContactRepository" should {
    "Contactを1件取得する" in {
      val repos = new H2ContactRepository
      val result = repos.contactOfId(new ContactId("874B2A37B9C345E8819116003A81B9AE"))
      result.map(_.id.value mustBe("874B2A37B9C345E8819116003A81B9AE"))
      result.map(_.name.firstName mustBe("Akira"))
      result.map(_.name.lastName mustBe("Hashimoto"))
      result.map(_.email.value mustBe("hashimoto.akira@gmail.com"))
      result.map(_.phone.value mustBe("092-926-5568"))
      result.map(_.deleted mustBe(false))
    }
    
    "Contactを全件取得する" in {
      val repos = new H2ContactRepository
      val result = repos.list
      result.length mustBe > (0)
    }
    
    "Contactを新規登録する" in {
      val repos = new H2ContactRepository
      val result = repos.add(
        new Contact(
          new ContactId(Identifier.create),
          new PersonName("Hiroshi", "Hashimoto"),
          new EmailAddress("hashiohiro@gmail.com"),
          new PhoneNumber("092-926-5568"),
          false
        )
      )
    }
          
    "Contactを論理削除する" in {
      val repos = new H2ContactRepository
      val identifier = Identifier.create

      val contact = new Contact(
        new ContactId(identifier),
        new PersonName("削除用", "削除用"),
        new EmailAddress("hashiohiro@gmail.com"),
        new PhoneNumber("092-926-5568"),
        false
      )
      // 論理削除用のデータを作成します
      val insertResult = repos.add(contact)
      
      // 論理削除を実行する
      repos.remove(contact)
    }
  }
}