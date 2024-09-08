package svc.library.unibitdiplomna.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import svc.library.unibitdiplomna.dao.LibraryDao;
import svc.library.unibitdiplomna.dto.User;
import svc.library.unibitdiplomna.exceptions.InvalidException;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{

  private final LibraryDao libraryDao;

  @Autowired
  public UserDetailServiceImpl(LibraryDao libraryDao)
  {
    this.libraryDao = libraryDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    User user = libraryDao.getUserByEmail(username).orElseThrow(() -> new InvalidException("No such user!"));

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .roles(user.getRole().name())
        .build();
  }
}