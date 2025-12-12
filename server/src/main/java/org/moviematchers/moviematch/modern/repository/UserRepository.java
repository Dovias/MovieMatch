package org.moviematchers.moviematch.modern.repository;

import java.util.stream.Stream;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.moviematchers.moviematch.modern.value.EncodedPassword;
import org.moviematchers.moviematch.modern.value.Identifier;
import org.moviematchers.moviematch.modern.value.UserDetails;
import org.moviematchers.moviematch.modern.value.Username;

@NullMarked
public interface UserRepository {
  Identifier insertUser(final Username username, final EncodedPassword password);

  Stream<UserDetails> getAllUserDetails();

  @Nullable
  Username findUsernameByUserIdentifier(final Identifier identifier);

  @Nullable
  Identifier findIdentifierByUsername(final Username username);

  EncodedPassword findPasswordByUserIdentifier(final Identifier indentifier);

  void updateUsernameByUserIdentifier(final Identifier identifier, final Username username);

  void updatePasswordByUserIdentifier(final Identifier identifier, final EncodedPassword password);
}
