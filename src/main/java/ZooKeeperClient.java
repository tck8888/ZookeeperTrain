import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;

public class ZooKeeperClient {

    private  String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private  int sessionTimeout = 2000;

    private ZooKeeper zkClient = null;


    @Test
    public void init() {
        try {
            zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println(watchedEvent.toString());

                    try {
                        zkClient.getChildren("/", true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
