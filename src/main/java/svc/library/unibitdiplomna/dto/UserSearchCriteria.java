package svc.library.unibitdiplomna.dto;

public class UserSearchCriteria
{
  private String firstName;
  private String secondName;
  private String thirdName;
  private String email;
  private String mainAddress;

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

  public String getMainAddress()
  {
    return mainAddress;
  }

  public void setMainAddress(String mainAddress)
  {
    this.mainAddress = mainAddress;
  }

}
