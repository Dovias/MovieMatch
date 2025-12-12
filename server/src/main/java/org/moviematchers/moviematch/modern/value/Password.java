package org.moviematchers.moviematch.modern.value;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import com.google.common.base.Preconditions;

@NullMarked
public final class Password {
  private final static Pattern PASSWORD_PATTERN = Pattern
      .compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
  private final String password;

  private Password(final String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return this.password;
  }

  @Override
  public boolean equals(final @Nullable Object object) {
    if (this == object)
      return true;
    if (!(object instanceof Password password))
      return false;
    return Objects.equals(this.password, password.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.password);
  }

  public static Password of(final String password) {
    Preconditions.checkNotNull(password, "password cannot be null");
    Matcher matcher = Password.PASSWORD_PATTERN.matcher(password);
    Preconditions.checkArgument(matcher.matches(), "password does not comply with requirements");

    return new Password(password);
  }
}