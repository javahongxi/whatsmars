package com.whatsmars.common.mongo;

import com.whatsmars.common.mongo.object.MongoDBConfig;
import com.whatsmars.common.ImageSizeEnum;
import com.whatsmars.common.mongo.object.MongoDBCredential;
import com.whatsmars.common.mongo.object.MongoDBDriver;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Adler32;

/**
 * Author: qing
 * Date: 14-10-11
 */
public class GridFSClient extends MongoDBClient{

    private GridFS _gridFS = null;

    private Object lock = new Object();

    protected static final String[] IMAGE_FORMAT = {"jpg","jpeg","png"};


    public void setMongoDBDriver(MongoDBDriver mongoDBDriver) {
        this.mongoDBDriver = mongoDBDriver;
    }

    public GridFS getInstance() {
        if(_gridFS != null) {
            return _gridFS;
        }
        synchronized (lock) {
            if(_gridFS != null) {
                return _gridFS;
            }
            _gridFS = new GridFS(mongoDBDriver.getDB(this.databaseName));
            return _gridFS;
        }

    }

    public void close() {
        mongoDBDriver.close();
    }

    /**
     *
     * @param inputStream 文件流
     * @param format 文件格式，“pdf”，“png”等，不包含后缀符号“.”
     * @return
     */
    public String saveFile(InputStream inputStream,String format,String uid) {
        try {
            GridFS gridFS = getInstance();

            //随机生成文件名称，多次重试
            String filename = this.randomFileName();
            //如果有文件重复，则重新生成filename
            while (true) {
                GridFSDBFile _current = gridFS.findOne(filename);
                //如果文件不存在，则保存操作
                if (_current == null) {
                    break;
                }
                filename = this.randomFileName();
            }

            GridFSInputFile file = gridFS.createFile(inputStream, filename);
            if(format != null) {
                file.put("format", format);
            }
            if(uid != null) {
                file.put("uid",uid);
            }
            file.put("content-type","application/octet-stream");
            file.save();
            return concat(filename,format);
        }catch (Exception e) {

            throw new RuntimeException(e);
        } finally {
            try{
                inputStream.close();
            }catch (Exception ex) {
                //
            }
        }
    }

    private String concat(String filename,String format) {
        if(format == null) {
            return filename;
        }
        if(format.startsWith(".")) {
            return filename + format;
        }
        return filename + "." + format;
    }

    private String randomFileName() {
        return RandomStringUtils.random(32, true, true).toLowerCase();
    }

    public void delete(String filename) {
        GridFS gridFS = getInstance();
        gridFS.remove(filename);
    }

    public InputStream getFile(String filename) {
        GridFS gridFS = getInstance();
        GridFSDBFile _current = gridFS.findOne(filename);
        if(_current == null) {
            return null;
        }
        return _current.getInputStream();
    }

    public InputStream getImage(String filename,String path) throws Exception{
        //获取最大边,等比缩放
        if(ImageSizeEnum.valueOfPath(path) == null) {
            return null;
        }

        GridFS gridFS = getInstance();
        GridFSDBFile _current = gridFS.findOne(filename);
        if(_current == null) {
            return null;
        }

        int size = ImageSizeEnum.valueOfPath(path).size;

        int max = (Integer)_current.get("max");//图片的实际尺寸

        InputStream result = null;
        //裁剪
        if(size < max) {
            InputStream inputStream = _current.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);

            inputStream.close();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BufferedImage thumbnail = Scalr.resize(image, size);//保留最大尺寸
            String format = (String) _current.get("format");
            ImageIO.write(thumbnail, format, bos);
            result = new ByteArrayInputStream(bos.toByteArray());
        } else {
            result = _current.getInputStream();
        }

