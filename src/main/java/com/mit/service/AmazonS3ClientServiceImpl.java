package com.mit.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class AmazonS3ClientServiceImpl {
    private String awsS3ImageBucket;
    private AmazonS3 amazonS3;
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public AmazonS3ClientServiceImpl(String awsS3ImageBucket)
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard().build();
        this.awsS3ImageBucket = awsS3ImageBucket;
    }

    public void uploadFileToS3Bucket(File uploadFile) {
        amazonS3.putObject(this.awsS3ImageBucket, uploadFile.getName(), uploadFile);
    }

    public void uploadFileToS3Bucket(String name,byte[] content)  {
        try {
            if (name.length() < 3) {
                name+="AAA";
            }
            File file = File.createTempFile(name,".jpg");
            FileOutputStream iofs = null;
            iofs = new FileOutputStream(file);
            iofs.write(content);
            amazonS3.putObject(this.awsS3ImageBucket, name+".jpg", file);
            log.info("User image uploaded on s3 .."+name);
            //removing the file created in the server
            file.deleteOnExit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] getFileFromS3Bucket(String name) {
        try {
            S3Object obj = amazonS3.getObject(this.awsS3ImageBucket, name+".jpg");
            S3ObjectInputStream stream = obj.getObjectContent();

            byte[] content = IOUtils.toByteArray(stream);
            obj.close();
            log.info("User image retrieved from on s3 .."+name);
            return content;
        }catch (AmazonS3Exception amazonS3Exception){
            log.info("User image not exist on s3 for "+name);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFileFromS3Bucket(String name) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(this.awsS3ImageBucket, name+".jpg"));
            log.info("User image delete from on s3 successfully.."+name);
        }catch (AmazonS3Exception amazonS3Exception){
            log.error("User image not exist on s3 for delete"+name);
        }
    }
}
