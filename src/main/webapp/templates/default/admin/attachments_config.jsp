<%@ include file="/templates/default/common/common_header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/templates/default/styles/style.css" />

<form action="#" method="post">
	<table class="forumline" cellspacing="1" cellpadding="3" width="100%"
		border="0">
		<tr>
			<th class="thhead" valign="middle" colspan="2" height="25">{I18n.getMessage("AdminAttachments.configTitle")}</th>
		</tr>

		<!-- THUMB -->
		<tr>
			<td>
				<table width="99%" cellpadding="4" cellspacing="1" border="0"
					align="center" class="forumline">
					<tr>
						<td class="row1" width="80%"><span class="gen"><b>${I18n.getMessage("AdminAttachments.configCreateThumb")}</b></span><br />
							<span class="gensmall">${I18n.getMessage("AdminAttachments.configCreateThumbDesc")}</span></td>

						<td class="row2"><span class="gen"> <input
								type="radio" name="p_attachments.images.createthumb"
								value="true"<#if
								createThumb>checked</#if>/>${I18n.getMessage("User.yes")} <input
								type="radio" name="p_attachments.images.createthumb"
								value="false"<#if
								!createThumb>checked</#if>/>${I18n.getMessage("User.no")}
						</span></td>
					</tr>
				</table>
			</td>
		</tr>

		<!-- THUMB SIZE -->
		<tr>
			<td>
				<table width="99%" cellpadding="4" cellspacing="1" border="0"
					align="center" class="forumline">
					<tr>
						<td class="row1" width="80%"><span class="gen"><b>${I18n.getMessage("AdminAttachments.configThumb")}</b></span><br />
							<span class="gensmall">${I18n.getMessage("AdminAttachments.configThumbDesc")}</span></td>

						<td class="row2"><span class="gensmall"> <input
								type="text" size="10" maxlength="4"
								name="p_attachments.images.thumb.maxsize.w" value="${thumbW}" />
								x <input type="text" size="10" maxlength="4"
								name="p_attachments.images.thumb.maxsize.h" value="${thumbH}" />
								(${I18n.getMessage("AdminAttachments.width")} x
								${I18n.getMessage("AdminAttachments.height")})
						</span></td>
					</tr>
				</table>
			</td>
		</tr>

		<!-- THUMB DISPLAY BORDER -->
		<tr>
			<td>
				<table width="99%" cellpadding="4" cellspacing="1" border="0"
					align="center" class="forumline">
					<tr>
						<td class="row1" width="80%"><span class="gen"><b>${I18n.getMessage("AdminAttachments.thumbBorder")}</b></span><br />
							<span class="gensmall">${I18n.getMessage("AdminAttachments.thumbBorderDesc")}</span></td>

						<td class="row2"><span class="gen"> <input
								type="radio" name="p_attachments.images.thumb.box.show"
								value="true"<#if
								thumbBorder>checked</#if>/>${I18n.getMessage("User.yes")} <input
								type="radio" name="p_attachments.images.thumb.box.show"
								value="false"<#if
								!thumbBorder>checked</#if>/>${I18n.getMessage("User.no")}
						</span></td>
					</tr>
				</table>
			</td>
		</tr>

		<!-- MAX POST -->
		<tr>
			<td>
				<table width="99%" cellpadding="4" cellspacing="1" border="0"
					align="center" class="forumline">
					<tr>
						<td class="row1" width="80%"><span class="gen"><b>${I18n.getMessage("AdminAttachments.maxAttachs")}</b></span><br />
							<span class="gensmall">${I18n.getMessage("AdminAttachments.maxAttachsDesc")}</span></td>

						<td class="row2"><input type="text" size="5" maxlength="2"
							name="p_attachments.max.post" value="${maxPost}" /></td>
					</tr>
				</table>
			</td>
		</tr>

		<tr>
			<td class="catsides" colspan="2" align="center"><input
				type="submit" value="${I18n.getMessage("
				Update")}" class="mainoption" />&nbsp;&nbsp;<input type="reset"
				value="${I18n.getMessage(" Reset")}" class="mainoption" /></td>
		</tr>

	</table>
</form>
