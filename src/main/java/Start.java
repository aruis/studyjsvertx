import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

public class Start {
    public static void main(String[] args) {

        JsonObject zkConfig = new JsonObject();
        zkConfig.put("zookeeperHosts", "202.98.195.154:9081");
        zkConfig.put("rootPath", "io.vertx");
        zkConfig.put("retry", new JsonObject()
                .put("initialSleepTime", 3000)
                .put("maxTimes", 3));

        ClusterManager mgr = new ZookeeperClusterManager(zkConfig);
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                System.out.println("connect zookeeper success.");
                Vertx vertx = res.result();
                vertx.deployVerticle("receiver.js");
            } else {
                System.out.println("connect zookeeper error.");
            }
        });

//        Vertx.vertx().deployVerticle("/Users/liurui/develop/workspace-study/studyjsvertx/src/main/js/MyJavaScriptVerticle.js");
    }


}
