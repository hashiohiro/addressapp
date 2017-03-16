package main.address

import org.scalatestplus.play.PlaySpec
import main.framework.Identifier

class ContactIdSpec extends PlaySpec {
  "ContactId" should {
    "引数が空の状態ででインスタンス化をしようとすると例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new ContactId("")
      }
    }
    
    "引数がnullの状態でインスタンス化しようとすると例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new ContactId(null)
      }
    }
    
    "引数が32文字を超える場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        val digit32 = (1 to 33).map(i => 1).toString
        new ContactId(digit32.toString)
      }
    }
    
    "引数がUUID(ハイフンなし)の正規表現パターンとマッチしない場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new ContactId("z")
      }
    }
    
    "引数にUUID(ハイフンなしかつ大文字)が入力された場合はインスタンス化できること" in {
      new ContactId(Identifier.create)
    }
  }
}