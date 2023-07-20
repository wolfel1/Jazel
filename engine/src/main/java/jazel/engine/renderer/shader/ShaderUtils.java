package jazel.engine.renderer.shader;

import jazel.engine.core.Core;
import jazel.engine.core.Log;
import jazel.engine.renderer.shader.enumeration.ShaderDataType;
import jazel.engine.renderer.utils.Utils;
import jazel.platform.opengl.shader.OpenGLShaderUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShaderUtils {
    public static int shaderDataTypeSize(ShaderDataType type) {
        switch (type) {
        case FLOAT:
        case INT:
            return 4;
        case FLOAT2:
        case INT2:
            return 4 * 2;
        case FLOAT3:
        case INT3:
            return 4 * 3;
        case FLOAT4:
        case INT4:
            return 4 * 4;
        case MAT3:
            return 4 * 3 * 3;
        case MAT4:
            return 4 * 4 * 4;
        case BOOL:
            return 1;
        }

        Log.getCoreLogger().error("Unknown ShaderDataType!");
        return 0;
    }

    public Map<Integer, String> readFiles(String folder) throws IOException {
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
}
