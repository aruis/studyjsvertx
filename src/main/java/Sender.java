import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

public class Sender {
    public static void main(String[] args) {

        JsonObject zkConfig = new JsonObject();
        zkConfig.put("zookeeperHosts", "192.168.0.88:2181,192.168.0.88:2182,192.168.0.88:2183");
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

                EventBus eb = vertx.eventBus();
                vertx.setPeriodic(1000, v -> {

                    eb.send("ping-address", "ping!", reply -> {
                        if (reply.succeeded()) {
                            System.out.println("Received reply " + reply.result().body());
                        } else {
                            System.out.println("No reply");
                        }
                    });

                });

            } else {
                System.out.println("connect zookeeper error.");
            }
        });

//        Vertx.vertx().deployVerticle("/Users/liurui/develop/workspace-study/studyjsvertx/src/main/js/MyJavaScriptVerticle.js");
    }


}
