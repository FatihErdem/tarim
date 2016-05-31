/**
 * Created by Fatih on 31.5.2016.
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
});