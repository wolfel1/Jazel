package jazel.engine.core.window;

import jazel.platform.window.WindowImpl;

public abstract class Window {

    public abstract void onUpdate();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void setVSync(boolean enabled);

    public abstract boolean isVSync();

    public abstract long getNativeWindow();

    public abstract void shutdown();

    public static Window create(WindowProps props) {
        return new WindowImpl(props);
    }
}
