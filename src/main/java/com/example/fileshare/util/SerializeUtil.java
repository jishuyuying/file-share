package com.example.fileshare.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author vague 7/5/2022 下午 3:52
 */
@Slf4j
public class SerializeUtil {

    private SerializeUtil() {
        //
    }

    public static <T> void serialize(T t) {
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(new FileOutputStream("EditVo.txt"))) {
            objectOutputStream.writeObject(t);
            log.info("序列化成功！已经生成EditVo.txt文件");
        } catch (IOException e) {
            log.error("序列化失败！" + e.getMessage());
        }
    }


    public static <T> T deserialize(Class<T> t) {
        try (ObjectInputStream out =
                     new ObjectInputStream(new FileInputStream("EditVo.txt"))) {
            final Object o = out.readObject();
            if (o != null) {
                log.info("反序列化成功！");
                return t.cast(o);
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("反序列化失败！" + e.getMessage());
        }
        T res = null;
        try {
            res = t.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            //
            log.error("反序列化失败！" + e.getMessage());
        }
        return res;
    }

}
