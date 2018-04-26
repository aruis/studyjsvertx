var eb = vertx.eventBus();

eb.publish("ping-address", "hello-world");
setInterval(function () {
    eb.publish("ping-address", "hello-world");
}, 1000)


console.log("Receiver ready!");