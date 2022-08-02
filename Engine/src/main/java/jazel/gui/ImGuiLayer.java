package jazel.gui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import jazel.core.Application;
import jazel.core.Layer;
import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import org.lwjgl.glfw.GLFW;

public class ImGuiLayer extends Layer {

    private final ImGuiImplGl3 imGuiImplGl3 = new ImGuiImplGl3();
    private final ImGuiImplGlfw imGuiImplGlfw = new ImGuiImplGlfw();

    public ImGuiLayer() {
        super("ImGuiLayer");
    }

    @Override
    public void onAttach() {

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.setConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.setConfigFlags(ImGuiConfigFlags.ViewportsEnable);

        ImGui.styleColorsLight();

        ImGuiStyle style = ImGui.getStyle();
        style.setWindowRounding(0.0f);
        style.setColor(ImGuiCol.WindowBg, 0, 0, 0, 1);

        var app = Application.getInstance();
        var window = app.getWindow().getNativeWindow();

        imGuiImplGlfw.init(window, true);
        imGuiImplGl3.init("#version 410");
    }

    @Override
    public void onDetach() {
        imGuiImplGlfw.dispose();
        imGuiImplGl3.dispose();
        ImGui.destroyContext();
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onEvent(Event event) {
        var io = ImGui.getIO();

        event.setHandled(event.isInCategory(EventCategory.MOUSE) && io.getWantCaptureMouse());
        event.setHandled(event.isInCategory(EventCategory.KEYBOARD) && io.getWantCaptureKeyboard());
    }

    public void begin() {
        imGuiImplGlfw.newFrame();
        ImGui.newFrame();
    }

    public void end() {
        var io = ImGui.getIO();
        var app = Application.getInstance();
        io.setDisplaySize(app.getWindow().getWidth(), app.getWindow().getHeight());

        ImGui.render();
        imGuiImplGl3.renderDrawData(ImGui.getDrawData());

        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            var backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }
}
