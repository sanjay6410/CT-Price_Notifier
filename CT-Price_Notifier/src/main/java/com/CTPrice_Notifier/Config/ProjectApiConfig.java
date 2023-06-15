package com.CTPrice_Notifier.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;

import io.vrap.rmf.base.client.oauth2.ClientCredentials;

@Configuration
public class ProjectApiConfig {

	@Value("${clientId}")
	private String idClient;
	
	@Value("${clientSecret}")
	private String secretClient;
	
	@Value("${projectKey}")
	private String keyProject;

	public ProjectApiRoot createApiClient() {
		final ProjectApiRoot apiRoot = ApiRootBuilder.of()
				.defaultClient(
						ClientCredentials.of().withClientId(idClient)
								.withClientSecret(secretClient).build(),
						ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1)
				.build(keyProject);

		return apiRoot;
	}
}
