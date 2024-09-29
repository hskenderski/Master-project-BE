package svc.library.unibitdiplomna;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import svc.library.unibitdiplomna.dto.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UnibitDiplomnaApplicationTests
{

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext)
  {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    this.objectMapper = Jackson2ObjectMapperBuilder.json().build();
  }

  @Test
  public void testRegistration() throws Exception
  {
    UserRequest userRequest = new UserRequest();
    userRequest.setFirstName("Test");
    userRequest.setSecondName("Last_name");
    userRequest.setEmail("test@abv.bg");
    userRequest.setPassword("123");
    userRequest.setPasswordConfirmation("123");
    userRequest.setRoleName(User.RoleName.ADMIN);
    userRequest.setMainAddress("Test Sofia");

    mockMvc.perform(post("/svc/library/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isCreated())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email@abv.bg")
  public void testGetLoggedUser() throws Exception
  {
    mockMvc.perform(get("/svc/library/user")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @WithMockUser(username = "email@abv.bg")
  public void testGetLoggedUser_NoSuchUserFound() throws Exception
  {
    mockMvc.perform(get("/svc/library/user")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(result -> assertEquals("No such user!", Objects.requireNonNull(result.getResolvedException()).getMessage()))
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email@abv.bg")
  public void testUpdateLoggedUser() throws Exception
  {
    UserRequest userRequest = new UserRequest();
    userRequest.setMainAddress("Test Sofia");
    userRequest.setEmail("test@abv.bg");
    userRequest.setPassword("123");
    userRequest.setPasswordConfirmation("123");

    mockMvc.perform(put("/svc/library/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isNoContent())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithUserDetails(value = "email2@abv.bg")
  public void testAddBook() throws Exception
  {
    BookRequest bookRequest = new BookRequest();
    bookRequest.setAuthor("TestAuthor");
    bookRequest.setIsbn("123445rfgdcx");
    bookRequest.setPrice(BigDecimal.TEN);
    bookRequest.setTitle("TestTitle");
    bookRequest.setStock(20);
    bookRequest.setPublishDate(LocalDate.now());
    bookRequest.setStockAvailable(20);

    mockMvc.perform(post("/svc/library/book")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookRequest)))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithUserDetails(value = "email@abv.bg")
  public void testAddQuantity() throws Exception
  {
    mockMvc.perform(patch("/svc/library/book/-123")
            .param("stock", "2")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithUserDetails(value = "email2@abv.bg")
  public void testRentBook() throws Exception
  {
    BookRentRequest bookRentRequest = new BookRentRequest();
    bookRentRequest.setUserId(-988);
    bookRentRequest.setReturnDate(LocalDate.of(2025, 1, 8));
    bookRentRequest.setBookId(-123);

    mockMvc.perform(post("/svc/library/book-rent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookRentRequest)))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithUserDetails(value = "email2@abv.bg")
  public void testRemoveRent() throws Exception
  {
    mockMvc.perform(delete("/svc/library/book-rent")
            .param("rentId", "-56565") //ne trqbva li da mojesh da triesh rent na kniga samo ako knigata e vurnata -> isReturned = 'O'???????ako ne e vurnata da gurmish
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithUserDetails(value = "email@abv.bg")
  public void testReturnBook() throws Exception
  {
    mockMvc.perform(patch("/svc/library/book-return")
            .param("rentId", "-56565")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithUserDetails(value = "email2@abv.bg")
  public void testWriteComment() throws Exception
  {
    CommentRequest commentRequest = new CommentRequest();
    commentRequest.setComment("The book was good!");
    commentRequest.setRentId("-56565");

    mockMvc.perform(post("/svc/library/book-comment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(commentRequest)))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email@abv.bg")
  public void testLoadBooks_Admin() throws Exception
  {
    mockMvc.perform(get("/svc/library/book")
            .param("userId", "-987")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email3@abv.bg")
  public void testLoadBooks_User() throws Exception
  {
    mockMvc.perform(get("/svc/library/book")
            .param("userId", "-987")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email3@abv.bg")
  public void testLoadComments_User() throws Exception
  {
    mockMvc.perform(get("/svc/library/comment") //problem s decoda
            .param("userId", "-987")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email@abv.bg")
  public void testLoadComments_admin() throws Exception
  {
    mockMvc.perform(get("/svc/library/comment") //problem s decoda
            .param("userId", "-987")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email@abv.bg")
  public void testSearchUser() throws Exception
  {
    UserSearchCriteria criteria = new UserSearchCriteria();
    criteria.setEmail("e%");
    criteria.setFirstName("%2%");
    criteria.setSecondName("%2%");
    criteria.setThirdName("%2%");
    criteria.setMainAddress("%Livadi%");

    mockMvc.perform(post("/svc/library/user/search")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(criteria)))
        .andExpect(status().isOk())
        .andDo(print())
    ;
  }

  @Test
  @Sql(scripts = "/user.sql")
  @WithMockUser(username = "email@abv.bg")
  public void searchBook() throws Exception
  {
      BookSearchCriteria criteria = new BookSearchCriteria();
      criteria.setTitle("Test%");
      criteria.setAuthor("Test%");
      criteria.setIsbn("-248%");
      criteria.setPriceFrom(new BigDecimal("20"));
      criteria.setPriceTo(new BigDecimal("20"));

      mockMvc.perform(post("/svc/library/book/search")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(criteria)))
          .andExpect(status().isOk())
          .andDo(print());
  }

}
