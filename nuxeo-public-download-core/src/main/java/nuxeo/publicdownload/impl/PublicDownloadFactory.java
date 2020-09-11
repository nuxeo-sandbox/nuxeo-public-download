/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     dmetzler
 */
package nuxeo.publicdownload.impl;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.adapter.DocumentAdapterFactory;
import nuxeo.publicdownload.api.PublicDownload;

/**
 * Decorate the document if it has the PublicDownload facet.
 */
public class PublicDownloadFactory implements DocumentAdapterFactory {

    @Override
    public Object getAdapter(DocumentModel doc, Class<?> itf) {
        if (PublicDownload.class.equals(itf) && doc.hasFacet(PublicDownload.FACET_NAME)) {
            return new PublicDownloadAdapter(doc);
        }
        return null;
    }

}
