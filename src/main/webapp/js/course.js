/**
 * Created by zaporozhec on 4/30/15.
 */
$(document).ready(function () {

    $("#edit-course-form").on("submit", function () {
        $(".alert").hide();
        $.ajax({
            url: "/course/save",
            type: "POST",
            dataType: "text",
            contentType: "application/json",
            data: toJson(),
            success: function (data, status, jqXHR) {
                $(".alert-success").show()
                window.location.href = "/course/list"
            },
            error: function (data) {
                $(".alert-error").show()
            }
        });
        return false;
    });
});

function toJson() {
    return JSON.stringify({
        id: $('#id').val(),
        disciplineId: $('#discipline').val(),
        name: $("#name").val(),
        description: $("#description").val(),
        userId: $("#userId").val()
    });

}