/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     dmetzler
 */
package nuxeo.publicdownload.impl;

import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.blobholder.BlobHolder;
import nuxeo.publicdownload.api.PublicDownload;

/**
 *
 *
 */
public class PublicDownloadAdapter implements PublicDownload {

    protected static final String PDL_KEY = "pdl:key";

    private final DocumentModel doc;

    public PublicDownloadAdapter(DocumentModel doc) {
        this.doc = doc;
    }

    @Override
    public String getKey() {
        return doc.getProperty(PDL_KEY).getValue(String.class);

    }

    @Override
    public void setKey(String key) {
        doc.setPropertyValue(PDL_KEY, key);
    }

    @Override
    public DocumentModel getDocument() {
        return doc;
    }


    public static Blob getBlobByKey(CoreSession session, String key) {
        String query = String.format("SELECT * FROM Document WHERE %s = '%s'", PDL_KEY, key);

        return session.query(query)
                      .stream()
                      .findFirst()
                      .map(d -> d.getAdapter(BlobHolder.class).getBlob())
                      .orElse(null);

    }

    public static PublicDownload makePublic(DocumentModel doc) {
        doc.addFacet(PublicDownload.FACET_NAME);
        return doc.getAdapter(PublicDownload.class);
    }

}
