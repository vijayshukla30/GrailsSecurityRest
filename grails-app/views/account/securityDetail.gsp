<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country; com.moniday.enums.Nationality" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Security Detail</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="bs-docs-section clearfix">
    <form action="${createLink(controller: 'account', action: 'saveSecurityDetail')}"
          class="form-horizontal" method="POST" id="personalDetail">
        <div class="question-container">
            <div class="row">
                <div class="col-lg-2"></div>

                <div class="col-lg-8">
                    <div class="well bs-component">
                        <g:hiddenField name="uniqueId" value="${uniqueId}"/>
                        <fieldset>
                            <legend>
                                <div class="col-md-4">
                                    <g:message code="user.security.detail"/>
                                </div>

                                <div class="col-md-1"></div>

                                <div class="col-md-7">
                                    <div class="btn btn-sm btn-success" id="addButton">Add Question</div>

                                    <div class="btn btn-sm btn-danger" id="removeButton">Remove Question</div>
                                </div>
                            </legend>

                            <div class="custom-row">
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">
                                        <g:message code="user.security.question"/>
                                    </label>

                                    <div class="col-lg-9">
                                        <input type="text" class="form-control" name="question"
                                               placeholder="${message(code: 'user.security.question')}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">
                                        <g:message code="user.security.answer"/>
                                    </label>

                                    <div class="col-lg-9">
                                        <input type="text" class="form-control" name="answer"
                                               placeholder="${message(code: 'user.security.answer')}">
                                    </div>
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="col-lg-8 col-lg-offset-4">
                                    <button type="reset" class="btn btn-default">
                                        <g:message code="form.reset"/>
                                    </button>
                                    <button type="submit" class="btn btn-primary">
                                        <g:message code="form.submit"/>
                                    </button>
                                </div>
                            </div>
                        </fieldset>

                    </div>
                </div>

                <div class="col-lg-2"></div>
            </div>
        </div>
    </form>
</div>
<script>
    $(document).ready(function () {
        $("#personalDetail").validate({
            rules: {
                firstName: {
                    required: true
                },
                lastName: {
                    required: true
                },
                nationality: {
                    required: true
                },
                country: {
                    required: true
                },
                currency: {
                    required: true
                }
            },
            messages: {
                firstName: {
                    required: "FirstName is required"
                },
                lastName: {
                    required: "LastName is required"
                },
                nationality: {
                    required: "Nationality is required"
                },
                country: {
                    required: "Country is required"
                },
                currency: {
                    required: "Currency is required"
                }
            }
        });

        $("#dob_day").addClass('form-control').css('width', "80px").css('display', 'inline');
        $("#dob_month").addClass('form-control').css('width', "140px").css('display', 'inline');
        $("#dob_year").addClass('form-control').css('width', "150px").css('display', 'inline');
    });

    var $container = $('.question-container');
    var $row = $('.custom-row');
    var $add = $('#addButton');
    var $remove = $('#removeButton');
    var $focused;

    $container.on('click', 'input', function () {
        $focused = $(this);
    });

    $add.on('click', function () {
        var $newRow = $row.clone().insertAfter('.custom-row:last');
        $newRow.find('input').each(function () {
            this.value = '';
        });
    });

    $remove.on('click', function () {
        if (!$focused) {
            alert('Select a row to delete (click en input with it)');
            return;
        }

        var $currentRow = $focused.closest('.custom-row');
        if ($currentRow.index() === 0) {
            // don't remove first row
            alert("You can't remove first row");
        } else {
            $currentRow.remove();
            $focused = null;
        }
    });
</script>
</body>
</html>
