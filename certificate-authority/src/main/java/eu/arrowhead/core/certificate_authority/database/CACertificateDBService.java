package eu.arrowhead.core.certificate_authority.database;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import eu.arrowhead.common.CoreCommonConstants;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.database.entity.CaCertificate;
import eu.arrowhead.common.database.repository.CaCertificateRepository;
import eu.arrowhead.common.dto.internal.CertificateCheckResponseDTO;
import eu.arrowhead.common.dto.internal.DTOConverter;
import eu.arrowhead.common.dto.internal.IssuedCertificatesResponseDTO;
import eu.arrowhead.common.exception.ArrowheadException;
import eu.arrowhead.common.exception.DataNotFoundException;
import eu.arrowhead.common.exception.InvalidParameterException;

@Service
public class CACertificateDBService {

    @Autowired
    CaCertificateRepository caCertificateRepository;

    private final Logger logger = LogManager.getLogger(CACertificateDBService.class);

    public void saveCertificateInfo(final String commonName, final BigInteger serial, final String requesterCN) {
        logger.debug("saveCertificateInfo started...");

        if (Utilities.isEmpty(commonName)) {
            throw new InvalidParameterException("commonName cannot be empty");
        }
        if (serial == null) {
            throw new InvalidParameterException("serial cannot be empty");
        }
        if (Utilities.isEmpty(requesterCN)) {
            throw new InvalidParameterException("requesterCN cannot be empty");
        }
        logger.debug("saveCertificateInfo for " + commonName);

        CaCertificate certificate = new CaCertificate(commonName, serial, requesterCN);
        caCertificateRepository.save(certificate);
    }

    public IssuedCertificatesResponseDTO getcertificateEntries(final int page, final int size,
            final Direction direction, final String sortField) {
        logger.debug("getcertificateEntries started...");

        final int validatedPage = page < 0 ? 0 : page;
        final int validatedSize = size < 1 ? Integer.MAX_VALUE : size;
        final Direction validatedDirection = direction == null ? Direction.ASC : direction;
        final String validatedSortField = Utilities.isEmpty(sortField) ? CoreCommonConstants.COMMON_FIELD_NAME_ID
                : sortField.trim();

        if (!CaCertificate.SORTABLE_FIELDS_BY.contains(validatedSortField)) {
            throw new InvalidParameterException(
                    "Sortable field with reference '" + validatedSortField + "' is not available");
        }

        try {
            final PageRequest request = PageRequest.of(validatedPage, validatedSize, validatedDirection,
                    validatedSortField);
            final Page<CaCertificate> queryResult = caCertificateRepository.findAll(request);
            return DTOConverter.convertCaCertificateListToIssuedCertificatesResponseDTO(queryResult);
        } catch (final Exception ex) {
            logger.debug(ex.getMessage(), ex);
            throw new ArrowheadException(CoreCommonConstants.DATABASE_OPERATION_EXCEPTION_MSG);
        }
    }

    public CertificateCheckResponseDTO isCertificateValidNow(final BigInteger serial) {
        logger.debug("isCertificateValidNow started...");

        if (serial == null) {
            throw new InvalidParameterException("serial cannot be null");
        }

        Optional<CaCertificate> queryResult = caCertificateRepository.findBySerial(serial);

        if (queryResult.isPresent()) {
            final CaCertificate cert = queryResult.get();
            final ZonedDateTime now = ZonedDateTime.now();
            CertificateCheckResponseDTO responseDTO = new CertificateCheckResponseDTO();
            responseDTO.setVersion(1);
            responseDTO.setProducedAt(now);
            responseDTO.setCommonName(cert.getCommonName());
            responseDTO.setSerialNumber(cert.getSerial());
            responseDTO.setStatus("unknown");

            if (now.isAfter(cert.getValidAfter()) && now.isBefore(cert.getValidBefore())) {
                if (cert.getRevokedAt() != null) {
                    responseDTO.setStatus("revoked");
                    responseDTO.setEndOfValidity(cert.getRevokedAt());
                } else {
                    responseDTO.setStatus("good");
                    responseDTO.setEndOfValidity(cert.getValidBefore());
                }
            } else {
                responseDTO.setEndOfValidity(now);
            }

            return responseDTO;
        } else {
            throw new DataNotFoundException("Cannot find trusted certificate");
        }
    }

    public boolean revokeCertificate(long id) {
        logger.debug("revokeCertificate started...");

        return caCertificateRepository.setRevokedById(id, ZonedDateTime.now());
    }
}
