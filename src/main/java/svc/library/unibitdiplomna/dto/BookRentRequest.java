package svc.library.unibitdiplomna.dto;

import java.time.LocalDate;

public class BookRentRequest
{
  private Integer userId;
  private Integer bookId;
  private LocalDate returnDate;

  public Integer getUserId()
  {
    return userId;
  }

  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }

  public Integer getBookId()
  {
    return bookId;
  }

  public void setBookId(Integer bookId)
  {
    this.bookId = bookId;
  }

  public LocalDate getReturnDate()
  {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate)
  {
    this.returnDate = returnDate;
  }
}
