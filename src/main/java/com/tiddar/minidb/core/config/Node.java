package com.tiddar.minidb.core.config;

import org.yaml.snakeyaml.Yaml;

import java.util.List;

/**
 * @author zhangweichen
 * @date 2019/9/6 12:32 下午
 */
public class Node {
    public String host;
    public Integer port;
    public Integer syncPort;
    public NodeRoleEnum role;
    public Node master;
    public List<Node> slaves;
    private volatile static Node instance = null;

    public static Node getInstance() {
        if (instance == null) {
            synchronized (Node.class) {
                if (instance == null) {
                    Yaml yaml = new Yaml();
                    instance = yaml.loadAs(Thread.currentThread().getContextClassLoader().getResourceAsStream("minidb.yml"), Node.class);
                    if (System.getProperty("role") != null) {
                        instance.role = NodeRoleEnum.valueOf(System.getProperty("role").toUpperCase());
                    }
                    if (System.getProperty("port") != null) {
                        instance.port = Integer.parseInt(System.getProperty("port"));
                    }
                    if (System.getProperty("syncPort") != null) {
                        instance.syncPort = Integer.parseInt(System.getProperty("syncPort"));
                    }
                    if (System.getProperty("host") != null) {
                        instance.host = System.getProperty("host");
                    }
                }
            }
        }
        return instance;
    }

    public static void error(Node node){

    }

    @Override
    public String toString() {
        return "node:  " + host + ":" + port;
    }
}
