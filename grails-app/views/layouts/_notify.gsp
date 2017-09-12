<g:if test="${flash.message}">
    <script>$.notify("${flash.message}", "success");</script>
</g:if>
<g:elseif test="${flash.error}">
    <script>$.notify("${flash.error}", "error");</script>>
</g:elseif>
<g:elseif test="${flash.info}">
    <script>$.notify("${flash.info}", "info");</script>
</g:elseif>