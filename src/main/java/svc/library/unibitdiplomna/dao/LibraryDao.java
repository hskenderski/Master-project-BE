package svc.library.unibitdiplomna.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import svc.library.unibitdiplomna.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LibraryDao
{
  private final NamedParameterJdbcOperations template;

  @Autowired
  public LibraryDao(NamedParameterJdbcOperations template)
  {
    this.template = template;
  }

  public Optional<User> getUserByEmail(final String email)
  {
    final String sql =
        ""
            + "SELECT                "
            + "       first_name,    "
            + "       second_name,   "
            + "       third_name,    "
            + "       email,         "
            + "       password,      "
            + "       role,          "
            + "       age,           "
            + "       main_address   "
            + " FROM user            "
            + " WHERE email = :email ";

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("email", email);

    try {
      return template.queryForObject(sql, sp, (rs, rowNum) ->
          Optional.of(
              new User(
                  rs.getString("first_name"),
                  rs.getString("second_name"),
                  rs.getString("third_name"),
                  rs.getString("email"),
                  rs.getString("password"),
                  User.RoleName.valueOf(rs.getString("role")),
                  rs.getInt("age"),
                  rs.getString("main_address")
              )));
    }
    catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }


  public void registration(final UserRequest userRequest)
  {
    final String sql =
        ""
            +"INSERT INTO user     "
            +"     (               "
            +"       first_name    "
            +"      ,second_name   "
            +"      ,third_name    "
            +"      ,email         "
            +"      ,password      "
            +"      ,role          "
            +"      ,main_address  "
            +"      ,age           "
            +"     )               "
            +"   VALUES            "
            +"     (               "
            +"       :firstName    "
            +"      ,:secondName   "
            +"      ,:thirdName    "
            +"      ,:email        "
            +"      ,:password     "
            +"      ,:role         "
            +"      ,:mainAddress  "
            +"      ,:age          "
            +"    )                "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("firstName"   , userRequest.getFirstName())
        .addValue("secondName"  , userRequest.getSecondName())
        .addValue("thirdName"   , userRequest.getThirdName())
        .addValue("email"       , userRequest.getEmail())
        .addValue("password"    , userRequest.getPassword())
        .addValue("role"        , userRequest.getRoleName().name())
        .addValue("mainAddress" , userRequest.getMainAddress())
        .addValue("age"         , userRequest.getAge());

    template.update(sql,sp);
  }

  public boolean isEmailExists(final String email)
  {
    final String sql = "SELECT 'Y' from user where email = :email";
    try{
      return "Y".equalsIgnoreCase(template.queryForObject(sql,new MapSqlParameterSource("email", email),String.class));
    }catch (EmptyResultDataAccessException ex){
      return false;
    }
  }

  public void updateUser(final UserRequest userRequest, final String email)
  {
    final String sql =
        ""
            +" UPDATE user                      "
            +" SET first_name   = :firstName    "
            +"    ,second_name  = :secondName   "
            +"    ,third_name   = :thirdName    "
            +"    ,main_address = :mainAddress  "
            +"    ,age          = :age          "
            +" WHERE email = :email             "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("firstName"   , userRequest.getFirstName())
        .addValue("secondName"  , userRequest.getSecondName())
        .addValue("thirdName"   , userRequest.getThirdName())
        .addValue("email"       , email)
        .addValue("mainAddress" , userRequest.getMainAddress())
        .addValue("age"         , userRequest.getAge());

    template.update(sql,sp);
  }

  public Integer addBook(BookRequest book)
  {
    final String sql =
        ""
            +" INSERT INTO book  "
            +" ( title           "
            +"  ,author          "
            +"  ,isbn            "
            +"  ,price           "
            +"  ,stock           "
            +"  ,stock_available "
            +"  ,date_published) "
            +" VALUES(  :title   "
            +"        , :author  "
            +"        , :isbn    "
            +"        , :price   "
            +"        , :stock   "
            +"        , :stock   "
            +"        , :dt)     "
        ;
    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("title", book.getTitle())
        .addValue("author", book.getAuthor())
        .addValue("isbn", book.getIsbn())
        .addValue("price", book.getPrice())
        .addValue("stock",book.getStock())
        .addValue("dt", book.getPublishDate());

    KeyHolder keyHolder = new GeneratedKeyHolder();

    template.update(sql,sp, keyHolder);

    return keyHolder.getKey().intValue();

  }

  public void updateQuantity(Integer id, Integer stock)
  {
    final String sql =
        ""
          +" UPDATE book                  "
          +" SET stock = :stock           "
          +" ,   stock_available = :stock "
          +" WHERE id  = :id              "
          +" AND stock < :stock           "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("stock", stock)
        .addValue("id", id);

    template.update(sql, sp);
  }

  public void rentBook(BookRentRequest book)
  {
    final String sql =
        ""
           +" INSERT INTO user_books  "
           +"        ( user_id        "
           +"         ,book_id        "
           +"         ,rent_date      "
           +"         ,return_date    "
           +"         ,isReturned )   "
           +" VALUES (  :userId       "
           +"         , :bookId       "
           +"         ,  current_date "
           +"         , :returnDate   "
           +"         , 'N')          "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("userId",book.getUserId())
        .addValue("bookId",book.getBookId())
        .addValue("returnDate", book.getReturnDate())
    ;

    template.update(sql, sp);
  }

  public void removeRent(Integer rentId)
  {
    final String sql = " DELETE FROM user_books WHERE id = :rentId ";

    template.update(sql, new MapSqlParameterSource("rentId",rentId));
  }

  public void returnBook(Integer rentId)
  {
    final String sql =
        ""
            +" UPDATE user_books    "
            +" SET isReturned = 'O' "
            +" WHERE id = :rentId   "
        ;

    template.update(sql, new MapSqlParameterSource("rentId",rentId));
  }

  public void writeComment(CommentRequest commentRequest, String username)
  {
    final String sql =
        ""
            +" UPDATE user_books                      "
            +" SET comment = :comment                 "
            +" WHERE id = :rentId                     "
            +" AND user_id = (SELECT id               "
            +"               FROM user                "
            +"               WHERE email = :username) "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("comment", commentRequest.getComment())
        .addValue("rentId", commentRequest.getRentId())
        .addValue("username", username);
    template.update(sql, sp);
  }

  public List<BookResponse> loadBooks(Integer userId, String username)
  {
    final String sql =
        ""
            +" SELECT b.id                                            "
            +"       ,b.title                                         "
            +"       ,b.author                                        "
            +"       ,b.isbn                                          "
            +"       ,b.price                                         "
            +"       ,b.date_published                                "
            +"       ,b.stock                                         "
            +"       ,b.stock_available                               "
            +"       ,ub.rent_date                                    "
            +"       ,ub.return_date                                  "
            +"       ,ub.isReturned                                   "
            +"       ,ub.id                                           "
            +"       ,ub.comment                                      "
            +" FROM book b                                            "
            +" JOIN user_books ub on b.id = ub.book_id                "
            +" WHERE ub.user_id = CASE(   SELECT role                 "
            +"                            FROM user                   "
            +"                            WHERE email = :username)    "
            +"                     WHEN 'USER'                        "
            +"                     THEN (SELECT id                    "
            +"                          FROM user                     "
            +"                          WHERE email = :username)      "
            +"                     ELSE :id END                       "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("username", username)
        .addValue("id", userId);

    return template.query(sql, sp, (rs, rowNum) ->
        {
          BookResponse b = new BookResponse();
          b.setId(rs.getObject("b.id",Integer.class));
          b.setTitle(rs.getString("b.title"));
          b.setAuthor(rs.getString("b.author"));
          b.setIsbn(rs.getString("b.isbn"));
          b.setPrice(rs.getBigDecimal("b.price"));
          b.setStock(rs.getInt("b.stock"));
          b.setStockAvailable(rs.getInt("b.stock_available"));
          b.setDatePublished(rs.getObject("b.date_published", LocalDate.class));
          b.setRentDate(rs.getObject("ub.rent_date", LocalDate.class));
          b.setReturnDate(rs.getObject("ub.return_date", LocalDate.class));
          b.setReturned("O".equals(rs.getString("ub.isReturned")));
          b.setRentId(rs.getInt("ub.id"));
          b.setComment(rs.getString("ub.comment"));
          return b;
        });
  }

  public List<CommentResponse> loadComment(Integer userId, String username)
  {
    final String sql =
        ""
           +" SELECT  ub.comment                                     "
           +"        ,b.title                                        "
           +"        ,b.id                                           "
           +"        ,ub.id                                          "
           +" FROM book b                                            "
           +" JOIN user_books ub on b.id = ub.book_id                "
           +" WHERE ub.user_id = CASE(  SELECT role                  "
           +"                           FROM user                    "
           +"                           WHERE email = :username)     "
           +"                    WHEN 'USER'                         "
           +"                    THEN ( SELECT id                    "
           +"                           FROM user                    "
           +"                           WHERE email = :username)     "
           +"                    ELSE :id END                        "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("username", username)
        .addValue("id", userId);


    return template.query(sql, sp, (rs, rowNum) ->
    {
      CommentResponse c = new CommentResponse();
      c.setBookId(rs.getObject("b.id",Integer.class));
      c.setBookTitle(rs.getString("b.title"));
      c.setComment(rs.getString("ub.comment"));
      c.setRentId(rs.getObject("ub.id",Integer.class));
      return c;
    });

  }

  public List<UserResponse> searchUser(UserSearchCriteria criteria)
  {
     StringBuilder sql = new StringBuilder(
        ""
            + "SELECT                "
            + "       id,            "
            + "       first_name,    "
            + "       second_name,   "
            + "       third_name,    "
            + "       email,         "
            + "       role,          "
            + "       age,           "
            + "       main_address   "
            + "FROM user             "
            + "WHERE 1=1             "
     );

    if(null != criteria.getFirstName()){
      sql.append(" AND first_name LIKE :firstName ");
    }
    if(null != criteria.getSecondName()){
      sql.append(" AND second_name LIKE :secondName ");
    }
    if(null != criteria.getThirdName()){
      sql.append(" AND third_name LIKE :thirdName ");
    }
    if(null != criteria.getEmail()){
      sql.append(" AND email LIKE :email ");
    }
    if(null != criteria.getMainAddress()){
      sql.append(" AND main_address LIKE :address ");
    }

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("firstName", criteria.getFirstName())
        .addValue("secondName", criteria.getSecondName())
        .addValue("thirdName", criteria.getThirdName())
        .addValue("email", criteria.getEmail())
        .addValue("address", criteria.getMainAddress());

    try{
      return template.query(sql.toString(), sp, (rs, rowNum) ->
      {
        UserResponse r = new UserResponse();
        r.setUserId(rs.getInt("id"));
        r.setFirstName(rs.getString("first_name"));
        r.setSecondName(rs.getString("second_name"));
        r.setThirdName(rs.getString("third_name"));
        r.setEmail(rs.getString("email"));
        r.setRole(User.RoleName.valueOf(rs.getString("role")));
        r.setAge(rs.getInt("age"));
        r.setMainAddress(rs.getString("main_address"));
        return r;
      });
    }catch (EmptyResultDataAccessException ex){
      return new ArrayList<>();
    }

  }

  public List<BookResponse> searchBook(BookSearchCriteria criteria){
    StringBuilder sql = new StringBuilder(
        ""
           +"SELECT id AS bookId,   "
           +"       title,          "
           +"       author,         "
           +"       isbn,           "
           +"       price,          "
           +"       date_published, "
           +"       stock,          "
           +"       stock_available "
           +"FROM book              "
           +"WHERE 1=1              "
    );

    if(null != criteria.getTitle()){
      sql.append(" AND title LIKE :title ");
    }
    if(null != criteria.getAuthor()){
      sql.append(" AND author LIKE :author ");
    }
    if(null != criteria.getIsbn()){
      sql.append(" AND isbn LIKE :isbn     ");
    }
    if(null != criteria.getPriceFrom()){
      sql.append(" AND price >= :priceFrom ");
    }
    if(null != criteria.getPriceTo()){
      sql.append(" AND price <= :priceTo    ");
    }

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("title", criteria.getTitle())
        .addValue("author", criteria.getAuthor())
        .addValue("isbn", criteria.getIsbn())
        .addValue("priceFrom", criteria.getPriceFrom())
        .addValue("priceTo", criteria.getPriceTo());

    try{
      return template.query(sql.toString(), sp,  (rs, rowNum) ->
      {
        BookResponse r = new BookResponse();
        r.setId(rs.getInt("bookId"));
        r.setTitle(rs.getString("title"));
        r.setAuthor(rs.getString("author"));
        r.setIsbn(rs.getString("isbn"));
        r.setPrice(rs.getBigDecimal("price"));
        r.setDatePublished(rs.getObject("date_published",LocalDate.class));
        r.setStock(rs.getInt("stock"));
        r.setStockAvailable(rs.getInt("stock_available"));
        return r;
      });
    }catch (EmptyResultDataAccessException ex){
     return new ArrayList<>();
    }

  }

  public Integer insertFile(MultipartFile file, Integer bookId)
  {
    final String sql =
        ""
            +" INSERT INTO file "
            +"  (               "
            +"    absolute_path "
            +"   ,file_name     "
            +"   ,bookId        "
            +"   ,id            "
            +"  )               "
            +" VALUES           "
            +"  (               "
            +"   :path          "
            +"  ,:name          "
            +"  ,:bookId        "
            +"  ,:bookId        "
            +"  )               "
        ;

    MapSqlParameterSource sp = new MapSqlParameterSource()
        .addValue("path"  ,"C:\\Users\\Hristiyan\\Desktop\\master-project-FE\\src\\files"+file.getOriginalFilename())
        .addValue("name"  ,file.getOriginalFilename())
        .addValue("bookId",bookId);

    KeyHolder keyHolder = new GeneratedKeyHolder();


    template.update(sql,sp,keyHolder);

    return keyHolder.getKey().intValue();

  }

  public String loadFileName(String fileId)
  {
    final String sql =
        ""
            +"SELECT file_name        "
            +"FROM  file              "
            +"WHERE id = :bookId      "
        ;
    try{
      return template.queryForObject(sql,new MapSqlParameterSource("bookId",fileId),String.class);
    }catch (EmptyResultDataAccessException ex){
      return null;
    }
  }

}