        return result;
    }


    /**
     *
     * @param inputStream 输入流
     * @return
     * @throws Exception
     */
    public String saveImage(InputStream inputStream,String uid) throws Exception{

        BundleEntry bundleEntry = this.drain(inputStream);
        if(bundleEntry == null) {
            throw new RuntimeException("file isn't a image!");
        }

        ByteArrayInputStream bis = bundleEntry.inputStream;

        String _currentFileName = this.isExistedImage(bundleEntry);

        //如果文件md5已存在
        if(_currentFileName != null) {
            return _currentFileName;
        }

        String format = bundleEntry.format;
        GridFS gridFS = getInstance();
        String filename = this.randomFileName();
        //检测文件名称
        while(true){
            GridFSDBFile _current = gridFS.findOne(filename);
            //如果文件不存在，则保存操作
            if (_current == null) {
                break;
            }
            //否则，重新生成文件名称
            filename = randomFileName();
        }
        //图片处理
        bis.reset();

        //保存原图
        GridFSInputFile _inputFile = gridFS.createFile(bis, filename);
        if(uid != null) {
            _inputFile.put("uid", uid);
        }
        _inputFile.put("max",bundleEntry.max);
        _inputFile.put("crc",bundleEntry.crc);
        _inputFile.put("format",format);
        _inputFile.put("md5_source",bundleEntry.md5);
        _inputFile.save();

        return concat(filename,format);

    }

    private String isExistedImage(BundleEntry entry) {
        GridFS gridFS = getInstance();
        DBObject query = new BasicDBObject();
        query.put("crc",entry.crc);
        query.put("md5_source",entry.md5);
        GridFSDBFile _current = gridFS.findOne(query);
        //根据MD5值查询，检测是否存在
        if(_current == null) {
            return null;
        }
        String format = (String)_current.get("format");
        if(format.startsWith(".")) {
            return _current.getFilename() + format;
        }
        return _current.getFilename() + "." + format;
    }

    /**
     * 因为图片的stream需要reset，所以需要将流全部汲取
     * @param inputStream
     * @return
     * @throws Exception
     */
    protected BundleEntry drain(InputStream inputStream) throws Exception{
        //
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //计算源文件的md5、crc，以防止图片的重复上传
        Adler32 crc = new Adler32();
        try{
            while(true) {
                int _c = inputStream.read();
                if(_c == -1) {
                    break;
                }
                bos.write(_c);
                crc.update(_c);
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            inputStream.close();
        }

        //第一步：图片格式
        List<String> formats = new ArrayList<String>();//
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(bos.toByteArray()));
        imageInputStream.mark();
        try {
            Iterator<ImageReader> it = ImageIO.getImageReaders(imageInputStream);
            while (it.hasNext()) {
                ImageReader reader = it.next();
                String format  = reader.getFormatName().toLowerCase();
                if(ArrayUtils.contains(IMAGE_FORMAT, format)) {
                    formats.add(format);
                }
            }
        }catch (Exception ex) {
            //
        }

        //如果格式不合法，则直接返回
        if(formats.isEmpty()) {
            try {
                imageInputStream.close();
            } catch (Exception e) {
                //
            }
            return null;
        }

        String md5 = DigestUtils.md5Hex(bos.toByteArray());//求原始图片的MD5，和crc
        System.out.println("md5:" + md5);
        imageInputStream.reset();

        BufferedImage image = ImageIO.read(imageInputStream);

        //获取最大边,等比缩放
        int max = Math.max(image.getHeight(), image.getWidth());

        bos = new ByteArrayOutputStream();
        //如果尺寸超过最大值，则resize
        if(max > ImageSizeEnum.PIXELS_MAX.size) {
            max = ImageSizeEnum.PIXELS_MAX.size;
        }
        String format = formats.get(0);
        BufferedImage thumbnail = Scalr.resize(image, max);//保留最大尺寸
        ImageIO.write(thumbnail, format, bos);

        return new BundleEntry(new ByteArrayInputStream(bos.toByteArray()),md5,crc.getValue(),format,max);
    }


    protected class BundleEntry {
        String md5;
        long crc;
        String format;
        int max;
        ByteArrayInputStream inputStream;
        BundleEntry(ByteArrayInputStream inputStream,String md5,long crc,String format,int max) {
            this.md5 = md5;
            this.crc = crc;
            this.inputStream = inputStream;
            this.format = format;
            this.max = max;
        }
    }



    public static void main(String[] args) throws Exception{

        MongoDBDriver mongoDBDriver = new MongoDBDriver();
        try {
            GridFSClient client = new GridFSClient();
            MongoDBConfig mongoDBConfig = new MongoDBConfig();
            //mongoDBConfig.setAddresses("61.172.238.149:27017");
            mongoDBConfig.setAddresses("61.172.240.58:27017");
            List<MongoDBCredential> credentials = new ArrayList<MongoDBCredential>();
            MongoDBCredential credential = new MongoDBCredential();
            credential.setDatabaseName("whatsmars-fs");
            credential.setUsername("whatsmars");
            //credential.setPassword("haodai.com");
            credential.setPassword("passwordisfs");
            credentials.add(credential);
            mongoDBConfig.setCredentials(credentials);
            mongoDBDriver.setConfiguration(mongoDBConfig);
            mongoDBDriver.init();
            client.setDatabaseName("whatsmars-fs");
            client.setMongoDBDriver(mongoDBDriver);
            testUpload(client);
            //testClear(client.getInstance());
            //testGetImage(client.getInstance(),"xhgcguccxumuyl9hzdombgfvzgriv7rf",null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoDBDriver.close();
        }

    }


    protected static void testClear(GridFS gridFS) {
        DBCursor cursor = gridFS.getFileList();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            String filename = (String)cursor.next().get("filename");
            System.out.println(filename);
            System.out.println(dbObject.toString());
            gridFS.remove(filename);
        }
        cursor.close();
    }


    protected static void testUpload(GridFSClient client) throws Exception{
        FileInputStream inputStream = new FileInputStream(new File("/data/tmp/222222222.jpg"));

        try {

            String filename = client.saveImage(inputStream, null);
            System.out.println(filename);
            String source = filename.substring(0,filename.lastIndexOf("."));
            System.out.println(source);
            InputStream result = client.getImage(source, "x4");
            if(result == null) {
                System.out.println("not found!");
            }
            //vejibw36famkscjyksgke7bugzonnyan

            FileOutputStream outputStream = new FileOutputStream("/data/tmp/" + filename);
            while (true) {
                int i = result.read();
                if( i == -1) {
                    break;
                }
                outputStream.write(i);
            }
            outputStream.flush();
            outputStream.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }

    }

    protected static void testGetImage(GridFS gridFS,String filename,String path) {
        DBObject query = new BasicDBObject();
        query.put("md5_source","9e131ae4ed7337d4712650229b827725");
        GridFSDBFile file = gridFS.findOne(query);
        if(file != null) {
            System.out.println(file.getFilename());
        }
    }

}
