/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     dmetzler
 */
package nuxeo.publicdownload.impl;

import static org.nuxeo.ecm.core.api.event.DocumentEventTypes.ABOUT_TO_CREATE;
import static org.nuxeo.ecm.core.api.event.DocumentEventTypes.BEFORE_DOC_UPDATE;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import nuxeo.publicdownload.api.PublicDownload;

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
