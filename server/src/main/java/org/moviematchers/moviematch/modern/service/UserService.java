package org.moviematchers.moviematch.modern.service;

import java.util.stream.Stream;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.moviematchers.moviematch.modern.repository.UserRepository;
import org.moviematchers.moviematch.modern.value.EncodedPassword;
import org.moviematchers.moviematch.modern.value.Identifier;
import org.moviematchers.moviematch.modern.value.Password;
import org.moviematchers.moviematch.modern.value.UserDetails;
import org.moviematchers.moviematch.modern.value.Username;

import com.google.common.base.Preconditions;

@NullMarked
public final class UserService {
  private final UserRepository repository;

  private UserService(final UserRepository repository) {
    this.repository = repository;
  }

  public Identifier createUser(final Username username, final EncodedPassword password) {
    return this.repository.insertUser(username, password);
  }

  public Stream<UserDetails> getAllDetails() {
    return this.repository.getAllUserDetails();
  }

  public @Nullable Identifier getIdentifier(final Username username) {
    Preconditions.checkNotNull(username, "username cannot be null");
    return this.repository.findIdentifierByUsername(username);
  }

  public @Nullable Username getUsername(final Identifier userIdentifier) {
    Preconditions.checkNotNull(userIdentifier, "user identifier cannot be null");
    return this.repository.findUsernameByUserIdentifier(userIdentifier);
  }

  public void changeUsername(final Identifier userIdentifier, final Username newUsername) {
    Preconditions.checkNotNull(userIdentifier, "user identifier cannot be null");
    Preconditions.checkNotNull(newUsername, "new username cannot be null");
    this.repository.updateUsernameByUserIdentifier(userIdentifier, newUsername);
  }

  public EncodedPassword changePassword(final Identifier userIdentifier, final Password newPassword) {
    Preconditions.checkNotNull(userIdentifier, "user identifier cannot be null");
    Preconditions.checkNotNull(newPassword, "new password cannot be null");

    final EncodedPassword password = this.repository.findPasswordByUserIdentifier(userIdentifier);
    Preconditions.checkNotNull(password, "user password not found");

    final EncodedPassword changedPassword = password.change(newPassword);
    this.repository.updatePasswordByUserIdentifier(userIdentifier, changedPassword);

    return changedPassword;
  }

}
