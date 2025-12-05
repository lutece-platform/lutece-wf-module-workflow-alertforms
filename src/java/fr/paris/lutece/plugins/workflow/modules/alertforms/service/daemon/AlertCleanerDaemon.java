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
package fr.paris.lutece.plugins.workflow.modules.alertforms.service.daemon;

import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.Alert;
import fr.paris.lutece.plugins.workflow.modules.alertforms.business.TaskAlertConfig;
import fr.paris.lutece.plugins.workflow.modules.alertforms.service.AlertService;
import fr.paris.lutece.plugins.workflow.modules.alertforms.service.IAlertService;
import fr.paris.lutece.plugins.workflow.modules.alertforms.util.constants.AlertConstants;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.portal.service.daemon.Daemon;
import fr.paris.lutece.portal.service.i18n.I18nService;

import java.util.Locale;

import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Daemon AlertCleanerDaemon
 * 
 */
public class AlertCleanerDaemon extends Daemon
{
    /**
     * Daemon's treatment method
     */
    public void run( )
    {
        StringBuilder sbLog = new StringBuilder( );
        ITaskConfigService configService = CDI.current().select( ITaskConfigService.class, NamedLiteral.of( AlertConstants.BEAN_ALERT_CONFIG_SERVICE ) ).get( );
        IAlertService alertService = CDI.current().select( IAlertService.class ).get( );

        for ( Alert alert : alertService.findAllActive( ) )
        {
            FormResponse formResponse = alertService.getFormResponse( alert );
            TaskAlertConfig config = configService.findByPrimaryKey( alert.getIdTask( ) );

            Locale locale = I18nService.getDefaultLocale( );

            if ( formResponse != null && config != null )
            {
                if ( !alertService.isFormResponseStateValid( config, formResponse, locale ) )
                {
                    sbLog.append( "\n- Cleaning alert (ID formResponse : " + formResponse.getId( ) + ", ID history : " + alert.getIdResourceHistory( )
                            + ", ID task : " + alert.getIdTask( ) + ")" );
                    // Remove the Alert
                    alertService.desactivateByHistory( alert.getIdResourceHistory( ), alert.getIdTask( ), false );
                }
            }
            else
            {
                // If the formResponse is null or the config is null, we remove the alert
                alertService.desactivateByHistory( alert.getIdResourceHistory( ), alert.getIdTask( ), false );
            }
        }

        if ( StringUtils.isBlank( sbLog.toString( ) ) )
        {
            sbLog.append( "\nNo alert to clean." );
        }

        setLastRunLogs( sbLog.toString( ) );
    }
}
