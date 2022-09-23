package dhyces.fireflies10k.platform;

import java.util.ServiceLoader;

public class Platforms {

    public static final PlatformHelper PLATFORM = load(PlatformHelper.class);

    private static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new RuntimeException("Failed to load service for: " + clazz.getName()));
    }
}
