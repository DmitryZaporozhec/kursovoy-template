/**
 * Created by zaporozhec on 4/30/15.
 */
$(document).ready(function () {
    $('#edit').froalaEditor({   // Set the image upload URL.
        imageUploadURL: '/editor/upload/',
        heightMin: 500
    });

    $("#edit").froalaEditor('html.set', $('#edit').val());

    $('#saveBTN').click(function () {
        $.ajax({
            url: "/editor/save",
            type: "POST",
            dataType: "text",
            contentType: "application/json",
            data: JSON.stringify({
                id: $("#id").val(),
                contentName: $("#nameVal").val(),
                courseId: $("#courseId").val(),
                type: $("#type").val(),
                body: $("#edit").froalaEditor('html.get')
            }),
            success: function (data, status, jqXHR) {
                window.location.href = data;

            },
            error: function (data) {
                $(".alert-error").show()
            }
        });
        return false;
    });
    if ($("#id").val()) {
        $.ajax({
            url: "/editor/get/body?id=" + $("#id").val(),
            type: "GET",
            dataType: "text",
            contentType: "application/json",
            success: function (data, status, jqXHR) {
                $("#edit").froalaEditor('html.set', data);
            },
            error: function (data) {
                $(".alert-error").show()
            }
        });
    }
});