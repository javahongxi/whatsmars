package org.hongxi.whatsmars.dubbo.demo.api.vo;

import org.nustaq.serialization.annotations.Version;

import java.io.Serializable;

public class Bar implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String barId;

    private String name;
    @Version(1) // 采用fst序列化时，增加字段需加此注解，每次加字段时版本号也需增加
    private String address;

    public Bar() {}

    public Bar(String barId, String name, String address) {
        this.barId = barId;
        this.name = name;
        this.address = address;
    }

    public String getBarId() {
        return barId;
    }

    public void setBarId(String barId) {
        this.barId = barId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "barId:" + barId + ",name:" + name + ",address" + address;
    }
}
