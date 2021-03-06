/********************************************************************************
 * Copyright (c) 2019 AITIA
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   AITIA - implementation
 *   Arrowhead Consortia - conceptualization
 ********************************************************************************/

package eu.arrowhead.common.core;

import org.springframework.util.Assert;

import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.common.Utilities;

public enum CoreSystemService {

	//=================================================================================================
	// elements
	
	// Authorization services
	AUTH_CONTROL_INTRA_SERVICE(CommonConstants.CORE_SERVICE_AUTH_CONTROL_INTRA, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_INTRA_CHECK_URI),
	AUTH_CONTROL_INTER_SERVICE(CommonConstants.CORE_SERVICE_AUTH_CONTROL_INTER, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_INTER_CHECK_URI),
	AUTH_TOKEN_GENERATION_SERVICE(CommonConstants.CORE_SERVICE_AUTH_TOKEN_GENERATION, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_TOKEN_URI),
	AUTH_PUBLIC_KEY_SERVICE(CommonConstants.CORE_SERVICE_AUTH_PUBLIC_KEY, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_KEY_URI),
	AUTH_CONTROL_SUBSCRIPTION_SERVICE(CommonConstants.CORE_SERVICE_AUTH_CONTROL_SUBSCRIPTION, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_SUBSCRIPTION_CHECK_URI),
	
