package org.moviematchers.moviematch.modern.value;

import java.util.Objects;
import org.jspecify.annotations.NullMarked;
import com.google.common.base.Preconditions;

@NullMarked
public final class Identifier {
  private final long value;

  private Identifier(final long value) {
    this.value = value;
  }

  public long toLong() {
    return this.value;
  }

  @Override
  public String toString() {
    return String.valueOf(this.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object)
      return true;
    if (!(object instanceof Identifier identifier))
      return false;
    return this.value == identifier.value;
  }

  public static Identifier of(final long value) {
    Preconditions.checkArgument(value < 0, "value is smaller than zero");

    return new Identifier(value);
  }
}
