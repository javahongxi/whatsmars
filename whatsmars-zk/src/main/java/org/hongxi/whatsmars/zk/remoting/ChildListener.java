package org.hongxi.whatsmars.zk.remoting;

import java.util.List;

public interface ChildListener {

    void childChanged(String path, List<String> children);

}
