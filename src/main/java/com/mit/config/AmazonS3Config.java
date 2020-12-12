package com.mit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config
{
    @Value("${aws.s3.image.bucket}")
    private String awsS3ImageBucket;

    @Bean(name = "awsS3ImageBucket")
    public String getAWSS3ImageBucket() {
        return awsS3ImageBucket;
    }
}
