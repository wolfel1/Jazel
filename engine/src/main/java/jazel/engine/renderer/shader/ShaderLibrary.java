package jazel.engine.renderer.shader;

import jazel.engine.core.Core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderLibrary {

    private final Map<String, Shader> shaderMap = new HashMap<>();

    public void add(String name, Shader shader) {
        Core.assertion(exists(name), "Shader already exists!");
        shaderMap.put(name, shader);
    }

    public void add(Shader shader) {
        var name = shader.getName();
        add(name, shader);
    }

    public Shader getShader(String name) {
        Core.assertion(exists(name), "Shader not found!");
        return shaderMap.get(name);
    }

    public Shader load(String name) {
        var shader = Shader.create(name);
        assert shader != null;
        add(shader);
        return shader;
    }

    public Shader load(String vertexPath, String fragmentPath) {
        var shader = Shader.createFromFile(vertexPath, fragmentPath);
        assert shader != null;
        add(shader);
        return shader;
    }


    public boolean exists(String name) {
        return shaderMap.containsKey(name);
    }
}
