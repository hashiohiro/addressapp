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
      // データ取得処理を実行する
      val result = H2ContactRepository.get(new ContactId("874B2A37B9C345E8819116003A81B9AE"))

      result.map(_.id.value mustBe("874B2A37B9C345E8819116003A81B9AE"))
      result.map(_.name.firstName mustBe("Akira"))
      result.map(_.name.lastName mustBe("Hashimoto"))
      result.map(_.email.value mustBe("hashimoto.akira@gmail.com"))
      result.map(_.phone.value mustBe("092-926-5568"))
      result.map(_.deleted mustBe(false))
    }
    
    "Contactを全件取得する" in {
      // 一覧取得処理を実行する
      val result = H2ContactRepository.list
      result.length mustBe > (0)
    }
    
    "Contactを新規登録する" in {
      // 更新処理を実行する
      val result = H2ContactRepository.add(
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
      // テストデータ用の識別子を生成する
      val identifier = Identifier.create

      val contact = new Contact(
        new ContactId(identifier),
        new PersonName("削除用", "削除用"),
        new EmailAddress("hashiohiro@gmail.com"),
        new PhoneNumber("092-926-5568"),
        false
      )
      // 論理削除用のデータを作成します
      val insertResult = H2ContactRepository.add(contact)
      
      // 論理削除を実行する
      H2ContactRepository.remove(contact)
    }
  }
}