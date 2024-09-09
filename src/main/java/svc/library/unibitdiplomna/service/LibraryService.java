package svc.library.unibitdiplomna.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import svc.library.unibitdiplomna.dao.LibraryDao;
import svc.library.unibitdiplomna.dto.*;
import svc.library.unibitdiplomna.exceptions.InvalidException;
import svc.library.unibitdiplomna.validators.LibraryValidator;
import org.springframework.core.io.Resource;

import java.util.List;

@Service
@Transactional
public class LibraryService
{
  private final LibraryDao libraryDao;
  private final   LibraryValidator      libraryValidator;
  private final BCryptPasswordEncoder passwordEncoder;
  private final FileStorageService fileStorageService;


  public LibraryService(LibraryDao libraryDao, LibraryValidator libraryValidator, BCryptPasswordEncoder passwordEncoder
  ,FileStorageService fileStorageService){
    this.libraryDao = libraryDao;
    this.libraryValidator = libraryValidator;
    this.passwordEncoder = passwordEncoder;
    this.fileStorageService = fileStorageService;
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

  public Integer addBook(BookRequest book)
  {
   return libraryDao.addBook(book);
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

  public void uploadFile(MultipartFile file, Integer itemId, final String email)
  {
//    shopValidator.validateItemId(itemId,email);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    Integer id = libraryDao.insertFile(file,itemId);
    try {
      fileStorageService.save(file, id);
    }
    catch (Exception ex) {
      throw new InvalidException(String.format("Could not upload the file: %s!",file.getOriginalFilename()));
    }
    System.gc();
  }

  public ResponseEntity<Resource> loadFile(final String fileName)
  {
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+libraryDao.loadFileName(fileName)).body(fileStorageService.load(fileName));
  }
}
