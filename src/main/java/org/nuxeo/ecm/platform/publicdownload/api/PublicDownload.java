/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     dmetzler
 */
package org.nuxeo.ecm.platform.publicdownload.api;

import org.nuxeo.ecm.core.api.DocumentModel;

/**
 * This interface expose the method to deal with PublicDownload documents.
 */
public interface PublicDownload {

    public static final String FACET_NAME = "PublicDownload";

    /**
     * @return the key that is used to retrieve that downloadable file.
     */
    public String getKey();

    /**
     * Sets the key that is used to retrieve that downloadable file.
     *
     * @param key The key to retrieve the Downloadable file.
     */
    public void setKey(String key);

    /**
     * @return the underlying document.
     */
    DocumentModel getDocument();

}
