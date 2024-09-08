package svc.library.unibitdiplomna.dto;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BookSearchCriteria
{
  @Size(max = 20)
  private String title;
  private String author;
  private String     isbn;
  private BigDecimal priceFrom;
  private BigDecimal priceTo;

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getAuthor()
  {
    return author;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public String getIsbn()
  {
    return isbn;
  }

  public void setIsbn(String isbn)
  {
    this.isbn = isbn;
  }

  public BigDecimal getPriceFrom()
  {
    return priceFrom;
  }

  public void setPriceFrom(BigDecimal priceFrom)
  {
    this.priceFrom = priceFrom;
  }

  public BigDecimal getPriceTo()
  {
    return priceTo;
  }

  public void setPriceTo(BigDecimal priceTo)
  {
    this.priceTo = priceTo;
  }
}
