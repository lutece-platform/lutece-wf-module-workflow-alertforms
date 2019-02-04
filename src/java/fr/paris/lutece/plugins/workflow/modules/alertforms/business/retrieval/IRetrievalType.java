/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.alertforms.business.retrieval;

import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.TaskAlertConfig;

import java.util.Locale;

/**
 *
 * IRetrievalType
 *
 */
public interface IRetrievalType
{
    /**
     * Get the id type
     * 
     * @return the id type
     */
    int getIdType( );

    /**
     * Set the id type
     * 
     * @param nIdType
     *            the id type
     */
    void setIdType( int nIdType );

    /**
     * Get the title key
     * 
     * @return the title key
     */
    String getTitleKey( );

    /**
     * Set the title key
     * 
     * @param strTitleKey
     *            the title key
     */
    void setTitleKey( String strTitleKey );

    /**
     * Get the title
     * 
     * @param locale
     *            the locale
     * @return the title
     */
    String getTitle( Locale locale );

    /**
     * Get the date
     * 
     * @param config
     *            the config
     * @param formResponse
     *            the formResponse
     * @return the date in long
     */
    Long getDate( TaskAlertConfig config, FormResponse formResponse );

    /**
     * Check if the config data is correctly configured
     * 
     * @param config
     *            the config
     * @return the error message if there is an error
     */
    boolean checkConfigData( TaskAlertConfig config );
}
