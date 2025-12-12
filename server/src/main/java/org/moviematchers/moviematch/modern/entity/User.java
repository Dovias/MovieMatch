package org.moviematchers.moviematch.modern.entity;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.moviematchers.moviematch.modern.value.EncodedPassword;
import org.moviematchers.moviematch.modern.value.Identifier;
import org.moviematchers.moviematch.modern.value.Username;

import com.google.common.base.Preconditions;

@NullMarked
public final class User {
  private final Identifier identifier;
  private final Username username;
  private final EncodedPassword password;

  private User(final Identifier identifier, final Username username, final EncodedPassword password) {
    this.identifier = identifier;
    this.username = username;
    this.password = password;
  }

  public Identifier identifier() {
    return this.identifier;
  }

  public Username username() {
    return this.username;
  }

  public EncodedPassword password() {
    return this.password;
  }

  public User change(final Username username) {
    Preconditions.checkNotNull(username, "username cannot be null");
    Preconditions.checkArgument(!this.username.equals(username), "username cannot be the same");

    return new User(this.identifier, username, this.password);
  }

  public User change(final EncodedPassword password) {
    Preconditions.checkNotNull(username, "encoded password cannot be null");
    Preconditions.checkArgument(!this.password.equals(password), "password cannot be the same");

    return new User(this.identifier, this.username, password);
  }

  @Override
  public String toString() {
    return "User [identifier=" + identifier + ", username=" + username + ", password=" + password + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier);
  }

  @Override
  public boolean equals(final @Nullable Object object) {
    if (this == object)
      return true;
    if (!(object instanceof User user))
      return false;
    return Objects.equals(this.identifier, user.identifier);
  }

  public static User of(final Identifier identifier, final Username username, final EncodedPassword password) {
    Preconditions.checkNotNull(identifier, "indentifier cannot be null");
    Preconditions.checkNotNull(username, "username cannot be null");
    Preconditions.checkNotNull(password, "encoded password cannot be null");

    return new User(identifier, username, password);
  }

}
