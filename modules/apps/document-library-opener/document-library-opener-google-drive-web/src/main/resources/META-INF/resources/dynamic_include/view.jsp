<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
DLOpenerGoogleDriveFileReference googleDriveFileReference = (DLOpenerGoogleDriveFileReference)request.getAttribute(DLOpenerGoogleDriveWebKeys.DL_OPENER_GOOGLE_DRIVE_FILE_REFERENCE);
%>

<c:if test="<%= googleDriveFileReference != null %>">
	<portlet:renderURL var="renderURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="/document_library/open_google_docs" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(googleDriveFileReference.getFileEntryId()) %>" />
	</portlet:renderURL>

	<aui:script>
		window.open('<%= renderURL %>');
	</aui:script>
</c:if>

<script>
	window.<portlet:namespace />redirectNotification = function(url) {
		var TIME_SHOW_MSG = 2000;

		Liferay.Util.openWindow(
			{
				dialog:
				{
					bodyContent: '<p><liferay-ui:message key="you-are-being-redirected-to-an-external-editor-to-edit-this-document" /></p><div aria-hidden="true" class="loading-animation"></div>',
					cssClass: 'google-docs-redirect-modal',
					height: 172,
					modal: true,
					resizable: false,
					title: '',
					width: 320
				}
			}
		);

		setTimeout(
			function() {
				var form = document.createElement('form');

				form.method = 'POST';
				form.action = url;

				document.body.appendChild(form);

				form.submit();
			},
			TIME_SHOW_MSG);
	}
</script>

<style>
	.google-docs-redirect-modal {
		font-size: .875rem;
		text-align: center;
	}
	.google-docs-redirect-modal .modal-header {
		display: none;
	}
</style>