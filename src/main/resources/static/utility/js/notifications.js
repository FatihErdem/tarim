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

                for (var counter = 0; counter < data.details.length; counter++) {
                    var demandOrMaintainId = data.details[counter].demandOrMaintainId;
                    var state = data.details[counter].state;
                    var date = data.details[counter].date;
                    var div = document.createElement('div');
                    var url;
                    var span;

                    switch (state){
                        case "OPEN":
                            url = "/demands/details/" + demandOrMaintainId;
                            span = "<span class=\"label label-sm label-default\">" + "#" + demandOrMaintainId + "</b> no\'lu talep geldi" + "</span><b>";
                            break;
                        case "IN_PROGRESS":
                            url = "/maintains/details/" + demandOrMaintainId;
                            span = "<span class=\"label label-sm label-warning\">" + "#" + demandOrMaintainId + "</b> no\'lu servis başladı" + "</span><b>";
                            break;
                        case "COMPLETED":
                            url = "/maintains/details/" + demandOrMaintainId;
                            span = "<span class=\"label label-sm label-primary\">" + "#" + demandOrMaintainId + "</b> no\'lu servis tamamlandı" + "</span><b>";
                            break;
                        case "REJECTED":
                            url = "/maintains/details/" + demandOrMaintainId;
                            span = "<span class=\"label label-sm label-danger\">" + "#" + demandOrMaintainId + "</b> no\'lu rapor reddedildi" + "</span><b>";
                            break;
                        case "APPROVED":
                            url = "/maintains/details/" + demandOrMaintainId;
                            span = "<span class=\"label label-sm label-success\">" + "#" + demandOrMaintainId + "</b> no\'lu rapor onaylandı" + "</span><b>";
                    }
                    
                    var uniqueNotification =
                        "<li>" +
                        "<a href=" + url + ">" +
                        "<span class=\"time\">" + date + "</span>" +
                        "<span class=\"details\">" +
                         span +
                        "</span>" +
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