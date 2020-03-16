package com.microservices.mentoring.catalog.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${aws.s3.catalogFileName}")
    private String catalogFileName;
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    private void initializeAmazonClient() {
        s3client = AmazonS3ClientBuilder.standard().withRegion(region).build();
    }

    @Retryable
    public <T> T downloadFile(TypeReference<T> reference) throws JsonProcessingException {
        String catalogData = s3client.getObjectAsString(bucketName, catalogFileName);
        return new ObjectMapper().readValue(catalogData, reference);
    }
}
