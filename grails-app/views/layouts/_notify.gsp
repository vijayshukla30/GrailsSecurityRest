<g:if test="${flash.message}">
    <script>$.notify("${flash.message}", "success");</script>
</g:if>
<g:if test="${flash.msg}">
    <script>$.notify("${flash.msg}", "success");</script>
</g:if>
<g:if test="${flash.error}">
    <script>$.notify("${flash.error}", "error");</script>
</g:if>
<g:if test="${flash.err}">
    <script>$.notify("${flash.err}", "error");</script>
</g:if>
<g:if test="${flash.info}">
    <script>$.notify("${flash.info}", "info");</script>
</g:if>
<g:if test="${flash.warning}">
    <script>$.notify("${flash.warning}", "warning");</script>
</g:if>
<g:if test="${flash.warn}">
    <script>$.notify("${flash.warn}", "warning");</script>
</g:if>