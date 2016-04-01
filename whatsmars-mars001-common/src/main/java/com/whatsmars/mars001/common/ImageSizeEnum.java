package com.whatsmars.mars001.common;

/**
 * Created by liuguanqing on 15/4/20.
 */
public enum ImageSizeEnum {

    PIXELS_200(200, "x2"),
    PIXELS_400(400, "x4"),
    PIXELS_600(600, "x6"),
    PIXELS_800(800, "x8"),

    PIXELS_MAX(1024, "xm");//最大1024
    public int size;

    public String path;

    ImageSizeEnum(int size, String path) {
        this.size = size;
        this.path = path;
    }

    public static ImageSizeEnum valueOfPath(String path) {
        if(path == null) {
            return  null;
        }
        for(ImageSizeEnum e : ImageSizeEnum.values()) {
            if(e.path.equalsIgnoreCase(path)) {
                return e;
            }
        }
        return null;
    }
}
