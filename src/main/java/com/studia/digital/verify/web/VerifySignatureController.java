package com.studia.digital.verify.web;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studia.digital.verify.domain.Document;
import com.studia.digital.verify.domain.NeoGedRequestMsg;
import com.studia.digital.verify.domain.NeoGedResponseMsg;
import com.studia.digital.verify.exception.VerifySignatureException;
import com.studia.digital.verify.service.IVerifySignatureService;
import com.studia.digital.verify.service.neoged.NeoGedDocFacet;

/**
 * A REST controller, define the entry point of Verify Signature web service
 * 
 * @author Jiliang.WANG
 *
 */
@RestController
@RequestMapping("/VerifySignature")
public class VerifySignatureController {

	private static final String TOKEN_FILE_EXT = "_token.xml";

	private final IVerifySignatureService verifySignatureService;
	private final NeoGedDocFacet neogedDocFacet;

	public VerifySignatureController(IVerifySignatureService verifySignatureService, NeoGedDocFacet neogedDocFacet) {
		super();
		this.verifySignatureService = verifySignatureService;
		this.neogedDocFacet = neogedDocFacet;
	}

	@PostMapping
	public ResponseEntity<NeoGedResponseMsg> verifySignature(@Valid @RequestBody NeoGedRequestMsg request) {

		NeoGedResponseMsg response = new NeoGedResponseMsg();

		String commandeStr = request.getElasticCommand();
		if (null == commandeStr)
			throw new VerifySignatureException("Action demandée inconnue");
		
		//
		// 1. Require Document with DocId and ProofId
		//
		
		// original doc ID
		String docId = neogedDocFacet.extractDocId(request.getElasticCommand());	
		Document originalDoc = neogedDocFacet.getDocumentById(docId, request.getUser(), request.getEncryptedPassword(),
				request.getNomBase());

		// require its title as well
		neogedDocFacet.searchDocumentById(originalDoc, request.getUser(), request.getEncryptedPassword(),
				request.getNomBase());
		
		//
		// 1bis. if there is a external signature, we should get the external signature doc
		//
		
		//
		// 2. Send the request for Verification
		//
		String tokenBase64 = verifySignatureService.verifyInternalSignature(originalDoc);

		//
		// 3. Put token document in NeoGed
		//
		Document tokenDoc = new Document();
		tokenDoc.setContenuBase64(tokenBase64);
		tokenDoc.setTitle(originalDoc.getDocId() + TOKEN_FILE_EXT);
		tokenDoc.setMimeType(MimeTypeUtils.APPLICATION_XML_VALUE);

		neogedDocFacet.putDocument(tokenDoc, request.getUser(), request.getEncryptedPassword(), request.getNomBase());

		//
		// 4. Attach all documents together
		//
		neogedDocFacet.attachDocuments(originalDoc, tokenDoc, request.getUser(), request.getEncryptedPassword(),
				request.getNomBase());

		return ResponseEntity.ok(response);
	}
}
