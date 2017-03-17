package main.infra

import org.scalatestplus.play.PlaySpec

class AssertSpec extends PlaySpec {
  "Assert#argumentNotEmpty" should {
    "値がが空でないことを保証する" in {
      Assert.argumentNotEmpty("foo")
    }
    
    "値が空文字だった場合は例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentNotEmpty("")
      }
    }
    
    "値がNullだった場合は例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentNotEmpty(null)
      }
    }
  }

  "Assert#argumentLength" should {
    "値の長さがmin以上、max以下であることを保証する" in {
      Assert.argumentLength("foo", 5, 2)
    }
    
    "値の長さがmin未満の場合は例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentLength("f", 5, 2)
      }
    }
    
    "値の長さがmaxより大きい場合は例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentLength("fooooo", 5, 2)
      }
    }
    
    "値の長さがminと同値の場合、例外は発生しない" in {
      Assert.argumentLength("fo", 5, 2)
    }
    
    "値の長さがmaxと同値の場合、例外は発生しない" in {
      Assert.argumentLength("foooo", 5, 2)
    }
  }

  "Assert#argumentMatch" should {
    "値が正規表現パターンにマッチしていることを保証する" in {
      Assert.argumentMatch("foo", "f..")
    }
    
    "値がNullの場合、例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentMatch(null, "f..")
      }
    }

    "値が正規表現パターンとマッチしていない場合、例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentMatch("foo", "[0-9]{3}")
      }
    }
  }

  "Assert#argumentTrue" should {
    "値がtrueであることを保証する" in {
      Assert.argumentTrue(true)
    }
    
    "値がfalseの場合、例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentTrue(false)
      }
    }
  }

  "Assert#argumentFalse" should {
    "値がfalseであることを保証する" in {
      Assert.argumentFalse(false)
    }
    
    "値がtrueの場合、例外を発生させる" in {
      a[IllegalArgumentException] mustBe thrownBy {
        Assert.argumentFalse(true)
      }
    }
  }
}