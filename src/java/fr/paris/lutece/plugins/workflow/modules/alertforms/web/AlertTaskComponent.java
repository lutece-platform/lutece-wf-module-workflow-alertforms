/*
 * Copyright (c) 2002-2021, City of Paris
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
package fr.paris.lutece.plugins.workflow.modules.alertforms.web;

import fr.paris.lutece.plugins.workflow.modules.alertforms.business.Alert;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.TaskAlertConfig;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.retrieval.RetrievalTypeFactory;
import fr.paris.lutece.plugins.workflow.modules.alertforms.service.IAlertService;
import fr.paris.lutece.plugins.workflow.modules.alertforms.util.constants.AlertConstants;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.state.IStateService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * AlertTaskComponent
 *
 */
@ApplicationScoped
@Named( "workflow-alertforms.alertTaskComponent" )
public class AlertTaskComponent extends NoFormTaskComponent
{
    private static final String TEMPLATE_TASK_ALERT_CONFIG = "admin/plugins/workflow/modules/alertforms/task_alert_config.html";
    private static final String TEMPLATE_TASK_ALERT_HISTORY = "admin/plugins/workflow/modules/alertforms/task_alert_history.html";
    @Inject
    @Named( AlertConstants.BEAN_ALERT_CONFIG_SERVICE )
    private ITaskConfigService _taskAlertConfigService;
    @Inject
    private IAlertService _alertService;
    @Inject
    private IStateService _stateService;
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    @Inject
    public AlertTaskComponent( @Named( "workflow-alertforms.taskTypeAlert" ) ITaskType taskType,
                               @Named( "workflow-alertforms.taskAlertConfigService" ) ITaskConfigService taskConfigService )
    {
        setTaskType( taskType );
        setTaskConfigService( taskConfigService );
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<>( );
        model.put( AlertConstants.MARK_CONFIG, _taskAlertConfigService.findByPrimaryKey( task.getId( ) ) );
        model.put( AlertConstants.MARK_LIST_STATES, _alertService.getListStates( task.getAction( ).getId( ) ) );
        model.put( AlertConstants.MARK_LIST_FORMS, _alertService.getListForms( ) );
        model.put( AlertConstants.MARK_LIST_QUESTIONS_DATE, _alertService.getListQuestionsDate( task.getId( ), request.getLocale( ) ) );
        model.put( AlertConstants.MARK_RETRIEVAL_TYPES, RetrievalTypeFactory.getRetrievalTypes( ) );
        model.put( AlertConstants.MARK_LOCALE, locale );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_ALERT_CONFIG, locale, model );

        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        Alert alert = _alertService.find( nIdHistory, task.getId( ) );
        if ( alert == null )
        {
            return "";
        }
        TaskAlertConfig alertConfig = _taskAlertConfigService.findByPrimaryKey( task.getId( ) );
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdHistory );

/*        State stateBefore = _stateService.findByPrimaryKey( resourceHistory.getAction( ).getStateBefore( ).getId( ) );
        State stateAfter = _stateService.findByPrimaryKey( alertConfig.getIdStateAfterDeadline( ) );*/

        Map<String, Object> model = new HashMap<>( );
/*        model.put( AlertConstants.MARK_STATE_BEFORE, stateBefore );
        model.put( AlertConstants.MARK_STATE_AFTER, stateAfter );*/

        LocalDateTime ldtRef = alert.getDateReference( ).toLocalDateTime( );
        LocalDateTime ldtRefAlert = ldtRef.plusDays( alertConfig.getNbDaysToDate( ) );
        LocalDateTime ldtNow = LocalDateTime.now( );

        model.put( AlertConstants.MARK_DAYS_BETWEEN, ChronoUnit.DAYS.between( ldtNow.toLocalDate( ), ldtRefAlert.toLocalDate( ) ) );
        model.put( AlertConstants.MARK_ALERT_ACTIVE, alert.isActive( ) );
        model.put( AlertConstants.MARK_ALERT_EXECUTED, alert.isExecuted( ) );

        model.put( AlertConstants.MARK_NB_DAYS_ALERT, alertConfig.getNbDaysToDate( ) );

        return AppTemplateService.getTemplate( TEMPLATE_TASK_ALERT_HISTORY, locale, model ).getHtml( );

    }
}
