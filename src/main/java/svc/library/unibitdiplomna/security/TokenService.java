package svc.library.unibitdiplomna.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class TokenService {

  @Autowired
  private final JwtEncoder encoder;

  @Autowired
  public TokenService(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  public String generateToken(Authentication authentication) {

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    ArrayList<String> authsList = new ArrayList<>(authorities.size());

    for (GrantedAuthority authority : authorities) {
      authsList.add(authority.getAuthority());
    }

      Instant now = Instant.now();
      JwtClaimsSet claims = JwtClaimsSet.builder()
          .issuer("self")
          .issuedAt(now)
          .expiresAt(now.plus(1, ChronoUnit.HOURS))
          .subject(((User) authentication.getPrincipal()).getUsername())
          .claim("roles", authsList)
          .build();

      return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}