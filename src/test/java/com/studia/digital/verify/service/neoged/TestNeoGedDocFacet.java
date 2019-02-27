package com.studia.digital.verify.service.neoged;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.studia.digital.verify.domain.Document;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol.Command;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestNeoGedDocFacet {
	
	private static final String DOC_ID = "AWkqMzesRX5N3W2R3Q1I";
	
	@Autowired
	private NeoGedDocFacet neoGedDocFacet;

	
	@Test
	void regex_should_extract_docid() {
		String docId = neoGedDocFacet.extractDocId(String.format(Command.NEOGED_GET_DOC.toString(), DOC_ID));
		
		assertEquals(DOC_ID, docId);
	}
	
	@Test
	void should_get_doc_by_id_from_neoged() {
		Document doc = neoGedDocFacet.getDocumentById(DOC_ID, NeoGedComProtocol.NEOGED_USER,
				NeoGedComProtocol.NEOGED_PSW, NeoGedComProtocol.NEOGED_DB);

		assertNotNull(doc.getContenuBase64());
	}
	
	
	@Test
	void should_get_title_by_id_from_neoged() {
		Document doc = new Document();
		doc.setDocId(DOC_ID);
		
		neoGedDocFacet.searchDocumentById(doc, NeoGedComProtocol.NEOGED_USER,
				NeoGedComProtocol.NEOGED_PSW, NeoGedComProtocol.NEOGED_DB);

		assertNotNull(doc.getTitle());
	}

}
