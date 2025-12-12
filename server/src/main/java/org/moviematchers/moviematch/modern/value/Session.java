package org.moviematchers.moviematch.modern.value;

import java.util.Objects;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Session {
  private final Identifier identifier;
  private final Identifier sessionIdentifier;

  private Session(final Identifier identifier, final Identifier sessionIdentifier) {
    this.identifier = identifier;
    this.sessionIdentifier = sessionIdentifier;
  }

  public Identifier identifier() {
    return this.identifier;
  }

  public Identifier invitationIdentifier() {
    return this.sessionIdentifier;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.identifier);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object)
      return true;
    if (!(object instanceof Session session))
      return false;
    return Objects.equals(this.identifier, session.identifier);
  }
}
