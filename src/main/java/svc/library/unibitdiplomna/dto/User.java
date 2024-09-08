package svc.library.unibitdiplomna.dto;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;

public class User  implements GrantedAuthority
{
  private String   firstName;
  private String   secondName;
  private String   thirdName;
  private String   email;
  private String   password;
  private RoleName role;
  private Integer  age;
  private String mainAddress;


  public User(String firstName, String secondName, String thirdName, String email, String password, RoleName role, Integer age, String mainAddress)
  {
    this.firstName = firstName;
    this.secondName = secondName;
    this.thirdName = thirdName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.age = age;
    this.mainAddress = mainAddress;
  }

  public User()
  {
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getSecondName()
  {
    return secondName;
  }

  public void setSecondName(String secondName)
  {
    this.secondName = secondName;
  }

  public String getThirdName()
  {
    return thirdName;
  }

  public void setThirdName(String thirdName)
  {
    this.thirdName = thirdName;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public Integer getAge()
  {
    return age;
  }

  public void setAge(Integer age)
  {
    this.age = age;
  }

  public RoleName getRole()
  {
    return role;
  }

  public void setRole(RoleName role)
  {
    this.role = role;
  }

  public String getMainAddress()
  {
    return mainAddress;
  }

  public void setMainAddress(String mainAddress)
  {
    this.mainAddress = mainAddress;
  }

  public enum RoleName
  {
    USER,
    ADMIN
  }

  @Transient
  @Override
  public String getAuthority()
  {
    return "ROLE_" + role.toString();
  }
}
