package org.hongxi.whatsmars.common.serialize;

import org.junit.Test;

/**
 * @author shenhongxi 2019/8/5
 */
public class SerializationTest {

    @Test
    public void testHessian() throws Exception {
        Serialization serialization = new HessianSerialization();
        User user = new User("hongxi", 30);
        byte[] bytes = serialization.serialize(user);
        user = serialization.deserialize(bytes, User.class);
        assert "hongxi".equals(user.name) && user.age == 30;
    }

    @Test
    public void testJava() throws Exception {
        Serialization serialization = new JavaSerialization();
        User user = new User("hongxi", 30);
        byte[] bytes = serialization.serialize(user);
        user = serialization.deserialize(bytes, User.class);
        assert "hongxi".equals(user.name) && user.age == 30;
    }

    @Test
    public void testJson() throws Exception {
        Serialization serialization = new FastJsonSerialization();
        User user = new User("hongxi", 30);
        byte[] bytes = serialization.serialize(user);
        user = serialization.deserialize(bytes, User.class);
        assert "hongxi".equals(user.name) && user.age == 30;
    }

    @Test
    public void testMsgpack() throws Exception {
        Serialization serialization = new MsgpackSerialization();
        User user = new User("hongxi", 30);
        byte[] bytes = serialization.serialize(user);
        user = serialization.deserialize(bytes, User.class);
        assert "hongxi".equals(user.name) && user.age == 30;
    }

    @Test
    public void testProtobuf() throws Exception {
        Serialization serialization = new ProtobufSerialization();
        // 9种基本类型之外的其他类型必须实现 MessageLite 接口
        Integer data = 1;
        byte[] bytes = serialization.serialize(data);
        data = serialization.deserialize(bytes, Integer.class);
        assert data == 1;
    }

    @Test
    public void testFST() throws Exception {
        Serialization serialization = new FSTSerialization();
        User user = new User("hongxi", 30);
        byte[] bytes = serialization.serialize(user);
        user = serialization.deserialize(bytes, User.class);
        assert "hongxi".equals(user.name) && user.age == 30;
    }

    @Test
    public void testHessianMulti() throws Exception {
        Serialization serialization = new HessianSerialization();
        User user = new User("hongxi", 30);
        Object[] data = new Object[]{user, 123, "xxx", false};
        byte[] bytes = serialization.serializeMulti(data);

        Class[] classes = new Class[data.length];
        for (int i = 0; i < data.length; i++) {
            classes[i] = data[i].getClass();
        }
        data = serialization.deserializeMulti(bytes, classes);
        assert data.length == 4 && data[2].toString().equals("xxx");
    }

    @Test
    public void testJavaMulti() throws Exception {
        Serialization serialization = new JavaSerialization();
        User user = new User("hongxi", 30);
        Object[] data = new Object[]{user, 123, "xxx", false};
        byte[] bytes = serialization.serializeMulti(data);

        Class[] classes = new Class[data.length];
        for (int i = 0; i < data.length; i++) {
            classes[i] = data[i].getClass();
        }
        data = serialization.deserializeMulti(bytes, classes);
        assert data.length == 4 && data[2].toString().equals("xxx");
    }

    @Test
    public void testJsonMulti() throws Exception {
        Serialization serialization = new FastJsonSerialization();
        User user = new User("hongxi", 30);
        Object[] data = new Object[]{user, 123, "xxx", false};
        byte[] bytes = serialization.serializeMulti(data);

        Class[] classes = new Class[data.length];
        for (int i = 0; i < data.length; i++) {
            classes[i] = data[i].getClass();
        }
        data = serialization.deserializeMulti(bytes, classes);
        assert data.length == 4 && data[2].toString().equals("xxx");
    }

    @Test
    public void testProtobufMulti() throws Exception {
        Serialization serialization = new ProtobufSerialization();
        Object[] data = new Object[]{9999L, 123, "xxx", false};
        byte[] bytes = serialization.serializeMulti(data);

        Class[] classes = new Class[data.length];
        for (int i = 0; i < data.length; i++) {
            classes[i] = data[i].getClass();
        }
        data = serialization.deserializeMulti(bytes, classes);
        assert data.length == 4 && data[2].toString().equals("xxx");
    }

}
