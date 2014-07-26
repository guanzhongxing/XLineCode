<c:forEach var="attm" items="${attachments}">
	<c:if test="${attm.attmInfo.fileType!='EMBEDDED_IMAGE'}">
		<c:set var="hasThumb" value="${attm.attmInfo.hasThumb}" />
		<c:set var="displayThumbShowBox"
			value="${requestScope.displayThumbShowBox}" />

		<c:choose>
			<c:when test="${!hasThumb||displayThumbShowBox}">
				<c:set var="border" value="1" />
			</c:when>
			<c:otherwise>
				<c:set var="border" value="0" />
			</c:otherwise>
		</c:choose>

		<table width="70%" border="${border}" cellpadding="2" cellspacing="0"
			class="attachtable" align="center">
			<tr>
				<td width="15%" class="attachrow"><span class="genmed">&nbsp;filename</span></td>
				<td width="75%" class="attachrow"><span class="genmed">${attm.attmInfo.fileName}</span></td>

				<td rowspan="4" align="center"><c:choose>
						<c:when test="${requestScope.enableDownloadAttm}">
							<img src="${resourcesHost}/images/icon_disk.gif"
								alt="[Disk]" />
							<c:choose>
								<c:when test="${requestScope.downloadCaptchaEnabled}">
									<a href="initDownloadAttachment.do?attmId=${attm.id}"
										class="gensmall" target="_blank"><b>download</b></a>
								</c:when>
								<c:otherwise>
									<a href="downloadAttachment.do?attmId=${attm.id}"
										class="gensmall" target="_blank"><b>download</b></a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<img
								src="${resourcesHost}/images/icon_disk_disable.gif"
								title="<fmt:message
								key="all.jsp.lack.of.permission" />" />
						</c:otherwise>
					</c:choose></td>
			</tr>

			<tr>
				<td width="15%" class="attachrow"><span class="genmed">&nbsp;description</span></td>
				<td width="75%" class="attachrow"><span class="genmed">${attm.attmInfo.comment}</span></td>
			</tr>

			<tr>
				<td class="attachrow"><span class="genmed">&nbsp;filesize</span></td>
				<td class="attachrow"><span class="genmed"> <c:choose>
							<c:when test="${attm.attmInfo.filesize < 1024}">
								${attm.attmInfo.filesize} bytes
							</c:when>
							<c:otherwise>
								<fmt:formatNumber value="${attm.attmInfo.filesize / 1024}"
									pattern="#,###" /> Kbytes
							</c:otherwise>
						</c:choose>
				</span></td>
			</tr>

			<tr>
				<td class="attachrow"><span class="genmed">&nbsp;totalDownload:</span></td>
				<td class="attachrow"><span class="genmed">&nbsp;${attm.attmInfo.downloadCount}
						time</span></td>
			</tr>
		</table>
		<br />
	</c:if>
</c:forEach>