$(document).ready(function() {
    var next = 0;
    var date_input = $('#eta'); // our date input has the name
    // "date"
    var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
    var options = {
        format: 'dd/mm/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
    };
    date_input.datepicker(options);
    $('#invoice_date').datepicker(options);
    $("#customer").change(function(e) {
        e.preventDefault();
        console.log("Customer Changed");
        var customerId = $(this).val();
        if (customerId == null || customerId == "") {
            return false;
        }
        console.log("Changed CustomerId : " + customerId);
        var newPurchaseOder = $('<div id="poPanel" class="form-group"></div>')
        $.ajax({
            url: "poByCustomer?customerId=" + customerId,
            success: function(result) {
                newPurchaseOder.append($(result));
                $("#etaPanel").after(newPurchaseOder);
                console.log(result);
                $('#customer').attr("disabled", true);
                $("#po").change(function(e) {
                    e.preventDefault();
                    console.log("Purchase Order Changed");
                    var purchaseOrderId = $(this).val();
                    console.log("Changed purchaseOrderId : " + purchaseOrderId);
                    $.ajax({
                        url: "prByPo?poId=" + purchaseOrderId,
                        success: function(result) {
                            newPurchaseOder.after($(result));
                            $('#po').attr("disabled", true);
                            $(".remove-item").click(function(e) {
                                var fields = this.id.split("_");
                                var panel = $('#purchaseItem_' + fields[1] + '_' + fields[2]);
                                panel.remove();
                            });
                            $(".remove-request").click(function(e) {
                                var fields = this.id.split("_");
                                var panel = $('#request_' + fields[1]);
                                panel.remove();
                            });
                        }
                    });
                });
            }
        });
    });
});