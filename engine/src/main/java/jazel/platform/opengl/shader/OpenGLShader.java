package jazel.platform.opengl.shader;

import jazel.engine.core.Core;
import jazel.engine.core.Log;
import jazel.engine.renderer.utils.Utils;
import jazel.engine.renderer.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lwjgl.opengl.GL20.*;

public class OpenGLShader extends Shader {

    public OpenGLShader(String name) {
        super(name);

        String path = rootPath + name;
        Map<Integer, String> shaderSources = null;
        try {
            shaderSources = readFiles(path);
        } catch (IOException e) {
            Core.assertion(true, "Could not read files");
        }
        compile(shaderSources);
    }

    public OpenGLShader(String vertexPath, String fragmentPath) {
        super();
    }

    public OpenGLShader(List<String> shaderPaths) {
        super();
    }

    private Map<Integer, String> readFiles(String folder) throws IOException {
        String dir = Utils.getPath(folder);
        Set<String> paths = Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory()).map(File::getName).collect(Collectors.toSet());

        var shaderSources = new HashMap<Integer, String>();
        for (var path : paths) {
            var type = path.split("\\.", -1)[1];
            var source = readFile(dir + "/" + path);
            shaderSources.put(OpenGLShaderUtils.getShaderTypeFromString(type), source);
        }
        return shaderSources;
    }

    private String readFile(String fileName) {
        var file = new File(fileName);

        var result = "";
        try {
            result = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void compile(Map<Integer, String> shaderSources) {
        var program = glCreateProgram();
        var shaderIDs = new ArrayList<Integer>(shaderSources.size());

        for (var keyValue : shaderSources.entrySet()) {
            var type = keyValue.getKey();
            var source = keyValue.getValue();

            int shader = glCreateShader(type);

            glShaderSource(shader, source);
            glCompileShader(shader);

            int isCompiled = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (isCompiled == GL_FALSE) {
                var infoLog = glGetShaderInfoLog(shader);

                glDeleteShader(shader);

                Log.getCoreLogger().error(infoLog);
                Core.assertion(true, "Shader compilation failure");
                break;
            }

            glAttachShader(program, shader);
            shaderIDs.add(shader);
        }

        glLinkProgram(program);

        var isLinked = glGetProgrami(program, GL_LINK_STATUS);
        if (isLinked == GL_FALSE) {
            var infoLog = glGetProgramInfoLog(program);

            glDeleteProgram(program);

            for (var id : shaderIDs) {
                glDeleteShader(id);
            }

            Log.getCoreLogger().error(infoLog);
            Core.assertion(true, "Shader link failure");
            return;
        }

        for (var id : shaderIDs) {
            glDetachShader(program, id);
            glDeleteShader(id);
        }
        rendererID = program;
    }

    @Override
    public void bind() {
        glUseProgram(rendererID);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }

    @Override
    public void setInt(String name, int value) {
        var location = glGetUniformLocation(rendererID, name);
        glUniform1i(location, value);
    }

    @Override
    public void setIntArray(String name, int[] value) {

        var location = glGetUniformLocation(rendererID, name);
        glUniform1iv(location, value);
    }

    @Override
    public void setFloat(String name, float value) {

        var location = glGetUniformLocation(rendererID, name);
        glUniform1f(location, value);
    }

    @Override
    public void setFloat2(String name, Vector2f value) {

        var location = glGetUniformLocation(rendererID, name);
        glUniform2f(location, value.x, value.y);
    }

    @Override
    public void setFloat3(String name, Vector3f value) {

        var location = glGetUniformLocation(rendererID, name);
        glUniform3f(location, value.x, value.y, value.z);
    }

    @Override
    public void setFloat4(String name, Vector4f value) {
        var location = glGetUniformLocation(rendererID, name);
        glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    @Override
    public void setMat4(String name, Matrix4f value) {
        var location = glGetUniformLocation(rendererID, name);
        glUniformMatrix4fv(location, false, value.get(new float[4 * 4]));
    }

    @Override
    public void destroy() {
        glDeleteProgram(rendererID);
    }
}
