package com.studia.digital.verify.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studia.digital.verify.domain.Document;
import com.studia.digital.verify.domain.NeoGedRequestMsg;
import com.studia.digital.verify.domain.NeoGedResponseMsg;
import com.studia.digital.verify.service.IVerifySignatureService;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol;
import com.studia.digital.verify.service.neoged.NeoGedDocFacet;

@RestController
@RequestMapping("/VerifySignature")
public class VerifySignatureController {
	private static final Logger LOGGER = LoggerFactory.getLogger(VerifySignatureController.class);

	public static final String COMMAND_KEY = "command";

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

		if (null == commandeStr) {
			response.setMessageErreur("action demandée inconnue");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} else {

			//
			// Require Document with DocId and ProofId
			//
			String docId = "AWb-gF_WkXtTuZ28Lq8y";
			Document doc = neogedDocFacet.getDocumentById(docId, NeoGedComProtocol.NEOGED_USER,
					NeoGedComProtocol.NEOGED_PSW, NeoGedComProtocol.NEOGED_DB);

			//
			// Send the request for Verification
			//
			String tokenBase64 = verifySignatureService.verifyInternalSignature(doc);

			//
			// Put token document in NeoGed
			//
			Document tokenDoc = new Document();
			tokenDoc.setContenuBase64(tokenBase64);
			tokenDoc.setTitle("");
			
			neogedDocFacet.putDocument(tokenDoc, NeoGedComProtocol.NEOGED_USER,
					NeoGedComProtocol.NEOGED_PSW, NeoGedComProtocol.NEOGED_DB);
			

			//
			// Attach all documents together
			//
			neogedDocFacet.attachDocuments(doc, tokenDoc, NeoGedComProtocol.NEOGED_USER,
					NeoGedComProtocol.NEOGED_PSW, NeoGedComProtocol.NEOGED_DB);
			

		}

		return ResponseEntity.ok(response);
	}

}
