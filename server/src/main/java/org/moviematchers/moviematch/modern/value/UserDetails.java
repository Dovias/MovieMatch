package org.moviematchers.moviematch.modern.value;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import com.google.common.base.Preconditions;

@NullMarked
public final class UserDetails {
  private final Identifier identifier;
  private final Username username;

  private UserDetails(final Identifier identifier, final Username username) {
    this.identifier = identifier;
    this.username = username;
  }

  public Identifier identifier() {
    return this.identifier;
  }

  public Username username() {
    return this.username;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.identifier, this.username);
  }

  @Override
  public boolean equals(final @Nullable Object object) {
    if (this == object)
      return true;
    if (!(object instanceof UserDetails user))
      return false;
    return Objects.equals(this.identifier, user.identifier) && Objects.equals(this.username, user.username);
  }

  public static UserDetails of(final Identifier identifier, final Username username) {
    Preconditions.checkNotNull(identifier, "indentifier cannot be null");
    Preconditions.checkNotNull(username, "username cannot be null");

    return new UserDetails(identifier, username);
  }

}
