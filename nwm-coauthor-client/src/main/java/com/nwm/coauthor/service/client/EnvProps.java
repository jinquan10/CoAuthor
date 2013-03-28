package com.nwm.coauthor.service.client;

public enum EnvProps {
    LOCAL_HOST("local", "http://localhost:8081/nwm-coauthor-webapp");

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    private EnvProps(String env, String hostAndServicePath) {
        this.env = env;
        this.setHostAndServicePath(hostAndServicePath);
    }

    private String env;
    private String hostAndServicePath;

    public String getHostAndServicePath() {
        return hostAndServicePath;
    }

    public void setHostAndServicePath(String hostAndServicePath) {
        this.hostAndServicePath = hostAndServicePath;
    }
}
