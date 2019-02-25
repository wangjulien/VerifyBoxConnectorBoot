package com.studia.digital.verify.service.neoged;

import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.studia.digital.verify.domain.Document;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestNeoGedDocFacet {

	@Autowired
	private NeoGedDocFacet neoGedDocFacet;

	@Test
	void should_get_doc_by_id_from_neoged() {
		Document doc = neoGedDocFacet.getDocumentById("AWb-gF_WkXtTuZ28Lq8y", NeoGedComProtocol.NEOGED_USER,
				NeoGedComProtocol.NEOGED_PSW, NeoGedComProtocol.NEOGED_DB);

		assertNull(doc);
	}

}
