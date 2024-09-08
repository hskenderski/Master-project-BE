package svc.library.unibitdiplomna.dto;

public class UserResponse
{
  private String firstName;
  private String secondName;
  private String thirdName;
  private String        email;
  private User.RoleName role;
  private Integer       age;
  private String mainAddress;
  private Integer userId;

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

  public User.RoleName getRole()
  {
    return role;
  }

  public void setRole(User.RoleName role)
  {
    this.role = role;
  }

  public Integer getAge()
  {
    return age;
  }

  public void setAge(Integer age)
  {
    this.age = age;
  }

  public String getMainAddress()
  {
    return mainAddress;
  }

  public void setMainAddress(String mainAddress)
  {
    this.mainAddress = mainAddress;
  }

  public Integer getUserId()
  {
    return userId;
  }

  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }
}
