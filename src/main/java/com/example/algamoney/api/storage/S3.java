package com.example.algamoney.api.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
import com.example.algamoney.api.config.ApiProperty;
import com.example.algamoney.api.config.S3Config;
import com.example.algamoney.api.util.Utils;

@Component
public class S3 {
	
	private static final Logger log = LoggerFactory.getLogger(S3.class);

	@Autowired
	private AmazonS3 amazonS3;	
	
	@Autowired
	private ApiProperty apiProperty;
	
	public String salvarTemporariamente(MultipartFile arquivo) {
		
		AccessControlList acl = new AccessControlList();
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arquivo.getContentType());
		objectMetadata.setContentLength(arquivo.getSize());
		
		String nomeUnicoArquivo = gerarNomeUnico(arquivo.getOriginalFilename());
		String nomeBucket = apiProperty.getS3().getBucket();
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					nomeBucket,
					nomeUnicoArquivo,
					arquivo.getInputStream(), 
					objectMetadata)
					.withAccessControlList(acl);
			
			putObjectRequest.setTagging(new ObjectTagging(Arrays.asList(S3Config.TAG_EXPIRAR)));
			
			amazonS3.putObject(putObjectRequest);
			
			if (log.isDebugEnabled()) {
				log.debug("Arquivo {} enviado com sucesso para o S3 com o novo nome {}.", 
						arquivo.getOriginalFilename(), nomeUnicoArquivo);
			}
		} catch (IOException e) {
			throw new RuntimeException("Problemas ao tentar enviar o arquivo para o S3.", e);
		}
		
		return nomeUnicoArquivo;
	}
	
	public void salvar(String objeto) {

		String nomeBucket = apiProperty.getS3().getBucket();
		
		SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(
				nomeBucket, 
				objeto, 
				new ObjectTagging(Collections.emptyList()));
		
		amazonS3.setObjectTagging(setObjectTaggingRequest);
	}
	
	public String configurarUrl(String objeto) {
		return "https://" + apiProperty.getS3().getBucket() + ".s3.amazonaws.com/" + objeto;
	}

	private String gerarNomeUnico(String originalFilename) {
		return UUID.randomUUID().toString() + "_" + originalFilename;
	}

	public void remover(String objeto) {
		String nomeBucket = apiProperty.getS3().getBucket();
		
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(nomeBucket, objeto);
		
		amazonS3.deleteObject(deleteObjectRequest);
	}

	public void substituir(String objetoAntigo, String objetoNovo) {
		
		if (!Utils.empty(objetoAntigo)) {
			this.remover(objetoAntigo);
		}
		
		this.salvar(objetoNovo);
	}

}
