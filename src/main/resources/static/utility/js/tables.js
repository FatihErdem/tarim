/**
 * Created by Fatih on 31.5.2016.
 */
$(document).ready(function () {
    $('#myTable').DataTable({
        retrieve: true,
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
        },
        iDisplayLength: 25,
        aLengthMenu: [[25, 50, 100, -1], [25, 50, 100, "All"]],
        stateSave: true,
        stateSaveParams: function (settings, data) {
            data.search.search = "";
        }
    });
});