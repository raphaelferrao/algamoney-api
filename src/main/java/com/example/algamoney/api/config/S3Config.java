package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Tag;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecycleTagPredicate;

@Configuration
public class S3Config {
	
	public static final Tag TAG_EXPIRAR = new Tag("expirar", "true");
	
	@Autowired
	private ApiProperty apiProperty;
	
	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials credenciais = new BasicAWSCredentials(
				apiProperty.getS3().getAccessKeyId(), 
				apiProperty.getS3().getSecretAccessKey());
		
		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credenciais))
				.withRegion(Regions.US_WEST_2)
				.build();
		
		if (!amazonS3.doesBucketExistV2(apiProperty.getS3().getBucket())) {
			amazonS3.createBucket(
					new CreateBucketRequest(apiProperty.getS3().getBucket()));
			
			BucketLifecycleConfiguration.Rule regraExpiracao = 
					new BucketLifecycleConfiguration.Rule()
						.withId("Regra de expiracao de arquivos temporarios")
						.withFilter(new LifecycleFilter(new LifecycleTagPredicate(TAG_EXPIRAR)))
						.withExpirationInDays(1)
						.withStatus(BucketLifecycleConfiguration.ENABLED);
			
			BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration()
					.withRules(regraExpiracao);
			
			amazonS3.setBucketLifecycleConfiguration(apiProperty.getS3().getBucket(), configuration);
		}
				
		return amazonS3;
	}

}
