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
package fr.paris.lutece.plugins.workflow.modules.alertforms.business;

import fr.paris.lutece.plugins.workflow.modules.alertforms.service.AlertPlugin;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 *
 * TaskAlertConfigDAO
 *
 */
public class TaskAlertConfigDAO implements ITaskConfigDAO<TaskAlertConfig>
{
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = " SELECT id_task, id_form, id_state_after_deadline, id_question_date, nb_days_to_date, id_retrieval_type "
            + " FROM task_alert_cf  WHERE id_task = ? ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO task_alert_cf( id_task, id_form, id_state_after_deadline, id_question_date, nb_days_to_date, id_retrieval_type )"
            + " VALUES ( ?,?,?,?,?,? ) ";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_alert_cf SET id_form = ?, id_state_after_deadline = ?, id_question_date = ?, nb_days_to_date = ?, id_retrieval_type = ? "
            + " WHERE id_task = ? ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM task_alert_cf WHERE id_task = ? ";

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( TaskAlertConfig config )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, AlertPlugin.getPlugin( ) ) )
        {
            int nIndex = 0;
    
            daoUtil.setInt( ++nIndex, config.getIdTask( ) );
            daoUtil.setInt( ++nIndex, config.getIdForm( ) );
            daoUtil.setInt( ++nIndex, config.getIdStateAfterDeadline( ) );
            daoUtil.setInt( ++nIndex, config.getIdQuestionDate( ) );
            daoUtil.setInt( ++nIndex, config.getNbDaysToDate( ) );
            daoUtil.setInt( ++nIndex, config.getIdRetrievalType( ) );
    
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( TaskAlertConfig config )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, AlertPlugin.getPlugin( ) ) )
        {
            int nIndex = 0;
    
            daoUtil.setInt( ++nIndex, config.getIdForm( ) );
            daoUtil.setInt( ++nIndex, config.getIdStateAfterDeadline( ) );
            daoUtil.setInt( ++nIndex, config.getIdQuestionDate( ) );
            daoUtil.setInt( ++nIndex, config.getNbDaysToDate( ) );
            daoUtil.setInt( ++nIndex, config.getIdRetrievalType( ) );
    
            daoUtil.setInt( ++nIndex, config.getIdTask( ) );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskAlertConfig load( int nIdTask )
    {
        TaskAlertConfig config = null;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, AlertPlugin.getPlugin( ) ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeQuery( );
    
            if ( daoUtil.next( ) )
            {
                int nIndex = 01;
                config = new TaskAlertConfig( );
                config.setIdTask( daoUtil.getInt( ++nIndex ) );
                config.setIdForm( daoUtil.getInt( ++nIndex ) );
                config.setIdStateAfterDeadline( daoUtil.getInt( ++nIndex ) );
                config.setIdQuestionDate( daoUtil.getInt( ++nIndex ) );
                config.setNbDaysToDate( daoUtil.getInt( ++nIndex ) );
                config.setIdRetrievalType( daoUtil.getInt( ++nIndex ) );
            }
        }
        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, AlertPlugin.getPlugin( ) ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeUpdate( );
        }
    }
}
