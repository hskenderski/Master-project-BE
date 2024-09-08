package svc.library.unibitdiplomna.validators;

import org.springframework.stereotype.Component;
import svc.library.unibitdiplomna.dao.LibraryDao;
import svc.library.unibitdiplomna.dto.User;
import svc.library.unibitdiplomna.dto.UserRequest;
import svc.library.unibitdiplomna.exceptions.InvalidException;

@Component
public class LibraryValidator
{
  private final LibraryDao libraryDao;

  public LibraryValidator(LibraryDao libraryDao){
    this.libraryDao = libraryDao;
  }

  public void validateUserRequest(UserRequest userRequest, String email)
  {

    User user = libraryDao.getUserByEmail(email).orElseGet(User::new);

    if(!userRequest.getPassword().equals(userRequest.getPasswordConfirmation())){
      throw new InvalidException("Password don't match!");
    }

    if(libraryDao.isEmailExists(userRequest.getEmail())){
      if(!userRequest.getEmail().equals(user.getEmail()))
        throw new InvalidException("The email is already registered!");
    }
  }
}
