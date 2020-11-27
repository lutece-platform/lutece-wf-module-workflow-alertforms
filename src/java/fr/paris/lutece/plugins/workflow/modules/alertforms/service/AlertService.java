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

import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.util.FormsConstants;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.Alert;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.IAlertDAO;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.TaskAlertConfig;
import fr.paris.lutece.plugins.workflow.modules.alertforms.util.constants.AlertConstants;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceWorkflow;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.plugins.workflowcore.business.state.StateFilter;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.state.IStateService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.plugins.workflowcore.service.task.ITaskService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.util.ReferenceList;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * AlertService
 * 
 */
public final class AlertService implements IAlertService
{
    public static final String BEAN_SERVICE = "workflow-alertforms.alertService";
    private List<Integer> _listAcceptedEntryTypesDate;
    @Inject
    private ITaskService _taskService;
    @Inject
    private IStateService _stateService;
    @Inject
    private IResourceWorkflowService _resourceWorkflowService;
    @Inject
    private IResourceHistoryService _resourceHistoryService;
    @Inject
    private IActionService _actionService;
    @Inject
    @Named( AlertConstants.BEAN_ALERT_CONFIG_SERVICE )
    private ITaskConfigService _taskAlertConfigService;
    @Inject
    private IAlertDAO _alertDAO;

    /**
     * Private constructor
     */
    private AlertService( )
    {
        // Init list accepted entry types for date
        _listAcceptedEntryTypesDate = fillListEntryTypes( AlertConstants.PROPERTY_ACCEPTED_FORM_ENTRY_TYPES_DATE );
    }

