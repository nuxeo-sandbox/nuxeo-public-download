/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/).
 * This is unpublished proprietary source code of Nuxeo SA. All rights reserved.
 * Notice of copyright on this source code does not indicate publication.
 *
 * Contributors:
 *     dmetzler
 */
package org.nuxeo.ecm.platform.publicdownload.web;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CloseableCoreSession;
import org.nuxeo.ecm.core.api.CoreInstance;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.api.repository.Repository;
import org.nuxeo.ecm.core.api.repository.RepositoryManager;
import org.nuxeo.ecm.platform.publicdownload.impl.PublicDownloadAdapter;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.runtime.api.Framework;

@Path("/publicdl")
@Produces("text/html;charset=UTF-8")
@WebObject(type = "PublicDownlowd")
public class PublicDownloadResource {

    @Path("{key}")
    public Object getDownload(@PathParam("key") String key) {
        Blob blob = Framework.doPrivileged(() -> {
            try (CloseableCoreSession session = openSession()) {
                return PublicDownloadAdapter.getBlobByKey(session, key);
            }
        });

        return blob == null ? build404() : blob;
    }

    private Response build404() {
        return Response.status(404).entity("Document not found.").type("text/plain").build();
    }

    private CloseableCoreSession openSession() {
        Repository repository = Framework.getService(RepositoryManager.class).getDefaultRepository();
        if (repository != null) {
            return CoreInstance.openCoreSession(repository.getName());
        }
        throw new NuxeoException("Unable to open session");
    }

}
