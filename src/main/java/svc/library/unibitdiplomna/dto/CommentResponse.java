package svc.library.unibitdiplomna.dto;

public class CommentResponse
{
  private String bookTitle;
  private Integer bookId;
  private String comment;
  private Integer rentId;


  public String getBookTitle()
  {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle)
  {
    this.bookTitle = bookTitle;
  }

  public Integer getBookId()
  {
    return bookId;
  }

  public void setBookId(Integer bookId)
  {
    this.bookId = bookId;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public Integer getRentId()
  {
    return rentId;
  }

  public void setRentId(Integer rentId)
  {
    this.rentId = rentId;
  }
}
