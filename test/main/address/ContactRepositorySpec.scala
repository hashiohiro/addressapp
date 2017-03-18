package main.address

import org.scalatestplus.play.PlaySpec
import main.infra.Identifier
import main.common.PersonName
import main.common.EmailAddress
import main.common.PhoneNumber

class ContactRepositorySpec extends PlaySpec {
  
  "ContactRepository" should {
    "Contactを1件取得する" in {
      // データ取得処理を実行する
      val result = ContactRepository.get(new ContactId("874B2A37B9C345E8819116003A81B9AE"))

      result.map(_.id.value mustBe("874B2A37B9C345E8819116003A81B9AE"))
      result.map(_.name.firstName mustBe("Akira"))
      result.map(_.name.lastName mustBe("Hashimoto"))
      result.map(_.email.value mustBe("hashimoto.akira@gmail.com"))
      result.map(_.phone.value mustBe("092-926-5568"))
      result.map(_.deleted mustBe(false))
    }
    
    "Contactを全件取得する" in {
      // 一覧取得処理を実行する
      val result = ContactRepository.list
      result.length mustBe > (0)
    }
    
    "Contactを新規登録する" in {
      // 更新処理を実行する
      val result = ContactRepository.insert(
        new Contact(
          new ContactId(Identifier.create),
          new PersonName("Hiroshi", "Hashimoto"),
          new EmailAddress("hashiohiro@gmail.com"),
          new PhoneNumber("092-926-5568"),
          false
        )
      )
    }
    
    "Contactを更新する" in {
      // テストデータ用の識別子を生成する
      val identifier = Identifier.create

      // 更新テスト用のデータを新規登録する
      val insertResult = ContactRepository.insert(
        new Contact(
          new ContactId(identifier),
          new PersonName("更新用", "更新用"),
          new EmailAddress("hashiohiro@gmail.com"),
          new PhoneNumber("092-926-5568"),
          false
        )
      )

      // 更新処理を実行する
      val updateResult = ContactRepository.update(
        new Contact(
          new ContactId(identifier),
          new PersonName("更新用", "更新用"),
          new EmailAddress("foobaz@gmail.com"),
          new PhoneNumber("092-926-5586"),
          false
        )
      )
      
      // 影響を受けた行が1件の場合はテスト成功
      updateResult mustBe 1
    }
    
    "Contactを論理削除する" in {
      // テストデータ用の識別子を生成する
      val identifier = Identifier.create

      // 論理削除用のデータを作成します
      val insertResult = ContactRepository.insert(
        new Contact(
          new ContactId(identifier),
          new PersonName("削除用", "削除用"),
          new EmailAddress("hashiohiro@gmail.com"),
          new PhoneNumber("092-926-5568"),
          false
        )
      )
      
      // 論理削除を実行する
      ContactRepository.logicalDelete(new ContactId(identifier))
    }
  }
}