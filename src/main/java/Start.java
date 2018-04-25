import io.vertx.core.Vertx;

public class Start {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle("/Users/liurui/develop/workspace-study/studyjsvertx/src/main/js/MyJavaScriptVerticle.js");
    }


}
