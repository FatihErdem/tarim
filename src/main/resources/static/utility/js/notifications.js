/**
 * Created by Fatih on 24.5.2016.
 */
$(document).ready(function () {
    (function request() {
        $.ajax({
            url: "http://localhost:8080/api/notifications",
            success: function (data) {
                document.getElementById('notificationMenu').innerHTML = '';
                document.getElementById("notificationCount").innerHTML = data.unreadCount;
                document.getElementById("notificationCount2").innerHTML = data.unreadCount;
                document.getElementById("notificationCount3").innerHTML = data.unreadCount;

                for (var counter = 0; counter < data.details.length; counter++) {
                    var div = document.createElement('div');
                    var link = data.details[counter].demandId;
                    var url = "/demands/details/" + link;
                    var uniqueNotification =
                        "<li>" +
                        "<a href=" + url + ">" +
                        "<span class=\"time\">" + data.details[counter].createdAt + "</span>" +
                        "<span class=\"details\">" +
                        "<span class=\"label label-sm label-icon label-success\">" +
                        "<i class=\"fa fa-plus\"></i>" +
                        "</span><b>" + "#" + data.details[counter].demandId + "</b> no\'lu talep geldi" + "</span>" +
                        "</a>" +
                        "</li>";
                    div.innerHTML = uniqueNotification;
                    var element = div.firstChild;
                    document.getElementById('notificationMenu').appendChild(element);
                    div.innerHTML = '';
                }
            },
            complete : function () {
                setTimeout(request, 15000)
            }

        });
    })();

});