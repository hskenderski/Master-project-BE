package svc.library.unibitdiplomna.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookRequest
{
  private String     title;
  private String     author;
  private String     isbn;
  private BigDecimal price;
  private Integer    stock;
  private Integer    stockAvailable;
  private LocalDate  publishDate;

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

  public BigDecimal getPrice()
  {
    return price;
  }

  public void setPrice(BigDecimal price)
  {
    this.price = price;
  }

  public Integer getStock()
  {
    return stock;
  }

  public void setStock(Integer stock)
  {
    this.stock = stock;
  }

  public Integer getStockAvailable()
  {
    return stockAvailable;
  }

  public void setStockAvailable(Integer stockAvailable)
  {
    this.stockAvailable = stockAvailable;
  }

  public LocalDate getPublishDate()
  {
    return publishDate;
  }

  public void setPublishDate(LocalDate publishDate)
  {
    this.publishDate = publishDate;
  }

}
