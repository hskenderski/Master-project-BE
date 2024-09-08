package svc.library.unibitdiplomna.dto;

import javax.validation.constraints.NotNull;

public class UserRequest
{
  private String firstName;
  private String secondName;
  private String thirdName;
  private String email;
  private String password;
  private String passwordConfirmation;
  private User.RoleName roleName;
  @NotNull
  private String mainAddress;
  private Integer age;

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

  public User.RoleName getRoleName()
  {
    return roleName;
  }

  public void setRoleName(User.RoleName roleName)
  {
    this.roleName = roleName;
  }

  public String getMainAddress()
  {
    return mainAddress;
  }

  public void setMainAddress(String mainAddress)
  {
    this.mainAddress = mainAddress;
  }

  public Integer getAge()
  {
    return age;
  }

  public void setAge(Integer age)
  {
    this.age = age;
  }

  public String getPasswordConfirmation()
  {
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(String passwordConfirmation)
  {
    this.passwordConfirmation = passwordConfirmation;
  }
}
