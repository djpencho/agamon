package ugis.age;

public class TestUtils {

    public static String getResourceAbsolutePath(String resourcePath) {
        return TestUtils.class.getResource(resourcePath).getPath();
    }

}
