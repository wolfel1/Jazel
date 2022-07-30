package jazel.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WindowProps {
  private String title;
  private int width;
  private int height;

  @Override
  public String toString() {
    return title + "(" + width + ", " + height + ")";
  }
}
