package org.moviematchers.moviematch.modern.repository;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.jspecify.annotations.Nullable;
import org.moviematchers.moviematch.modern.entity.User;
import org.moviematchers.moviematch.modern.value.EncodedPassword;
import org.moviematchers.moviematch.modern.value.Identifier;
import org.moviematchers.moviematch.modern.value.UserDetails;
import org.moviematchers.moviematch.modern.value.Username;

import com.google.common.base.Preconditions;

public class InMemoryUserRepository implements UserRepository {
  private final Map<Identifier, User> users;
  private long nextIdentifierValue;

  private InMemoryUserRepository(final Map<Identifier, User> users) {
    this.users = users;
  }

  @Override
  public Identifier insertUser(final Username username, final EncodedPassword password) {
    Preconditions.checkNotNull(username, "username cannot be null");
    Preconditions.checkNotNull(password, "encoded password cannot be null");

    final Identifier identifier = Identifier.of(nextIdentifierValue++);
    final User user = User.of(identifier, username, password);

    this.users.put(identifier, user);
    return identifier;
  }

  @Override
  public Stream<UserDetails> getAllUserDetails() {
    return this.users.values().stream().map((user) -> UserDetails.of(user.identifier(), user.username()));
  }

  @Override
  public @Nullable Identifier findIdentifierByUsername(final Username username) {
    Preconditions.checkNotNull(username, "username cannot be null");

    return this.users.values().stream().filter((user) -> user.username().equals(username))
        .map((user) -> user.identifier()).findAny().orElse(null);
  }

  @Override
  public @Nullable Username findUsernameByUserIdentifier(final Identifier identifier) {
    return this.users.values().stream().filter((user) -> user.identifier().equals(identifier))
        .map((user) -> user.username()).findAny().orElse(null);
  }

  @Override
  public EncodedPassword findPasswordByUserIdentifier(final Identifier identifier) {
    Preconditions.checkNotNull(identifier, "identifier cannot be null");

    return this.users.values().stream().filter((user) -> user.identifier().equals(identifier))
        .map((user) -> user.password()).findAny()
        .orElseThrow(() -> new NoSuchElementException("identifier cannot be associated"));
  }

  @Override
  public void updateUsernameByUserIdentifier(final Identifier identifier, final Username username) {
    Preconditions.checkNotNull(identifier, "identifier cannot be null");
    Preconditions.checkNotNull(username, "username cannot be null");
    final User user = this.users.get(identifier);
    if (user == null) {
      throw new NoSuchElementException("identifier cannot be associated");
    }

    final User changedUser = user.change(username);
    this.users.put(identifier, changedUser);
  }

  @Override
  public void updatePasswordByUserIdentifier(final Identifier identifier, final EncodedPassword password) {
    Preconditions.checkNotNull(identifier, "identifier cannot be null");
    Preconditions.checkNotNull(password, "encoded password cannot be null");

    final User user = this.users.get(identifier);
    if (user == null) {
      throw new NoSuchElementException("identifier cannot be associated");
    }

    final User changedUser = user.change(password);
    this.users.put(identifier, changedUser);
  }
}
