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
package nuxeo.publicdownload.api;

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
