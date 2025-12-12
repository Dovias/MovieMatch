package org.moviematchers.moviematch.modern.value;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jspecify.annotations.NullMarked;

import com.google.common.base.Preconditions;

@NullMarked
public final class Username {
  private final static Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9_-]{3,15}$", Pattern.CASE_INSENSITIVE);
  private final String username;

  private Username(final String username) {
    this.username = username;
  }

  public Username change(final String username) {
    Preconditions.checkNotNull(username, "username cannot be null");
    Preconditions.checkArgument(!this.username.equalsIgnoreCase(username), "username cannot be the same");

    return Username.of(username);
  }

  @Override
  public String toString() {
    return this.username;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.username);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object)
      return true;
    if (!(object instanceof Username username))
      return false;
    return Objects.equals(this.username, username.username);
  }

  public static Username of(final String username) {
    Preconditions.checkNotNull(username, "username cannot be null");
    final Matcher matcher = Username.USERNAME_PATTERN.matcher(username);
    Preconditions.checkArgument(matcher.matches(), "username does not comply with the requirements");
    return new Username(username);
  }

}
