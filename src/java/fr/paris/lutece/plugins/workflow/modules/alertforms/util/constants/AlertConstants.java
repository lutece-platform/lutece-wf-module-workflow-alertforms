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
package fr.paris.lutece.plugins.workflow.modules.alertforms.util.constants;

/**
 *
 * AlertConstants
 *
 */
public final class AlertConstants
{
    // CONSTANTS
    public static final String COMMA = ",";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSED_BRACKET = ")";
    public static final String HYPHEN = "-";
    public static final String USER_AUTO = "auto";

    // BEAN
    public static final String BEAN_ALERT_CONFIG_SERVICE = "workflow-alertforms.taskAlertConfigService";

    // PROPERTIES
    public static final String PROPERTY_ACCEPTED_FORM_ENTRY_TYPES_DATE = "workflow-alertforms.acceptedFormEntryTypesDate";
    public static final String PROPERTY_LABEL_FORM = "module.workflow.alertforms.task_alert_config.label_task_form";
    public static final String PROPERTY_LABEL_POSITION_ENTRY_FORM_DATE = "module.workflow.alertforms.task_alert_config.label_task_entry_form_date";
    public static final String PROPERTY_LABEL_ID_STATE_AFTER_DEADLINE = "module.workflow.alertforms.task_alert_config.label_task_id_state_after_deadline";
    public static final String PROPERTY_LABEL_NB_DAYS_TO_DATE = "module.workflow.alertforms.task_alert_config.label_task_nb_days_to_date";
    public static final String PROPERTY_LABEL_RETRIEVAL_TYPE = "module.workflow.alertforms.task_alert_config.label_task_retrieval_type";

    // MARKS
    public static final String MARK_CONFIG = "config";
    public static final String MARK_LIST_FORMS = "list_forms";
    public static final String MARK_LIST_STATES = "list_states";
    public static final String MARK_LIST_QUESTIONS_DATE = "list_questions_date";
    public static final String MARK_RETRIEVAL_TYPES = "retrieval_types";
    public static final String MARK_LOCALE = "locale";
    public static final String MARK_STATE_BEFORE = "state_before";
    public static final String MARK_STATE_AFTER = "state_after";
    public static final String MARK_DAYS_BETWEEN = "days_between";
    public static final String MARK_ALERT_ACTIVE = "alert_active";
    public static final String MARK_NB_DAYS_ALERT = "nb_days_alert";

    // PARAMETERS
    public static final String PARAMETER_ID_FORM = "id_form";
    public static final String PARAMETER_POSITION_ENTRY_FORM_DATE = "position_entry_form_date";
    public static final String PARAMETER_ID_STATE_AFTER_DEADLINE = "id_state_after_deadline";
    public static final String PARAMETER_NB_DAYS_TO_DATE = "nb_days_to_date";
    public static final String PARAMETER_RETRIEVAL_TYPE = "retrieval_type";
    public static final String PARAMETER_APPLY = "apply";

    // MESSAGES
    public static final String MESSAGE_MANDATORY_FIELD = "module.workflow.alertforms.message.mandatory_field";
    public static final String MESSAGE_STATE_AFTER_DEADLINE_SAME_STATE_BEFORE = "module.workflow.alertforms.message.state_after_deadline_same_state_before";
    public static final String MESSAGE_ERROR_INVALID_NUMBER = "module.workflow.alertforms.message.invalid_number";

    /**
     * Private constructor
     */
    private AlertConstants( )
    {
    }
}
