package svc.library.unibitdiplomna.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svc.library.unibitdiplomna.dao.LibraryDao;
import svc.library.unibitdiplomna.dto.*;
import svc.library.unibitdiplomna.exceptions.InvalidException;
import svc.library.unibitdiplomna.validators.LibraryValidator;

import java.util.List;

@Service
@Transactional
public class LibraryService
{
  private final LibraryDao libraryDao;
  private final   LibraryValidator      libraryValidator;
  private final BCryptPasswordEncoder passwordEncoder;


  public LibraryService(LibraryDao libraryDao, LibraryValidator libraryValidator, BCryptPasswordEncoder passwordEncoder){
    this.libraryDao = libraryDao;
    this.libraryValidator = libraryValidator;
    this.passwordEncoder = passwordEncoder;
  }

  public void registration(UserRequest userRequest)
  {
    if(null == userRequest.getPassword() || null == userRequest.getPasswordConfirmation() || null == userRequest.getEmail()){
      throw new InvalidException("Password and PasswordConfirmation and Email are mandatory!");
    }
    libraryValidator.validateUserRequest(userRequest,null);
    userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    libraryDao.registration(userRequest);
  }

  public User getLoggedUser(final String email)
  {
    return libraryDao.getUserByEmail(email).orElseThrow(() -> new InvalidException("No such user!"));
  }

  public void updateLoggedUser(UserRequest userRequest, String email)
  {
    libraryDao.updateUser(userRequest, email);
  }

  public void addBook(BookRequest book)
  {
    libraryDao.addBook(book);
  }

  public void updateQuantity(Integer id, Integer stock)
  {
    libraryDao.updateQuantity(id, stock);
  }

  public void rentBook(BookRentRequest book)
  {
    libraryDao.rentBook(book);
  }

  public void removeRent(Integer rentId)
  {
    libraryDao.removeRent(rentId);
  }

  public void returnBook(Integer rentId)
  {
    libraryDao.returnBook(rentId);
  }

  public void writeComment(CommentRequest commentRequest, String username)
  {
    libraryDao.writeComment(commentRequest, username);
  }

  public List<BookResponse> loadBooks(Integer userId, String username)
  {
    return libraryDao.loadBooks(userId, username);
  }

  public List<CommentResponse> loadComment(Integer userId, String username)
  {
    return libraryDao.loadComment(userId, username);
  }

  public List<UserResponse> searchUser(UserSearchCriteria criteria)
  {
    return libraryDao.searchUser(criteria);
  }

  public List<BookResponse> searchBook(BookSearchCriteria criteria){
    return libraryDao.searchBook(criteria);
  }
}
