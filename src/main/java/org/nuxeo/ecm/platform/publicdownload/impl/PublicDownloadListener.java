/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     dmetzler
 */
package org.nuxeo.ecm.platform.publicdownload.impl;

import static org.nuxeo.ecm.core.api.event.DocumentEventTypes.ABOUT_TO_CREATE;
import static org.nuxeo.ecm.core.api.event.DocumentEventTypes.BEFORE_DOC_UPDATE;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.platform.publicdownload.api.PublicDownload;

/**
 * This listeners initializes the key of a public download document.
 */
public class PublicDownloadListener implements EventListener {

    @Override
    public void handleEvent(Event event) {
        EventContext ctx = event.getContext();

        if (ctx instanceof DocumentEventContext
                && (ABOUT_TO_CREATE.equals(event.getName()) || BEFORE_DOC_UPDATE.equals(event.getName()))) {

            DocumentEventContext docCtx = (DocumentEventContext) ctx;
            DocumentModel targetDoc = docCtx.getSourceDocument();

            if (targetDoc.isVersion()) {
                return;
            }

            PublicDownload pa = targetDoc.getAdapter(PublicDownload.class);
            if (pa != null && StringUtils.isBlank(pa.getKey())) {
                pa.setKey(UUID.randomUUID().toString());
            }

        }

    }

}
