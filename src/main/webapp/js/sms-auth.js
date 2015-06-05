/**
 * Created by zaporozhec on 4/30/15.
 */
$(document).ready(function () {

    $("#doSmsAuth").on("submit", function () {
        $("#error").hide();
        $.ajax({
            url: "/sms-auth",
            type: "POST",
            dataType: "text",
            contentType: "application/json",
            data: toJson(),
            success: function (data, status, jqXHR) {
                document.location.href = data;
            },
            error: function (data) {
                $("#error").show();
                $("#error").text("");
                $("#error").text(data.responseText);
            }
        });
        return false;
    });
});

function toJson() {
    return JSON.stringify({
        login: $("#login").val(),
        smsCode: $("#inputLogin").val()
    });

}