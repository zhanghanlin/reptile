package com.demo.java.collector.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;

public class Proxys extends ArrayList<Proxy> {
    public static final Logger LOG = LoggerFactory.getLogger(Proxys.class);

    public static Random random = new Random();

    public Proxy nextRandom() {
        int r = random.nextInt(this.size());
        return this.get(r);
    }

    public void addEmpty() {
        Proxy nullProxy = null;
        this.add(nullProxy);
    }

    public void add(String ip, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        this.add(proxy);
    }

    public void add(String proxyStr) throws Exception {
        try {
            String[] infoArr = proxyStr.split(":");
            String ip = infoArr[0];
            int port = Integer.valueOf(infoArr[1]);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            this.add(proxy);
        } catch (Exception ex) {
            LOG.error("Exception", ex);
        }
    }

    public void addAllFromFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            } else {
                this.add(line);
            }
        }
    }
}