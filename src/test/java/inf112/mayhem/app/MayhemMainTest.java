package inf112.mayhem.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockConstruction;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.system.Configuration;
import org.mockito.MockedConstruction;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import inf112.mayhem.controller.AppController;

class MayhemMainTest {

    private static final String GLFW_LIBRARY_NAME = "GLFW_LIBRARY_NAME";

    @AfterEach
    void resetGlfwLibraryName() {
        Configuration.GLFW_LIBRARY_NAME.set(GLFW_LIBRARY_NAME);
    }

    @Test
    void mainConfiguresAndLaunchesApplication() {
        Os originalOs = SharedLibraryLoader.os;
        SharedLibraryLoader.os = Os.Windows;
        AtomicReference<List<?>> constructorArguments = new AtomicReference<>();

        try (MockedConstruction<Lwjgl3Application> mockedConstruction = mockConstruction(Lwjgl3Application.class,
                (mock, context) -> constructorArguments.set(context.arguments()))) {
            MayhemMain.main(new String[0]);

            assertEquals(1, mockedConstruction.constructed().size());

            List<?> args = constructorArguments.get();
            assertNotNull(args);
            assertEquals(2, args.size());

            assertTrue(MayhemMain.class.desiredAssertionStatus());

            ApplicationListener listener = (ApplicationListener) args.get(0);
            assertNotNull(listener);
            assertTrue(listener instanceof AppController);

            Lwjgl3ApplicationConfiguration cfg = (Lwjgl3ApplicationConfiguration) args.get(1);
            assertEquals("Gun Mayhem 3", getPrivateField(cfg, "title"));
            assertEquals(1280, getPrivateField(cfg, "windowWidth"));
            assertEquals(768, getPrivateField(cfg, "windowHeight"));
        } finally {
            SharedLibraryLoader.os = originalOs;
        }
    }

    @Test
    void mainSetsMacSpecificGlfwLibraryNameOnMac() {
        Os originalOs = SharedLibraryLoader.os;
        SharedLibraryLoader.os = Os.MacOsX;

        try (MockedConstruction<Lwjgl3Application> ignored = mockConstruction(Lwjgl3Application.class)) {
            MayhemMain.main(new String[0]);

            assertEquals("glfw_async", Configuration.GLFW_LIBRARY_NAME.get());
        } finally {
            SharedLibraryLoader.os = originalOs;
        }
    }

    private static Object getPrivateField(Object object, String fieldName) {
        try {
            Class<?> type = object.getClass();
            while (type != null) {
                try {
                    Field field = type.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field.get(object);
                } catch (NoSuchFieldException ignored) {
                    type = type.getSuperclass();
                }
            }

            throw new NoSuchFieldException(fieldName);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }
}
