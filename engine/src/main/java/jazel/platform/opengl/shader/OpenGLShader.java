package jazel.platform.opengl.shader;

import jazel.engine.core.Core;
import jazel.engine.core.Log;
import jazel.engine.renderer.utils.Utils;
import jazel.engine.renderer.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.lwjgl.opengl.GL20.*;

public class OpenGLShader extends Shader {

    public OpenGLShader(String name, String directory) {
        super(name);

        Map<Integer, String> shaderSources = null;
        try {
            shaderSources = readFiles(directory);
        } catch (IOException e) {
            Core.assertion(true, "Could not read files");
        }
        compile(shaderSources);
    }
    private Map<Integer, String> readFiles(String folder) throws IOException {
        var classLoader = Thread.currentThread().getContextClassLoader();
        var resource = classLoader.getResource(folder);
        var shaderSources = new HashMap<Integer, String>();
        if (resource == null) {
            String dir = Utils.getPath(folder);
            var paths = Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                    .filter(file -> !file.isDirectory()).map(File::getName).collect(Collectors.toSet());

            for (var path : paths) {
                var type = path.split("\\.", -1)[1];
                var source = readFile(dir + "/" + path);
                shaderSources.put(OpenGLShaderUtils.getShaderTypeFromString(type), source);
            }
        } else if (resource.getProtocol().equals("jar")) {
            /* A JAR path */
            String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            Set<String> paths = new HashSet<>(); //avoid duplicates in case it is a subdirectory
            while(entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(folder)) { //filter according to the path
                    String entry = name.substring(folder.length());
                    if (entry.length() > 1) {
                        paths.add(entry);
                    }
                }
            }
            for (var path : paths) {
                var type = path.split("\\.", -1)[1];
                var source = readJarFile(folder + path);
                shaderSources.put(OpenGLShaderUtils.getShaderTypeFromString(type), source);
            }
        }
        return shaderSources;
    }

    private String readJarFile(String fileName) throws IOException {
        var classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(fileName);

        StringBuilder result = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        for (String line; (line = reader.readLine()) != null;) {
            result.append(line).append("\n");
        }
        return result.toString();
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
