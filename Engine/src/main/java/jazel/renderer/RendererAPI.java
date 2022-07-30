package jazel.renderer;

import jazel.core.Log;
import jazel.renderer.enumeration.API;
import org.joml.Vector4f;

public interface RendererAPI {

  static API api = API.OPENGL;

  void init();

  void setViewport(int x, int y, int width, int height);

  void setClearColor(Vector4f color);

  void clear();

  void drawIndexed();

  static API getAPI() {
    return api;
  }

  static RendererAPI create() {
    switch (api) {
      case NONE:
        Log.getCoreLogger().error("RendererAPI NONE is not supported!");
      case OPENGL:
        return null;
    }
    Log.getCoreLogger().error("Unknown RendererAPI!");

    return null;
  }
}
