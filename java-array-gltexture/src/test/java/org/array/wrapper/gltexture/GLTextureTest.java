package org.array.wrapper.gltexture;

import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

class GLTextureTest {

    @Test
    void constructor() throws IOException {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);      // <- ❗️ IMPORTANT: Set context version to 3.2
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);      // <- ❗️
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);// <- ❗️ IMPORTANT: Set forward compatibility, or GLFW_INVALID_VALUE error will occur
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        long window = glfwCreateWindow(720, 480, "", NULL, NULL);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        GL.createCapabilities();

        final String filename = Paths.get( "src", "test", "resources", "Joseph21_05.png").toAbsolutePath().toString();
        GLTexture glt = new GLTexture(new File(filename));
        assertNotNull(glt);
        System.out.println(glt);

        glt.bind();
        glt.unbind();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

}