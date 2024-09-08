package svc.library.unibitdiplomna.dto;

public class CommentRequest
{
  private String rentId;
  private String comment;

  public String getRentId()
  {
    return rentId;
  }

  public void setRentId(String rentId)
  {
    this.rentId = rentId;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }
}
