/**
 * Created by zaporozhec on 4/30/15.
 */
$(document).ready(function () {
    $('#edit').froalaEditor({   // Set the image upload URL.
        imageUploadURL: '/editor/upload/',
        saveURL: '/editor/save/',
        saveMethod: 'POST',
        saveParams: {
            id: $("#id").val(),
            contentName: $("#nameVal").val(),
            courseId: $("#courseId").val(),
            type: $("#type").val()
        },
    });

    $("#edit").froalaEditor('html.set', $('#edit').val());

    $('#saveBTN').click(function () {
        $('#edit').froalaEditor('save.save')
        return false;
    });
});