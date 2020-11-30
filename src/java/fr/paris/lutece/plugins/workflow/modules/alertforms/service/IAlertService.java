/*
 * Copyright (c) 2002-2020, City of Paris
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
package fr.paris.lutece.plugins.workflow.modules.alertforms.service;

import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.Alert;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.TaskAlertConfig;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;
import java.util.Locale;

/**
 *
 * IAlertService
 *
 */
public interface IAlertService
{
    /**
     * Create a new alert
     * 
     * @param alert
     *            the alert
     */
    void create( Alert alert );

    /**
     * Desactivate an alert by id history
     * 
     * @param nIdResourceHistory
     *            the id history
     * @param nIdTask
     *            the id task
     */
    void desactivateByHistory( int nIdResourceHistory, int nIdTask, boolean executed );

    /**
     * Desactivate an alert by id task
     * 
     * @param nIdTask
     *            the id task
     */
    void desactivateByTask( int nIdTask );

    /**
     * Find an alert
     * 
     * @param nIdResourceHistory
     *            the id history
     * @param nIdTask
     *            the id task
     * @return an {@link Alert}
     */
    Alert find( int nIdResourceHistory, int nIdTask );

    /**
     * Find all the active alerts
     * 
     * @return a list of {@link Alert}
     */
    List<Alert> findAllActive( );

    // CHECKS

    /**
     * Check if the given entry type id is accepted for the date
     * 
     * @param nIdEntryType
     *            the id entry type
     * @return true if it is accepted, false otherwise
     */
    boolean isEntryTypeDateAccepted( int nIdEntryType );

    /**
     * Check if the formresponse has the same state before executing the action
     * 
     * @param config
     *            the config
     * @param formResponse
     *            the formresponse
     * @param locale
     *            the locale
     * @return true if the formresponse has a valid state, false otherwise
     */
    boolean isFormResponseStateValid( TaskAlertConfig config, FormResponse formResponse, Locale locale );

    // GET

    /**
     * Get the list of questions from a given id task
     * 
     * @param nIdTask
     *            the id task
     * @return a list of IEntry
     */
    List<Question> getListQuestions( int nIdTask );

    /**
     * Get the list of questions that have the accepted type (which are defined in <b>workflow-alertforms.properties</b>)
     * 
     * @param nIdTask
     *            the id task
     * @param locale
     *            the Locale
     * @return a ReferenceList
     */
    ReferenceList getListQuestionsDate( int nIdTask, Locale locale );

    /**
     * Get the list of forms
     * 
     * @return a ReferenceList
     */
    ReferenceList getListForms( );

    /**
     * Get the list of states
     * 
     * @param nIdAction
     *            the id action
     * @return a ReferenceList
     */
    ReferenceList getListStates( int nIdAction );

    /**
     * Get the formresponse from a given Alert
     * 
     * @param alert
     *            the alert
     * @return a {@link FormResponse}
     */
    FormResponse getFormResponse( Alert alert );

    /**
     * Get the date
     * 
     * @param config
     *            the config
     * @param nIdFormResponse
     *            the id formresponse
     * @param nIdForm
     *            the id form
     * @return the date
     */
    long getDate( TaskAlertConfig config, int nIdFormResponse, int nIdForm );

    // ACTIONS

    /**
     * Do change the formresponse state
     * 
     * @param config
     *            the config
     * @param nIdFormResponse
     *            the id form response
     * @param alert
     *            the alert
     */
    void doChangeFormResponseState( TaskAlertConfig config, int nIdFormResponse, Alert alert );

    /**
     * Delete alert by history
     * 
     * @param nIdResourceHistory
     *            the History id
     * @param nIdTask
     *            the task id
     * @param plugin
     *            the plugin
     */
    void deleteByHistory( int nIdResourceHistory, int nIdTask );
}
