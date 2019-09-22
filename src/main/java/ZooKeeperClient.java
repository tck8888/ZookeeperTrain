import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZooKeeperClient {

    private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private int sessionTimeout = 2000;

    private ZooKeeper zkClient = null;

    private String nodeName="/tianck";
    @Before
    public void init() {
        try {
            zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("===" + watchedEvent.toString());

                    try {
                        List<String> children = zkClient.getChildren("/", true);
                        System.err.println("-------start-------");
                        for (String child : children) {
                            System.out.println(child);
                        }
                        System.err.println("-------end-------");
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

    //创建节点
    @Test
    public void create() {
        try {
            String path = zkClient.create(
                    nodeName,
                    "tck".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);

            System.out.println("===create path=" + path);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //获取子节点并监听节点的变化
    @Test
    public void monitorData(){
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //判断节点是否存在
    @Test
    public void checkNodeExist(){
        try {
            Stat stat = zkClient.exists(nodeName, false);
            if (stat==null){
                System.err.println(nodeName+" 节点不存在");
            }else {
                System.out.println(nodeName+" 节点存在");
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
