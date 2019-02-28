package com.studia.digital.verify.service.neoged;

import java.util.Base64;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.studia.digital.verify.domain.Document;
import com.studia.digital.verify.domain.NeoGedRequestMsg;
import com.studia.digital.verify.domain.NeoGedResponseMsg;
import com.studia.digital.verify.exception.VerifySignatureException;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol.Command;

/**
 * Class of all operation with Neoged : using a RestTemplate to communicate with Neoged
 * 
 * @author Jiliang.WANG
 *
 */
@Service
public class NeoGedDocFacet {
	private static final Logger LOGGER = LoggerFactory.getLogger(NeoGedDocFacet.class);

	@Value("${app.url.neoged-host:https://neoged.telino.fr/cdmsserveur/startElasticXML}")
	private String neoGedHostUrl;

	private final RestTemplate restTemplate;

	public NeoGedDocFacet(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}
	
	
	public String extractDocId(final String elasticCommand) {
		Matcher matcher = Pattern.compile("\\(_id=([^)]+)\\)").matcher(elasticCommand);
		if (matcher.find()) 
			return matcher.group(1);
		else
			throw new VerifySignatureException("Command does not contain DocID : " + elasticCommand); 
	}
	
	/**
	 * Get a document from NeoGed
	 * 
	 * @param docId
	 * @param user
	 * @param encryptedPassword
	 * @param nomBase
	 * @return
	 */
	public Document getDocumentById(final String docId, final String user, final String encryptedPassword,
			final String nomBase) {

		NeoGedRequestMsg getDocMessage = new NeoGedRequestMsg.Builder()
				.setElasticCommand(String.format(Command.NEOGED_GET_DOC.toString(), docId))
				.setUser(user).setEncryptedPassword(encryptedPassword).setNomBase(nomBase)
				.build();

		Document orginalDoc = new Document();
		orginalDoc.setDocId(docId);
				
		templatePostMethod(getDocMessage, orginalDoc, (doc, resp) -> {
			doc.setContenuBase64(resp.getData().get(0).get(0).get(0));
		});

		return orginalDoc;
	}
	
	/**
	 * Search extra informations (ex, Title) of a given document
	 * 
	 * @param docId
	 * @param user
	 * @param encryptedPassword
	 * @param nomBase
	 * @return
	 */
	public void searchDocumentById(final Document orginalDoc, final String user, final String encryptedPassword,
			final String nomBase) {

		NeoGedRequestMsg searchDocMessage = new NeoGedRequestMsg.Builder()
				.setElasticCommand(String.format(Command.NEOGED_SEARCH.toString(), orginalDoc.getDocId()))
//				.setElasticRequest(NeoGedComProtocol.ELASTIC_REQUEST_FOR_SEARCH)
				.setElasticFields(NeoGedComProtocol.ELASTIC_FIELDS_FOR_SEARCH)
				.setUser(user).setEncryptedPassword(encryptedPassword).setNomBase(nomBase)
				.build();
				
		templatePostMethod(searchDocMessage, orginalDoc, (doc, resp) -> {
			doc.setTitle(resp.getData().get(0).get(0).get(0));
		});
	}

	/**
	 * Put a document into NeoGed
	 * 
	 * @param document
	 * @param user
	 * @param encryptedPassword
	 * @param nomBase
	 */
	public void putDocument(final Document document, final String user, final String encryptedPassword,
			final String nomBase) {

		NeoGedRequestMsg putDocMessage = new NeoGedRequestMsg.Builder()
				.setElasticCommand(Command.NEOGED_PUT.toString())
				.setFileContent(document.getContenuBase64())
				.setElasticTaille(Base64.getDecoder().decode(document.getContenuBase64()).length)
				.setElasticDocName(document.getTitle())
				.setElasticContentType(document.getMimeType())
				.setUser(user).setEncryptedPassword(encryptedPassword).setNomBase(nomBase)
				.build();

		templatePostMethod(putDocMessage, document, (doc, resp) -> {
			doc.setDocId(resp.getDocumentID());
		});
	}

	/**
	 * Attach the tokenDoc to orignalDoc
	 * 
	 * @param originalDoc
	 * @param tokenDoc
	 * @param user
	 * @param encryptedPassword
	 * @param nomBase
	 */
	public void attachDocuments(final Document originalDoc, final Document tokenDoc, final String user,
			final String encryptedPassword, final String nomBase) {
		NeoGedRequestMsg attachDocMessage = new NeoGedRequestMsg.Builder()
				.setElasticCommand(Command.NEOGED_ATTACH_DOCUMENTS.toString())
				.setElasticId(originalDoc.getDocId())
				.setDocumentsList(String.join(";", tokenDoc.getDocId(), tokenDoc.getTitle(), tokenDoc.getMimeType()))
				.setUser(user).setEncryptedPassword(encryptedPassword).setNomBase(nomBase)
				.build();

		templatePostMethod(attachDocMessage, originalDoc, (doc, resp) -> {
		});
	}

	private void templatePostMethod(final NeoGedRequestMsg neoGedRequestMsg, final Document document,
			BiConsumer<Document, NeoGedResponseMsg> apply) {
		HttpEntity<NeoGedRequestMsg> request = new HttpEntity<>(neoGedRequestMsg);

		ResponseEntity<NeoGedResponseMsg> response = restTemplate.postForEntity(neoGedHostUrl, request,
				NeoGedResponseMsg.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			if ("OK".equals(response.getBody().getCodeRetour())) {
				LOGGER.debug(response.getBody().toString());
				
				apply.accept(document, response.getBody());

			} else {
				String msg = "NeoGed response error : " + response.getBody().getCodeRetour() + "\n"
						+ response.getBody().getMessageErreur();
				LOGGER.error(msg);
				throw new VerifySignatureException(msg);
			}

		} else {
			String msg = "HTTP error : " + response.getStatusCode();
			LOGGER.error(msg);
			throw new VerifySignatureException(msg);
		}
	}
}
