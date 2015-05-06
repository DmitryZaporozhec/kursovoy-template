/**
 * Created by zaporozhec on 4/30/15.
 */
$(document).ready(function () {
    $("#submit").on("click", function () {
        $.ajax({
            url: "/user",
            type: "POST",
            dataType: "text",
            contentType: "application/json",
            data: toJson(),
            success: function (data, status, jqXHR) {
                alert("Success! " + data);
            },
            error: function (data) {
                alert("Error! " + data);
            }
        });
    });
});

function toJson() {
    return JSON.stringify({firstName: $("#name").val(), lastName: $("#lastName").val(), age: $("#age").val()});

}