/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     dmetzler
 */
package nuxeo.publicdownload;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.blobholder.BlobHolder;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;
import org.nuxeo.ecm.core.test.CoreFeature;
import nuxeo.publicdownload.api.PublicDownload;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

/**
 * Tests the various aspects of the PublicDownload facet.
 */
@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy("nuxeo.public.download.nuxeo-public-download-core")
public class PublicDownloadTest {

    @Inject
    CoreSession session;

    @Test
    public void a_document_which_holds_a_blob_can_be_set_a_public_downloadable_facet() throws Exception {
        DocumentModel doc = createFileDocument();

        doc = session.createDocument(doc);

        doc = session.getDocument(new PathRef(doc.getPathAsString()));
        assertThat(doc.hasFacet(PublicDownload.FACET_NAME)).isTrue();

    }

    @Test
    public void when_a_public_document_is_created_it_get_assigned_a_key() throws Exception {
        DocumentModel doc = createFileDocument();

        doc = session.createDocument(doc);

        PublicDownload pd = doc.getAdapter(PublicDownload.class);
        assertThat(pd).isNotNull();
        assertThat(pd.getKey()).isNotEmpty();
    }

    @Test
    public void when_a_document_is_updated_its_key_does_not_change() throws Exception {
        DocumentModel doc = createFileDocument();

        doc = session.createDocument(doc);

        PublicDownload pd = doc.getAdapter(PublicDownload.class);
        assertThat(pd).isNotNull();
        assertThat(pd.getKey()).isNotEmpty();

        String key = pd.getKey();

        doc.setPropertyValue("dc:title", "test");
        session.saveDocument(doc);

        doc = session.getDocument(doc.getRef());
        pd = doc.getAdapter(PublicDownload.class);
        assertThat(pd.getKey()).isEqualTo(key);

    }

    protected DocumentModel createFileDocument() {
        DocumentModel doc = session.createDocumentModel("/", "testFile", "File");
        BlobHolder bh = doc.getAdapter(BlobHolder.class);
        bh.setBlob(new StringBlob("Test"));
        doc.addFacet(PublicDownload.FACET_NAME);
        return doc;

    }
}
