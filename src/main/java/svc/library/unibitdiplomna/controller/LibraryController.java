package svc.library.unibitdiplomna.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import svc.library.unibitdiplomna.dto.*;
import svc.library.unibitdiplomna.security.TokenService;
import svc.library.unibitdiplomna.service.LibraryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React FE Url
public class LibraryController
{
  private final LibraryService libraryService;
  private final TokenService   tokenService;

  public LibraryController(LibraryService libraryService, TokenService tokenService)
  {
    this.libraryService = libraryService;
    this.tokenService = tokenService;
  }

  @PostMapping("/svc/library/user")
  @ResponseStatus(HttpStatus.CREATED)
  public void registration(@RequestBody @Valid UserRequest userRequest)
  {
    libraryService.registration(userRequest);
  }

  @PostMapping("/svc/library/login")
  @ResponseStatus(HttpStatus.CREATED)
  public String login(Authentication authentication)
  {
    return tokenService.generateToken(authentication);
  }

  @GetMapping("/svc/library/user")
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @ResponseStatus(HttpStatus.OK)
  public User getLoggedUser(@CurrentSecurityContext(expression = "authentication") Authentication authentication)
  {
    return libraryService.getLoggedUser(authentication.getName());
  }

  @PutMapping("/svc/library/user")
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateLoggedUser(@CurrentSecurityContext(expression = "authentication") Authentication authentication
      , @RequestBody @Valid UserRequest userRequest)
  {
    libraryService.updateLoggedUser(userRequest, authentication.getName());
  }

  @PostMapping("/svc/library/book")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  public void addBook(@RequestBody @Valid BookRequest book)
  {
    libraryService.addBook(book);
  }

  @PatchMapping("/svc/library/book/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public void addQuantity(@PathVariable Integer id
      , @RequestParam Integer stock)
  {
    libraryService.updateQuantity(id, stock);
  }

  @PostMapping("/svc/library/book-rent")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public void rentBook(@RequestBody BookRentRequest book)
  {
    libraryService.rentBook(book);
  }

  @DeleteMapping("/svc/library/book-rent")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeRent(@RequestParam Integer rentId)
  {
    libraryService.removeRent(rentId);
  }

  @PatchMapping("/svc/library/book-return")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void returnBook(@RequestParam Integer rentId)
  {
    libraryService.returnBook(rentId);
  }

  @PostMapping("/svc/library/book-comment")
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @ResponseStatus(HttpStatus.OK)
  public void writeComment(@RequestBody @Valid CommentRequest commentRequest
      , @CurrentSecurityContext(expression = "authentication") Authentication authentication)
  {
    libraryService.writeComment(commentRequest, authentication.getName());
  }

  @PostMapping("/svc/library/book/search")
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @ResponseStatus(HttpStatus.OK)
  public List<BookResponse> searchBook(@RequestBody @Valid BookSearchCriteria criteria)
  {
   return libraryService.searchBook(criteria);
  }

  @PostMapping("/svc/library/user/search")
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponse> searchUser(@RequestBody @Valid UserSearchCriteria criteria)
  {
    return libraryService.searchUser(criteria);
  }

  // User functions
  @GetMapping("/svc/library/book")
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @ResponseStatus(HttpStatus.OK)
  public List<BookResponse> loadBooks(@CurrentSecurityContext(expression = "authentication") Authentication authentication,
                                      @RequestParam(required = false) Integer userId) // if passed and authenticated user is ADMIN load this userId books info
  {
    return libraryService.loadBooks(userId, authentication.getName());
  }

  @GetMapping("/svc/library/comment")
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @ResponseStatus(HttpStatus.OK)
  public List<CommentResponse> loadComment(@CurrentSecurityContext(expression = "authentication") Authentication authentication,
                                           @RequestParam(required = false) Integer userId)
  {
    return libraryService.loadComment(userId, authentication.getName());
  }

}