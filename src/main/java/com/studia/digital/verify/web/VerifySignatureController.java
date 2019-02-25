package com.studia.digital.verify.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.studia.digital.verify.domain.Document;
import com.studia.digital.verify.service.IVerifySignatureService;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol;
import com.studia.digital.verify.service.neoged.NeoGedDocFacet;
import com.studia.digital.verify.service.neoged.NeoGedServletCaller;

@Controller
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

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public void verifySignature(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> input = NeoGedServletCaller.lecture(request);
		Map<String, Object> result = new HashMap<>();

		String message;
		String commandeStr = (String) input.get(COMMAND_KEY);

		if (null == commandeStr) {
			message = "action demandée inconnue";
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
			message = verifySignatureService.verifyInternalSignature(doc);

		}

		result.put("codeRetour", "OK");
		result.put("message", message);

		NeoGedServletCaller.ecriture(response, result);

	}

}
