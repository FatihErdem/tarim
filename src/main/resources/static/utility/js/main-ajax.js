/**
 * Created by Fatih on 24.5.2016.
 */
$(document).ready(function () {

    var table = $('#table').DataTable({
        language: {
            search: "Tabloda Ara: ",
            lengthMenu: "_MENU_ Kayıt Göster",
            zeroRecords: "Sonuç Bulunamadı",
            emptyTable: "Veri Yok",
            "sInfo": "  _TOTAL_ Kayıttan _START_ - _END_ Arası Kayıtlar Gösteriliyor",
            paginate: {
                first: "İlk",
                previous: "Önceki",
                next: "Sonraki",
                last: "Son"
            }
        }
    });


    (function request() {
        $.ajax({
            url: "http://localhost:8080/api/notifications",
            success: function (data) {
                document.getElementById("notificationCount").innerHTML = data.unreadCount;
                document.getElementById("notificationCount2").innerHTML = data.unreadCount;
                document.getElementById("notificationCount3").innerHTML = data.unreadCount;

                for (var counter = 0; counter < data.details.length; counter++) {
                    var div = document.createElement('div');

                    var uniqueNotification =
                        "<li>" +
                        "<a href=\"/demands\">" +
                        "<span class=\"time\">" + data.details[counter].createdAt + "</span>" +
                        "<span class=\"details\">" +
                        "<span class=\"label label-md label-icon label-success\">" +
                        "<i class=\"fa fa-plus\"></i>" +
                        "</span><b>" + "#" + data.details[counter].demandId + "</b> no\'lu talep geldi" + "</span>" +
                        "</a>" +
                        "</li>";


                    div.innerHTML = uniqueNotification;
                    var element = div.firstChild;
                    document.getElementById('notificationMenu').appendChild(element);
                }
            }
        });
    })();

});