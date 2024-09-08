package svc.library.unibitdiplomna.dto;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookResponse
{
  private Integer id;
  private String title;
  private String author;
  private String isbn;
  private BigDecimal price;
  private LocalDate datePublished;
  private LocalDate rentDate;
  private LocalDate returnDate;
  private boolean returned;
  private Integer stock;
  private Integer stockAvailable;
  private Integer rentId;
  private String comment;


  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public LocalDate getRentDate()
  {
    return rentDate;
  }

  public void setRentDate(LocalDate rentDate)
  {
    this.rentDate = rentDate;
  }

  public LocalDate getReturnDate()
  {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate)
  {
    this.returnDate = returnDate;
  }

  public boolean isReturned()
  {
    return returned;
  }

  public void setReturned(boolean returned)
  {
    this.returned = returned;
  }

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

  public LocalDate getDatePublished()
  {
    return datePublished;
  }

  public void setDatePublished(LocalDate datePublished)
  {
    this.datePublished = datePublished;
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

  public Integer getRentId()
  {
    return rentId;
  }

  public void setRentId(Integer rentId)
  {
    this.rentId = rentId;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    if(null == comment){
      comment = "";
    }
    this.comment = comment;
  }
}
