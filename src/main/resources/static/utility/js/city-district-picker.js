/**
 * Created by Fatih on 31.5.2016.
 */
$(document).ready(function () {

    $.ajax({
        url: "http://localhost:8080/api/cities",
        success: function (data) {
            var select = document.getElementById("city-select");
            for (var counter = 0; counter < data.length; counter++) {
                var option = document.createElement("option");
                option.value = data[counter].cityId;
                option.text = data[counter].cityName;
                select.appendChild(option);

            }
        }
    });

    $("#city-select").change(function () {
        var selected = document.getElementById("city-select");
        var cityId = selected.options[selected.selectedIndex].value;
        var selectdistrict = document.getElementById("district-select");
        selectdistrict.innerHTML = '';
        $.ajax({
            url: "http://localhost:8080/api/districts/" + cityId,
            success: function (data) {
                for (var counter = 0; counter < data.length; counter++) {
                    var option = document.createElement("option");
                    option.value = data[counter].districtId;
                    option.text = data[counter].districtName;
                    selectdistrict.appendChild(option);
                }
            }
        });
    });
});