	// Orchestrator services
	ORCHESTRATION_SERVICE(CommonConstants.CORE_SERVICE_ORCH_PROCESS, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_PROCESS_URI),
	ORCHESTRATION_QOS_ENABLED_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_ENABLED, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_ENABLED_URI),
	ORCHESTRATION_QOS_RESERVATIONS_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_RESERVATIONS, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_RESERVATIONS_URI),
	ORCHESTRATION_QOS_TEMPORARY_LOCK_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_TEMPORARY_LOCK, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_TEMPORARY_LOCK_URI),
	ORCHESTRATION_QOS_CONFIRM_RESERVATION_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_CONFIRM_RESERVATION, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_RESERVATIONS_URI),
	
	// Gatekeeper services
	GATEKEEPER_GLOBAL_SERVICE_DISCOVERY(CommonConstants.CORE_SERVICE_GATEKEEPER_GSD, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_GSD_SERVICE),
	GATEKEEPER_INTER_CLOUD_NEGOTIATION(CommonConstants.CORE_SERVICE_GATEKEEPER_ICN, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_ICN_SERVICE),
	GATEKEEPER_PULL_CLOUDS(CommonConstants.CORE_SERVICE_GATEKEEPER_PULL_CLOUDS, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_PULL_CLOUDS_SERVICE),
	GATEKEEPER_COLLECT_SYSTEM_ADDRESSES(CommonConstants.CORE_SERVICE_GATEKEEPER_COLLECT_SYSTEM_ADDRESSES, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_COLLECT_SYSTEM_ADDRESSES_SERVICE),
	GATEKEEPER_COLLECT_ACCESS_TYPES(CommonConstants.CORE_SERVICE_GATEKEEPER_COLLECT_ACCESS_TYPES, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_COLLECT_ACCESS_TYPES_SERVICE),
	GATEKEEPER_RELAY_TEST_SERVICE(CommonConstants.CORE_SERVICE_GATEKEEPER_RELAY_TEST, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_RELAY_TEST_SERVICE),
	GATEKEEPER_GET_CLOUD_SERVICE(CommonConstants.CORE_SERVICE_GATEKEEPER_GET_CLOUD, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_GET_CLOUD_SERVICE +
								 CommonConstants.OP_GATEKEEPER_GET_CLOUD_SERVICE_SUFFIX),
	
	// Gateway services
	GATEWAY_PUBLIC_KEY_SERVICE(CommonConstants.CORE_SERVICE_GATEWAY_PUBLIC_KEY, CommonConstants.GATEWAY_URI + CommonConstants.OP_GATEWAY_KEY_URI),
	GATEWAY_PROVIDER_SERVICE(CommonConstants.CORE_SERVICE_GATEWAY_CONNECT_PROVIDER, CommonConstants.GATEWAY_URI + CommonConstants.OP_GATEWAY_CONNECT_PROVIDER_URI),
	GATEWAY_CONSUMER_SERVICE(CommonConstants.CORE_SERVICE_GATEWAY_CONNECT_CONSUMER, CommonConstants.GATEWAY_URI + CommonConstants.OP_GATEWAY_CONNECT_CONSUMER_URI),
	
	// Eventhandler services
	EVENT_PUBLISH_SERVICE(CommonConstants.CORE_SERVICE_EVENT_HANDLER_PUBLISH, CommonConstants.EVENT_HANDLER_URI + CommonConstants.OP_EVENT_HANDLER_PUBLISH),
	EVENT_SUBSCRIBE_SERVICE(CommonConstants.CORE_SERVICE_EVENT_HANDLER_SUBSCRIBE, CommonConstants.EVENT_HANDLER_URI + CommonConstants.OP_EVENT_HANDLER_SUBSCRIBE),
	EVENT_UNSUBSCRIBE_SERVICE(CommonConstants.CORE_SERVICE_EVENT_HANDLER_UNSUBSCRIBE, CommonConstants.EVENT_HANDLER_URI + CommonConstants.OP_EVENT_HANDLER_UNSUBSCRIBE),
	EVENT_PUBLISH_AUTH_UPDATE_SERVICE(CommonConstants.CORE_SERVICE_EVENT_HANDLER_PUBLISH_AUTH_UPDATE, CommonConstants.EVENT_HANDLER_URI + CommonConstants.OP_EVENT_HANDLER_PUBLISH_AUTH_UPDATE),
	
	// CA services
	CERTIFICATE_AUTHORITY_SIGN_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_SIGN, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_SIGN_CERTIFICATE_URI),
	CERTIFICATE_AUTHORITY_LIST_CERTIFICATES_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_LIST_CERTIFICATES, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_MGMT_CERTIFICATES_URI),
	CERTIFICATE_AUTHORITY_CHECK_CERTIFICATE_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_CHECK_CERTIFICATE, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_CHECK_CERTIFICATE_URI),
	CERTIFICATE_AUTHORITY_REVOKE_CERTIFICATE_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_REVOKE_CERTIFICATE, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_MGMT_CERTIFICATES_URI +
													CommonConstants.OP_CA_MGMT_CERTIFICATES_URI_SUFFIX),
	CERTIFICATE_AUTHORITY_LIST_TRUSTED_KEYS_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_LIST_TRUSTED_KEYS, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI),
	CERTIFICATE_AUTHORITY_CHECK_TRUSTED_KEY_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_CHECK_TRUSTED_KEY, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_CHECK_TRUSTED_KEY_URI),
	CERTIFICATE_AUTHORITY_ADD_TRUSTED_KEY_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_ADD_TRUSTED_KEY, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI),
	CERTIFICATE_AUTHORITY_DELETE_TRUSTED_KEY_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATE_AUTHORITY_DELETE_TRUSTED_KEY, CommonConstants.CERTIFICATE_AUTHRORITY_URI + CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI +
													CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI_SUFFIX),

	// Choreographer services
	CHOREOGRAPHER_SERVICE(CommonConstants.CORE_SERVICE_CHOREOGRAPHER_PROCESS, CommonConstants.CHOREOGRAPHER_URI +  CommonConstants.OP_CHOREOGRAPHER_NOTIFY_STEP_DONE),
	
	// QoS Monitor services

	QOS_MONITOR_INTRA_PING_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOS_MONITOR_INTRA_PING_MEASUREMENT, CommonConstants.QOS_MONITOR_URI + CommonConstants.OP_QOS_MONITOR_INTRA_PING_MEASUREMENT + 
										 	   CommonConstants.OP_QOS_MONITOR_INTRA_PING_MEASUREMENT_SUFFIX),
	QOS_MONITOR_INTRA_PING_MEDIAN_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOS_MONITOR_INTRA_PING_MEDIAN_MEASUREMENT, CommonConstants.QOS_MONITOR_URI + CommonConstants.OP_QOS_MONITOR_INTRA_PING_MEDIAN_MEASUREMENT),
	QOS_MONITOR_INTER_DIRECT_PING_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOS_MONITOR_INTER_DIRECT_PING_MEASUREMENT, CommonConstants.QOS_MONITOR_URI + CommonConstants.OP_QOS_MONITOR_INTER_DIRECT_PING_MEASUREMENT),
	QOS_MONITOR_INTER_RELAY_ECHO_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOS_MONITOR_INTER_RELAY_ECHO_MEASUREMENT, CommonConstants.QOS_MONITOR_URI + CommonConstants.OP_QOS_MONITOR_INTER_RELAY_ECHO_MEASUREMENT),
	QOS_MONITOR_PUBLIC_KEY_SERVICE(CommonConstants.CORE_SERVICE_QOS_MONITOR_PUBLIC_KEY, CommonConstants.QOS_MONITOR_URI + CommonConstants.OP_QOS_MONITOR_KEY_URI),
	QOS_MONITOR_JOIN_RELAY_TEST_SERVICE(CommonConstants.CORE_SERVICE_QOS_MONITOR_JOIN_RELAY_TEST, CommonConstants.QOS_MONITOR_URI + CommonConstants.OP_QOS_MONITOR_JOIN_RELAY_TEST_URI),
	QOS_MONITOR_INIT_RELAY_TEST_SERVICE(CommonConstants.CORE_SERVICE_QOS_MONITOR_INIT_RELAY_TEST, CommonConstants.QOS_MONITOR_URI + CommonConstants.OP_QOS_MONITOR_INIT_RELAY_TEST_URI),

	// Onboarding services
    ONBOARDING_WITH_CERTIFICATE_AND_NAME_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_CERTIFICATE_AND_NAME, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_CERTIFICATE_AND_NAME),
    ONBOARDING_WITH_CERTIFICATE_AND_CSR_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_CERTIFICATE_AND_CSR, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_CERTIFICATE_AND_CSR),
    ONBOARDING_WITH_SHARED_SECRET_AND_NAME_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_SHARED_SECRET_AND_NAME, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_SHARED_SECRET_AND_NAME),
    ONBOARDING_WITH_SHARED_SECRET_AND_CSR_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_SHARED_SECRET_AND_CSR, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_SHARED_SECRET_AND_CSR),

    // Device Registry services
    DEVICE_REGISTRY_REGISTER_SERVICE(CommonConstants.CORE_SERVICE_DEVICE_REGISTRY_REGISTER, CommonConstants.DEVICE_REGISTRY_URI + CommonConstants.OP_DEVICE_REGISTRY_REGISTER_URI),
    DEVICE_REGISTRY_UNREGISTER_SERVICE(CommonConstants.CORE_SERVICE_DEVICE_REGISTRY_UNREGISTER, CommonConstants.DEVICE_REGISTRY_URI + CommonConstants.OP_DEVICE_REGISTRY_UNREGISTER_URI),
    DEVICE_REGISTRY_ONBOARDING_WITH_NAME_SERVICE(CommonConstants.CORE_SERVICE_DEVICE_REGISTRY_ONBOARDING_WITH_NAME,
    											 CommonConstants.DEVICE_REGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_DEVICE_REGISTRY_ONBOARDING_WITH_NAME_URI),
    DEVICE_REGISTRY_ONBOARDING_WITH_CSR_SERVICE(CommonConstants.CORE_SERVICE_DEVICE_REGISTRY_ONBOARDING_WITH_CSR,
    											CommonConstants.DEVICE_REGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_DEVICE_REGISTRY_ONBOARDING_WITH_CSR_URI),

    // System Registry services
    SYSTEM_REGISTRY_REGISTER_SERVICE(CommonConstants.CORE_SERVICE_SYSTEM_REGISTRY_REGISTER, CommonConstants.SYSTEM_REGISTRY_URI + CommonConstants.OP_SYSTEM_REGISTRY_REGISTER_URI),
    SYSTEM_REGISTRY_UNREGISTER_SERVICE(CommonConstants.CORE_SERVICE_SYSTEM_REGISTRY_UNREGISTER, CommonConstants.SYSTEM_REGISTRY_URI + CommonConstants.OP_SYSTEM_REGISTRY_UNREGISTER_URI),
    SYSTEM_REGISTRY_ONBOARDING_WITH_NAME_SERVICE(CommonConstants.CORE_SERVICE_SYSTEM_REGISTRY_ONBOARDING_WITH_NAME,
    											 CommonConstants.SYSTEM_REGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_SYSTEM_REGISTRY_ONBOARDING_WITH_NAME_URI),
    SYSTEM_REGISTRY_ONBOARDING_WITH_CSR_SERVICE(CommonConstants.CORE_SERVICE_SYSTEM_REGISTRY_ONBOARDING_WITH_CSR,
    											CommonConstants.SYSTEM_REGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_SYSTEM_REGISTRY_ONBOARDING_WITH_CSR_URI),

    // Service Registry services
    SERVICE_REGISTRY_REGISTER_SERVICE(CommonConstants.CORE_SERVICE_SERVICE_REGISTRY_REGISTER, CommonConstants.SERVICE_REGISTRY_URI + CommonConstants.OP_SERVICE_REGISTRY_REGISTER_URI),
    SERVICE_REGISTRY_UNREGISTER_SERVICE(CommonConstants.CORE_SERVICE_SERVICE_REGISTRY_UNREGISTER, CommonConstants.SERVICE_REGISTRY_URI + CommonConstants.OP_SERVICE_REGISTRY_UNREGISTER_URI);
	
	//TODO: additional services 
	
	//=================================================================================================
	// members
	
	private final String serviceDefinition;
	private final String serviceUri;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public String getServiceDefinition() { return serviceDefinition; }
	public String getServiceUri() { return serviceUri; }
	
	
	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private CoreSystemService(final String serviceDefinition, final String serviceUri) {
		Assert.isTrue(!Utilities.isEmpty(serviceDefinition), "Service definition is null or blank");
		Assert.isTrue(!Utilities.isEmpty(serviceUri), "Service URI is null or blank");
		
		this.serviceDefinition = serviceDefinition;
		this.serviceUri = serviceUri;
	}
}
