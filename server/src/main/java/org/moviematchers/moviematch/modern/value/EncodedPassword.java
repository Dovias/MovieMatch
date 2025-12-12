package org.moviematchers.moviematch.modern.value;

import java.util.Objects;
import java.util.regex.Pattern;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.base.Preconditions;

@NullMarked
public final class EncodedPassword {
  private final static Pattern PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
  private final static PasswordEncoder ENCODER = new BCryptPasswordEncoder(BCryptVersion.$2A, 10);

  private final PasswordEncoder encoder;
  private final String hash;

  private EncodedPassword(final PasswordEncoder encoder, final String hash) {
    this.encoder = encoder;
    this.hash = hash;
  }

  public boolean matches(final Password password) {
    return this.encoder.matches(password.toString(), this.hash);
  }

  public EncodedPassword change(final Password password) {
    Preconditions.checkNotNull(password, "password cannot be null");
    Preconditions.checkArgument(!this.matches(password), "changed password cannot be the same");
    return EncodedPassword.of(password);
  }

  @Override
  public String toString() {
    return this.hash;
  }

  @Override
  public boolean equals(final @Nullable Object object) {
    if (this == object)
      return true;
    if (!(object instanceof EncodedPassword password))
      return false;
    return Objects.equals(this.hash, password.hash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.hash);
  }

  public static EncodedPassword of(final Password password) {
    Preconditions.checkNotNull(password, "password cannot be null");
    return new EncodedPassword(EncodedPassword.ENCODER, EncodedPassword.ENCODER.encode(password.toString()));
  }

  public static EncodedPassword from(final String hash) {
    Preconditions.checkNotNull(hash, "hash cannot be null");
    Preconditions.checkArgument(EncodedPassword.PATTERN.matcher(hash).matches(),
        "hash does not comply with the requirements");
    return new EncodedPassword(EncodedPassword.ENCODER, hash);
  }
}