    // CRUD

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( AlertPlugin.BEAN_TRANSACTION_MANAGER )
    public void create( Alert alert )
    {
        if ( alert != null )
        {
            _alertDAO.insert( alert, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( AlertPlugin.BEAN_TRANSACTION_MANAGER )
    public void desactivateByHistory( int nIdResourceHistory, int nIdTask, boolean executed )
    {
        _alertDAO.desactivateByHistory( nIdResourceHistory, nIdTask, executed, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( AlertPlugin.BEAN_TRANSACTION_MANAGER )
    public void desactivateByTask( int nIdTask )
    {
        _alertDAO.desactivateByTask( nIdTask, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Alert find( int nIdResourceHistory, int nIdTask )
    {
        return _alertDAO.load( nIdResourceHistory, nIdTask, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Alert> findAllActive( )
    {
        return _alertDAO.selectAllActive( PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
    }

    // CHECKS

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEntryTypeDateAccepted( int nIdEntryType )
    {
        boolean bIsAccepted = false;

        if ( ( _listAcceptedEntryTypesDate != null ) && !_listAcceptedEntryTypesDate.isEmpty( ) )
        {
            bIsAccepted = _listAcceptedEntryTypesDate.contains( nIdEntryType );
        }

        return bIsAccepted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFormResponseStateValid( TaskAlertConfig config, FormResponse formResponse, Locale locale )
    {
        boolean bIsValid = false;

        ITask task = _taskService.findByPrimaryKey( config.getIdTask( ), locale );

        if ( task != null )
        {
            Action action = _actionService.findByPrimaryKey( task.getAction( ).getId( ) );

            if ( ( action != null ) && ( action.getStateAfter( ) != null ) )
            {
                ResourceWorkflow resourceWorkflow = _resourceWorkflowService.findByPrimaryKey( formResponse.getId( ), FormResponse.RESOURCE_TYPE,
                        action.getWorkflow( ).getId( ) );

                if ( ( resourceWorkflow != null ) && ( resourceWorkflow.getState( ) != null )
                        && ( resourceWorkflow.getState( ).getId( ) == action.getStateAfter( ).getId( ) ) )
                {
                    bIsValid = true;
                }
            }
        }

        return bIsValid;
    }

    // GET

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Question> getListQuestions( int nIdTask )
    {
        TaskAlertConfig config = _taskAlertConfigService.findByPrimaryKey( nIdTask );

        List<Question> listQuestions = new ArrayList<>( );

        if ( config != null )
        {
            listQuestions = QuestionHome.getListQuestionByIdForm( config.getIdForm( ) );
        }

        return listQuestions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList getListQuestionsDate( int nIdTask, Locale locale )
    {
        ReferenceList refenreceListEntries = new ReferenceList( );
        refenreceListEntries.addItem( FormsConstants.DEFAULT_ID_VALUE, StringUtils.EMPTY );

        for ( Question question : getListQuestions( nIdTask ) )
        {
            int nIdEntryType = question.getEntry( ).getEntryType( ).getIdType( );

            if ( isEntryTypeDateAccepted( nIdEntryType ) )
            {
                refenreceListEntries.addItem( question.getId( ), buildReferenceEntryToString( question ) );
            }
        }

        return refenreceListEntries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList getListForms( )
    {
        ReferenceList listForms = FormHome.getFormsReferenceList( );
        ReferenceList refenreceListForms = new ReferenceList( );
        refenreceListForms.addItem( FormsConstants.DEFAULT_ID_VALUE, StringUtils.EMPTY );

        if ( listForms != null )
        {
            refenreceListForms.addAll( listForms );
        }

        return refenreceListForms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList getListStates( int nIdAction )
    {
        ReferenceList referenceListStates = new ReferenceList( );
        Action action = _actionService.findByPrimaryKey( nIdAction );

        if ( ( action != null ) && ( action.getWorkflow( ) != null ) )
        {
            StateFilter stateFilter = new StateFilter( );
            stateFilter.setIdWorkflow( action.getWorkflow( ).getId( ) );

            List<State> listStates = _stateService.getListStateByFilter( stateFilter );

            referenceListStates.addItem( FormsConstants.DEFAULT_ID_VALUE, StringUtils.EMPTY );
            referenceListStates.addAll( ReferenceList.convert( listStates, AlertConstants.ID, AlertConstants.NAME, true ) );
        }

        return referenceListStates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormResponse getFormResponse( Alert alert )
    {
        FormResponse formresponse = null;

        if ( alert != null )
        {
            ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( alert.getIdResourceHistory( ) );

            if ( ( resourceHistory != null ) && FormResponse.RESOURCE_TYPE.equals( resourceHistory.getResourceType( ) ) )
            {
                formresponse = FormResponseHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
            }
        }

        return formresponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDate( TaskAlertConfig config, int nIdFormResponse, int nIdForm )
    {
        long lDate = 0;

        if ( config != null )
        {
            FormQuestionResponse formQuestionResponse = FormQuestionResponseHome
                    .findFormQuestionResponseByResponseQuestion( nIdFormResponse, config.getIdQuestionDate( ) ).get( 0 );

            String strDate = formQuestionResponse.getEntryResponse( ).get( 0 ).getResponseValue( );

            SimpleDateFormat formatter = new SimpleDateFormat( AppPropertiesService.getProperty( FormsConstants.PROPERTY_EXPORT_FORM_DATE_CREATION_FORMAT ) );
            try
            {
                Date dDate = formatter.parse( strDate );
                return dDate.getTime( );
            }
            catch( ParseException e )
            {
                AppLogService.error( "Unable to parse given date", e );
            }
        }

        return lDate;
    }

    // ACTIONS

    /**
     * {@inheritDoc}
     */
    @Override
    public void doChangeFormResponseState( TaskAlertConfig config, int nIdFormResponse, Alert alert )
    {
        // The locale is not important. It is just used to fetch the task action id
        Locale locale = I18nService.getDefaultLocale( );
        ITask task = _taskService.findByPrimaryKey( config.getIdTask( ), locale );

        if ( task != null )
        {
            State state = _stateService.findByPrimaryKey( config.getIdStateAfterDeadline( ) );
            Action action = _actionService.findByPrimaryKey( task.getAction( ).getId( ) );

            if ( ( state != null ) && ( action != null ) )
            {
                // Create Resource History
                ResourceHistory resourceHistory = new ResourceHistory( );
                resourceHistory.setIdResource( nIdFormResponse );
                resourceHistory.setResourceType( FormResponse.RESOURCE_TYPE );
                resourceHistory.setAction( action );
                resourceHistory.setWorkFlow( action.getWorkflow( ) );
                resourceHistory.setCreationDate( WorkflowUtils.getCurrentTimestamp( ) );
                resourceHistory.setUserAccessCode( AlertConstants.USER_AUTO );
                _resourceHistoryService.create( resourceHistory );

                // Update Resource
                ResourceWorkflow resourceWorkflow = _resourceWorkflowService.findByPrimaryKey( nIdFormResponse, FormResponse.RESOURCE_TYPE,
                        action.getWorkflow( ).getId( ) );
                resourceWorkflow.setState( state );
                _resourceWorkflowService.update( resourceWorkflow );

                // If the new state has automatic reflexive actions
                WorkflowService.getInstance( ).doProcessAutomaticReflexiveActions( nIdFormResponse, FormResponse.RESOURCE_TYPE, state.getId( ),
                        resourceWorkflow.getExternalParentId( ), locale );

                // if new state has action automatic
                WorkflowService.getInstance( ).executeActionAutomatic( nIdFormResponse, FormResponse.RESOURCE_TYPE, action.getWorkflow( ).getId( ),
                        resourceWorkflow.getExternalParentId( ) );

                // Remove the Alert
                desactivateByHistory( alert.getIdResourceHistory( ), alert.getIdTask( ), true );
            }
        }
    }

    // PRIVATE METHODS

    /**
     * Build the reference entry into String
     * 
     * @param entry
     *            the entry
     * @param locale
     *            the Locale
     * @return the reference entry
     */
    private String buildReferenceEntryToString( Question question )
    {
        StringBuilder sbReferenceEntry = new StringBuilder( );
        sbReferenceEntry.append( question.getId( ) );
        sbReferenceEntry.append( AlertConstants.SPACE + AlertConstants.OPEN_BRACKET );
        sbReferenceEntry.append( question.getTitle( ) );
        sbReferenceEntry.append( AlertConstants.SPACE + AlertConstants.HYPHEN + AlertConstants.SPACE );
        sbReferenceEntry.append( question.getEntry( ).getEntryType( ).getTitle( ) );
        sbReferenceEntry.append( AlertConstants.CLOSED_BRACKET );

        return sbReferenceEntry.toString( );
    }

    /**
     * Fill the list of entry types
     * 
     * @param strPropertyEntryTypes
     *            the property containing the entry types
     * @return a list of integer
     */
    private static List<Integer> fillListEntryTypes( String strPropertyEntryTypes )
    {
        List<Integer> listEntryTypes = new ArrayList<>( );
        String strEntryTypes = AppPropertiesService.getProperty( strPropertyEntryTypes );

        if ( StringUtils.isNotBlank( strEntryTypes ) )
        {
            String [ ] listAcceptEntryTypesForIdDemand = strEntryTypes.split( AlertConstants.COMMA );

            for ( String strAcceptEntryType : listAcceptEntryTypesForIdDemand )
            {
                if ( StringUtils.isNotBlank( strAcceptEntryType ) && StringUtils.isNumeric( strAcceptEntryType ) )
                {
                    int nAcceptedEntryType = Integer.parseInt( strAcceptEntryType );
                    listEntryTypes.add( nAcceptedEntryType );
                }
            }
        }

        return listEntryTypes;
    }
    
    @Override
    public void deleteByHistory( int nIdResourceHistory, int nIdTask )
    {
        _alertDAO.deleteByHistory( nIdResourceHistory, nIdTask, PluginService.getPlugin( AlertPlugin.PLUGIN_NAME ) );
    }
}
